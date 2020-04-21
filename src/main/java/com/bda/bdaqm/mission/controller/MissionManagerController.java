package com.bda.bdaqm.mission.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.service.MissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/mission/manager")
public class MissionManagerController {

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Autowired
    private MissionService missionService;

    @RequestMapping("/uploadFile")
    @ResponseBody
    /*上传文件，接收临时文件夹id*/
    public Result uploadFile(@RequestParam("files") CommonsMultipartFile[] files, @RequestParam("dirName")String dirName){
        String path = uploadFilePath + dirName + "\\";
        File foldor = new File(path);
        if(foldor.exists()){
            foldor.delete();
        }
        foldor.mkdirs();
        //文件有效数
        int sum = 0;
        for (int i = 0; i < files.length; i++){
                String fileName = files[i].getOriginalFilename();
                String type = fileName.substring(fileName.lastIndexOf("."));
                if(type.equals(".mp3")){
                    File file = new File(path + fileName);
                    try {
                        sum++;
                        files[i].transferTo(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sum--;
                    }
                }
            }
        return Result.success("上传完成:" + sum + "/" + files.length,path);
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
        missionModel.setMissionFilepath(uploadFilePath + dirName + "\\");
        missionModel.setMissionCreaterid(Integer.valueOf(user.getUserId()));
        //文件总数
        missionModel.setMissionTotalNum(missionTotalNum);

        //创建任务时，任务相关状态
        missionModel.setMissionStatus(0);
        missionModel.setMissionUploadStatus(0);
        missionModel.setMissionTransferStatus(0);
        missionModel.setMissionInspectionStatus(0);

        if(missionService.createMission(missionModel) == 1){
            return Result.success();
        }else {
            return Result.failure();
        }
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

    private void deleteTemp(String id){
        String path = uploadFilePath + id + "\\";
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

}