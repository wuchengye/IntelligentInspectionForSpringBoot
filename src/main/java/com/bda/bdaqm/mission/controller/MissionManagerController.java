package com.bda.bdaqm.mission.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.UZipFile;
import com.bda.common.util.StringUtil;
import com.oracle.jrockit.jfr.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/mission/manager", method = RequestMethod.POST)
public class MissionManagerController {

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Autowired
    private MissionService missionService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;
    @Value("#{mqconfig.mq_ready_queue}")
    private String queueId;

    @RequestMapping("/uploadFile")
    @ResponseBody
    /*上传文件，接收临时文件夹id*/
    public Result uploadFile(@RequestParam("file") CommonsMultipartFile file){

        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //配置路径
        String tempDirName = user.getUserId() + "-" + System.currentTimeMillis();
        String tempPath = uploadFilePath + tempDirName + "/"; //临时文件夹路径
        String zipPath = tempPath + "zip/"; //zip保存路径
        String unZipPath = tempPath + "unZip/"; //解压路径
        //生成临时文件夹
        File tempFoldor = new File(tempPath);
        File zipFoldor = new File(zipPath);
        if(tempFoldor.exists()){
            System.out.println("临时文件夹已存在");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }
        if (!tempFoldor.mkdirs() || !zipFoldor.mkdirs()) {
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //保存zip文件
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        File tempFile = new File(zipPath + fileName);
        if (type.equals(".zip")) {
            try {
                file.transferTo(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
                FileUtils.delDir(tempPath);
                return Result.failure();
            }
        } else {
            System.out.println("上传文件不是zip格式");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //解压文件
        try {
            UZipFile.unZipFiles(tempFile, unZipPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解压失败");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //遍历mp3，检查格式和大小
        if (!checkMp3Files(new File(unZipPath))) {
            System.out.println("解压后的单个文件超过5M，或格式不是mp3");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        return Result.success("上传完成");
    }

    @RequestMapping("/createSingleMission")
    @ResponseBody
    public Result createMission(@RequestParam("missionName")String missionName,
                                @RequestParam("missionBegintime")String missionBegintime,
                                @RequestParam("missionLevel")int missionLevel,
                                @RequestParam("missionIstransfer")int missionIstransfer,
                                @RequestParam("missionIsinspection")int missionIsinspection,
                                @RequestParam("missionIstaboo")int missionIstaboo,
                                @RequestParam("missionIsrisk")int missionIsrisk,
                                @RequestParam("missionIsnodubious")int missionIsnodubious,
                                @RequestParam("missionDescribe")String missionDescribe,
                                @RequestParam("dirName")String dirName,
                                @RequestParam("missionTotalNum")int missionTotalNum
    ){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        InspectionMission missionModel = new InspectionMission();
        missionModel.setMissionName(missionName);
        //任务开始时间不能早于现在
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        long m2 = 120000;
        try {
            Date begin = df.parse(missionBegintime);
            //开始时间比现在小于两分钟
            if(begin.getTime() - (new Date().getTime()) < m2){
                //两分钟后开始
                date = new Date(new Date().getTime() + m2);
            }else {
                date = begin;
            }
        } catch (ParseException e) {
            date = new Date(new Date().getTime() + m2);
        }
        missionModel.setMissionBegintime(df.format(date));

        missionModel.setMissionLevel(missionLevel);
        missionModel.setMissionIstransfer(missionIstransfer);
        missionModel.setMissionIsinspection(missionIsinspection);
        missionModel.setMissionIstaboo(missionIstaboo);
        missionModel.setMissionIsrisk(missionIsrisk);
        missionModel.setMissionIsnodubious(missionIsnodubious);
        missionModel.setMissionDescribe(missionDescribe);
        //语音文件路径,任务创建时，文件在本机上
        missionModel.setMissionFilepath(uploadFilePath + dirName + "/");
        missionModel.setMissionCreaterid(Integer.valueOf(user.getUserId()));
        //文件总数
        missionModel.setMissionTotalNum(missionTotalNum);

        //创建任务时，任务相关状态
        missionModel.setMissionStatus(0);
        missionModel.setMissionUploadStatus(0);
        missionModel.setMissionTransferStatus(0);
        missionModel.setMissionInspectionStatus(0);

        int missionId = missionService.createMission(missionModel);
        if(missionId > 0 ){
            try {
                missionService.quartzMission(missionModel);
            }catch (SchedulerException e){
                e.printStackTrace();
            }
            return Result.success();
        }else {
            return Result.failure();
        }
    }

    @RequestMapping("/createCommonMission")
    public Result createCommonMission(
            @RequestParam("missionName")String missionName,
            @RequestParam("missionBegintime")String missionBegintime,
            @RequestParam("missionIstransfer")int missionIstransfer,
            @RequestParam("missionIsinspection")int missionIsinspection,
            @RequestParam("missionIstaboo")int missionIstaboo,
            @RequestParam("missionIsrisk")int missionIsrisk,
            @RequestParam("missionIsnodubious")int missionIsnodubious,
            @RequestParam("missionDescribe")String missionDescribe,
            @RequestParam("ftpPath")String ftpPath,
            @RequestParam(value = "ftpAccount",required = false)String ftpAccount,
            @RequestParam(value = "ftpPwd",required = false)String ftpPwd,
            @RequestParam("period")String period,
            @RequestParam(value = "isTest",required = false)Boolean isTest
            ){
        //验证连接
        if(isTest != null && isTest == true){
            if(ftpPath == null || ftpPath.equals("")){
                return Result.failure(ResultCode.FTP_PATH_NULL);
            }
            if(ftpPath.substring(0,1).equals("f")){
                FtpUtil ftpUtil = new FtpUtil(ftpPath,21,ftpAccount,ftpPwd);
                try {
                    ftpUtil.connect();
                    ftpUtil.disconnect();
                }catch (Exception e){
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
                return Result.success();
            }else {
                SFTPUtil3 sftpUtil3 = new SFTPUtil3(ftpPath,ftpAccount,ftpPwd);
                sftpUtil3.connect();
                if(sftpUtil3.getSftp().isConnected()){
                    return Result.success();
                }else {
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
            }
        }

        return Result.success();


    }


    @RequestMapping("/getListMission")
    public Result getListMission(){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<InspectionMission> list = missionService.getListMission(Integer.valueOf(user.getUserId()));
        return Result.success(list);
    }






    @RequestMapping("/missionCancel")
    public void missionCancel(@RequestParam("dirName")String dirName){
        deleteTemp(dirName);
    }

    @RequestMapping("/mqTest")
    public void mqTest(@RequestParam("path")String path, Integer pri){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", path + System.currentTimeMillis());
            map.put("path", path);
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            rabbitmqProducer.sendQueue(queueId + "_exchange", "pri_" + queueId + "_patt", map, pri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTemp(String id){
        String path = uploadFilePath + id + "/";
        File foldor = new File(path);
        if(foldor.exists()){
            if(foldor.listFiles().length > 0){
                for (File file : foldor.listFiles()){
                    file.delete();
                }
            }
            foldor.delete();
        }
    }

    private boolean checkMp3Files(File foldor){
        File[] fs = foldor.listFiles();
        if (fs == null) {
            return false;
        }
        for(File f:fs){
            if(f.isDirectory() && !checkMp3Files(f))	//若是目录，则递归该目录下的文件
                return false;
            if(f.isFile() && !isMp3File(f))		//若是文件，直接判断
                return false;
        }
        return true;
    }

    private boolean isMp3File(File file) {
        Long size = file.length();
        Long maxSize = 5L * 1024 * 1024; //5MB
        String fileName = file.getName();
        System.out.println(fileName);
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (type.equals(".mp3") && size <= maxSize) {
            return true;
        }
        return false;
    }
}