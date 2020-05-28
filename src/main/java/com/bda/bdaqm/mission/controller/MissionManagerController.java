package com.bda.bdaqm.mission.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.MyRoleService;
import com.bda.bdaqm.admin.service.UserOprService;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
import com.bda.bdaqm.mission.service.MissionService;

import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.rabbitmq.model.VoiceResult;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.UZipFile;

import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(value = "/mission/manager", method = RequestMethod.POST)
public class MissionManagerController {

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MyRoleService myRoleService;

    @Autowired
    private UserOprService userService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @Value("#{mqconfig.mq_ready_queue}")
    private String readyQueueId;
    @Value("#{mqconfig.mq_update_queue}")
    private String updateQueueId;
    @Value("#{mqconfig.mq_check_queue}")
    private String checkQueueId;


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
        //计数器
        int totalNum = totalNum(unZipPath);
        return Result.success(tempDirName,totalNum);
    }

    @RequestMapping("/createSingleMission")
    @ResponseBody
    public Result createMission(@RequestParam(value = "missId",required = false,defaultValue = "")String missId,
                                @RequestParam("missionName")String missionName,
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
        if(missId.equals("")){
            //获取用户信息
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            String missionCreaterRole = myRoleService.getRoleNameByUserId(user.getUserId());

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
            missionModel.setMissionFilepath(uploadFilePath + dirName + "/" + "unZip");
            missionModel.setMissionCreaterid(Integer.valueOf(user.getUserId()));
            missionModel.setMissionCreaterRole(missionCreaterRole);
            //文件总数
            missionModel.setMissionTotalNum(missionTotalNum);

            //创建任务时，任务相关状态
            missionModel.setMissionStatus(0);
            missionModel.setMissionUploadStatus(0);
            missionModel.setMissionTransferStatus(0);
            missionModel.setMissionInspectionStatus(0);
            missionModel.setMissionType(1);

            int missionId = missionService.createMission(missionModel);
            if(missionId > 0 ){
                try {
                    missionService.addSingleJob(missionModel);
                }catch (SchedulerException e){
                    return Result.failure();
                }
                return Result.success();
            }else {
                return Result.failure();
            }
        }else {
            int missionId = 0;
            try {
                missionId = Integer.valueOf(missId);
            }catch (Exception e){
                return Result.failure();
            }
            //old
            InspectionMission inspectionMission = missionService.getMissionByMissionId(missionId);
            if(inspectionMission == null){
                return Result.failure();
            }
            if(inspectionMission.getMissionStatus()!= 0){
                return Result.failure();
            }
            if(missionService.isCurrentlyExe(missId)){
                return Result.failure();
            }
            //移除定时任务
            try {
                missionService.removeSingleJob(String.valueOf(missionId));
            }catch (SchedulerException e){
                return Result.failure();
            }
            inspectionMission.setMissionName(missionName);
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
            inspectionMission.setMissionBegintime(df.format(date));

            inspectionMission.setMissionLevel(missionLevel);
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            //语音文件路径,任务创建时，文件在本机上
            inspectionMission.setMissionFilepath(uploadFilePath + dirName + "/");
            //文件总数
            inspectionMission.setMissionTotalNum(missionTotalNum);

            //更新数据库
            if(missionService.updateSingleMission(inspectionMission) > 0){
                try {
                    missionService.addSingleJob(inspectionMission);
                }catch (SchedulerException e){
                    return Result.failure();
                }
                return Result.success();
            }else {
                return Result.failure();
            }
        }
    }


    /*常规任务的创建*/
    @RequestMapping("/createCommonMission")
    public Result createCommonMission(
            @RequestParam(value = "missId",required = false,defaultValue = "")String missId,
            @RequestParam("missionName")String missionName,
            @RequestParam("missionBegintime")String missionBegintime,
            @RequestParam("missionIstransfer")int missionIstransfer,
            @RequestParam("missionIsinspection")int missionIsinspection,
            @RequestParam("missionIstaboo")int missionIstaboo,
            @RequestParam("missionIsrisk")int missionIsrisk,
            @RequestParam("missionIsnodubious")int missionIsnodubious,
            @RequestParam("missionDescribe")String missionDescribe,
            @RequestParam("ftpPath")String ftpPath,
            @RequestParam("scanTime")String scanTime,
            @RequestParam("scanDay")String scanDay
            ){
        if(missId.equals("")){
            //获取用户信息
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            String missionCreaterRole = myRoleService.getRoleNameByUserId(user.getUserId());

            InspectionMission inspectionMission = new InspectionMission();
            inspectionMission.setMissionName(missionName);
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
            inspectionMission.setMissionBegintime(df.format(date));
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            //等级最高 ： 10
            inspectionMission.setMissionLevel(10);
            inspectionMission.setMissionCreaterid(Integer.valueOf(user.getUserId()));
            inspectionMission.setMissionCreaterRole(missionCreaterRole);
            //创建任务时，任务相关状态
            inspectionMission.setMissionStatus(0);
            inspectionMission.setMissionUploadStatus(0);
            inspectionMission.setMissionTransferStatus(0);
            inspectionMission.setMissionInspectionStatus(0);
            //标识常规任务
            inspectionMission.setMissionType(0);

            inspectionMission.setMissionFtp(ftpPath);
            String[] scantimes = scanTime.split(":");
            StringBuffer cycle = new StringBuffer();
            for(int i = scantimes.length-1;i>=0;i--){
                cycle.append(scantimes[i]);
                cycle.append(" ");
            }
            cycle.append("? * ");
            cycle.append(scanDay);
            inspectionMission.setMissionCycle(cycle.toString());
            int missionId = missionService.createMission(inspectionMission);
            if(missionId > 0){
                //添加定时任务
                try {
                    missionService.addCommonJob(inspectionMission);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                return Result.success();
            }
            return Result.failure();
        }else{
            int missionId = 0;
            try {
                missionId = Integer.valueOf(missId);
            }catch (Exception e){
                return Result.failure();
            }
            //old
            InspectionMission inspectionMission = missionService.getMissionByMissionId(missionId);
            if(inspectionMission == null){
                return Result.failure();
            }
            //写入对象中
            inspectionMission.setMissionName(missionName);
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            inspectionMission.setMissionFtp(ftpPath);
            String[] scantimes = scanTime.split(":");
            StringBuffer cycle = new StringBuffer();
            for(int i = scantimes.length-1;i>=0;i--){
                cycle.append(scantimes[i]);
                cycle.append(" ");
            }
            cycle.append("? * ");
            cycle.append(scanDay);
            if(!inspectionMission.getMissionBegintime().equals(missionBegintime) ||
                !inspectionMission.getMissionCycle().equals(cycle.toString())){
                if(missionService.isCurrentlyExe(missId)){
                    return Result.failure();
                }
                try {
                    missionService.removeCommonJob(missId);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                inspectionMission.setMissionBegintime(missionBegintime);
                inspectionMission.setMissionCycle(cycle.toString());
                if(missionService.updateCommonMission(inspectionMission) > 0){
                    try {
                        missionService.addCommonJob(inspectionMission);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                    return Result.success();
                }
                return Result.failure();
            }else {
                if(missionService.updateCommonMission(inspectionMission) > 0){
                    return Result.success();
                }else {
                    return Result.failure();
                }
            }
        }
    }

    //创建任务前调用
    @RequestMapping("/isCreateCommonMission")
    public Result isCreateCommonMission(){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        String userId = user.getUserId();
        String missionType = "0";
        List<InspectionMission> list = missionService.isCreateCommonMission(userId,missionType);
        if(list != null && list.size() > 0){
            return Result.failure();
        }
        return Result.success();
    }


    @RequestMapping("/getQuartz")
    public void getQuartz(){
        try {
            SchedulerUtils.getSingleJob();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }


    @RequestMapping("isAttach")
    public Result isAttach(@RequestParam(value = "ftpPath") String ftpPath){
            if(ftpPath == null || ftpPath.equals("")){
                return Result.failure(ResultCode.FTP_PATH_NULL);
            }
            String[] ftps = ftpPath.split("&|:|\\?");
            String path = "";
            String account = "";
            String pwd = "";
            int i = 0;
            for(String ftp : ftps){
                i++;
                switch (i){
                    case 2:
                        path = ftp;
                        break;
                    case 3:
                        account = ftp.substring(ftp.indexOf("=") + 1);
                        break;
                    case 4:
                        pwd = ftp.substring(ftp.indexOf("=") + 1);
                        break;
                }
            }
            if(ftps[0].equals("ftp")){
                FtpUtil ftpUtil = new FtpUtil(path,21,account,pwd);
                try {
                    ftpUtil.connect();
                    ftpUtil.disconnect();
                }catch (Exception e){
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
                return Result.success();
            }else {
                SFTPUtil3 sftpUtil3 = new SFTPUtil3(path,account,pwd);
                sftpUtil3.connect();
                if(sftpUtil3.getSftp().isConnected()){
                    return Result.success();
                }else {
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
            }
    }

    @RequestMapping("/queryMissonList")
    public Result queryMissonList(Page page,
                                  @RequestParam(value = "companyName",required = false)String companyName,
                                  @RequestParam(value = "planName",required = false)String planName,
                                  @RequestParam(value = "recordStartDate",required = false)String recordStartDate,
                                  @RequestParam(value = "recordEndDate",required = false)String recordEndDate){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<Map<String,Object>> resList = new ArrayList<>();
        resList.addAll(haveSonUsers(user.getAccount()));
        List<String> allUserIds = new ArrayList<>();
        for (Map<String,Object> map : resList){
            allUserIds.add(map.get("userId").toString());
        }
        allUserIds.add(user.getUserId());

        List<InspectionMission> listMission = missionService.getListMission(page,allUserIds,companyName,planName,recordStartDate,recordEndDate);
        PageInfo<InspectionMission> pageInfo = new PageInfo<>(listMission);
        return Result.success(new DataGrid(listMission, pageInfo.getTotal()));


        //List<InspectionMission> list = missionService.getListMission(Integer.valueOf(user.getUserId()));
        //return Result.success(list);
    }

    @RequestMapping("/missionPause")
    public void missionPause(@RequestParam("missionId")int missionId){
        missionService.updateMissionStatus(missionId, 0);
        updateReadyQueue();
    }

    @RequestMapping("/missionStart")
    public void missionStart(@RequestParam("missionId")int missionId){
        missionService.updateMissionStatus(missionId, 1);
        updateReadyQueue();
    }



    @RequestMapping("/missionCancel")
    public void missionCancel(@RequestParam("dirName")String dirName){
        deleteTemp(dirName);
    }

    @RequestMapping("/mqTest")
    public void mqTest(@RequestParam("path")String path, Integer pri){
        try {
            Map<String,String> map = new HashMap<>();
            map.put("id","24");
            map.put("missionId","45");
            map.put("name","zhishino2.mp3");
            map.put("path",path);
            rabbitmqProducer.sendQueue(readyQueueId + "_exchange", readyQueueId + "_patt",
                    map,9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/mqCheck")
    public String  mqPurge(String str){
        return "null";
    }

    @RequestMapping("/mqUpdate")
    public void mqUpdate(){
        try {
            updateReadyQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新ready队列
    private void updateReadyQueue() {
        rabbitmqProducer.sendQueue(updateQueueId+"_exchange", updateQueueId+"_patt", null);
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

    //先清空队列，然后查表根据任务状态添加任务至队列
    private void updateMQ() {

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

    private int totalNum(String path){
        int num = 0;
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                num = num + totalNum(file.getPath());
            }else {
                num++;
                System.out.println("???" + file.getPath());
            }
        }
        return num;
    }

    @RequestMapping("getTriggerState")
    public Result getTriggerState(String missionId){
        return Result.success(SchedulerUtils.getTriggerState(missionId));
    }

    //获取任务列表用；查询当前用户所创建的用户
    private List<Map<String,Object>> haveSonUsers(String create){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> users = userService.selectUsersAndRoleByCreate(create,"");
        if(users != null && users.size() > 0){
            for(Map map : users){
                //向下递归，查找当前用户所创建的用户及其子用户
                if(map.get("account") != null && !map.get("account").toString().equals("")){
                    result.addAll(haveSonUsers(map.get("account").toString()));
                }
            }
            result.addAll(users);
        }
        return result;
    }
}