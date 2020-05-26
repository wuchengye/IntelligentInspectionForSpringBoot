package com.bda.bdaqm.mission.controller;

import afu.org.checkerframework.checker.igj.qual.I;
import com.alibaba.fastjson.JSONObject;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
import com.bda.bdaqm.mission.service.MissionService;

import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.rabbitmq.model.VoiceResult;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.UZipFile;

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

        return Result.success(tempDirName);
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
        //验证连接
        if(missId.equals("")){
            //获取用户信息
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();

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
            }

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

    @RequestMapping("/getListMission")
    public Result getListMission(){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<InspectionMission> list = missionService.getListMission(Integer.valueOf(user.getUserId()));
        return Result.success(list);
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
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",System.currentTimeMillis());
            map.put("missionId",1);
            map.put("name","aaa");
            map.put("path",path);
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            rabbitmqProducer.sendQueue(readyQueueId + "_exchange", readyQueueId + "_patt", map, pri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/mqCheck")
    public String  mqPurge(String str){
        Map<String,String> checkMap = new HashMap<>();
        checkMap.put("mp3FilePath", "/2/qwe/yy.mp3");
        checkMap.put("xmlFilePath", createXMLFile("D:\\newXML_2020525.xml", resolve(str)));
        rabbitmqProducer.sendQueue(checkQueueId+"_exchange", checkQueueId+"_patt", checkMap);
        return "success";
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

    //生成xml文件
    private String createXMLFile(String path, Map<String,String> map) {
        String textStr1 = map.get("text1");
        String textStr2 = map.get("text2");
        String timeStr1 = map.get("time1");
        String timeStr2 = map.get("time2");

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            // 不显示standalone="no"
            //document.setXmlStandalone(true);

            Element result = document.createElement("result");
            Element instance = document.createElement("instance");
            Element subject = document.createElement("subject");

            instance.setAttribute("waveuri", "./wav/qwert.wav");
            subject.setAttribute("value", "search");

            //客服 n0
            Element channel_n0 = document.createElement("channel");
            Element function_n0 = document.createElement("function");
            Element text1 = document.createElement("text");
            Element time1 = document.createElement("time");

            channel_n0.setAttribute("no", "n0");
            text1.setTextContent(textStr1);
            time1.setTextContent(timeStr1);

            function_n0.appendChild(text1);
            function_n0.appendChild(time1);
            channel_n0.appendChild(function_n0);
            subject.appendChild(channel_n0);

            //客户 n1
            Element channel_n1 = document.createElement("channel");
            Element function_n1 = document.createElement("function");
            Element text2 = document.createElement("text");
            Element time2 = document.createElement("time");

            channel_n1.setAttribute("no", "n1");
            text2.setTextContent(textStr2);
            time2.setTextContent(timeStr2);

            function_n1.appendChild(text2);
            function_n1.appendChild(time2);
            channel_n1.appendChild(function_n1);
            subject.appendChild(channel_n1);

            instance.appendChild(subject);
            result.appendChild(instance);
            document.appendChild(result);

            // 创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            // 创建 Transformer对象
            Transformer tf = tff.newTransformer();

            // 输出内容是否使用换行
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            // 创建xml文件并写入内容
            tf.transform(new DOMSource(document), new StreamResult(new File(path)));
            System.out.println("生成book1.xml成功");
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成book1.xml失败");
            return null;
        }
    }

    private Map<String, String> resolve(String str) {
        //客服text1,time1  客户text2,time2
        StringBuilder text1 = new StringBuilder();
        StringBuilder time1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();
        StringBuilder time2 = new StringBuilder();

        //转成VoiceResult对象
        JSONObject jsonObject=JSONObject.parseObject(str);
        VoiceResult voiceResult = (VoiceResult) JSONObject.toJavaObject(jsonObject, VoiceResult.class);
        //csNum 区分哪个是客服
        String csNum = voiceResult.getResult().getCustomer_service();
        //对话列表
        List<VoiceResult.ResultBean.ContentBean> contentList = voiceResult.getResult().getContent();
        for (VoiceResult.ResultBean.ContentBean content : contentList
        ) {
            if (content.getSpeaker().equals(csNum)) {
                //添加到客服
                text1.append(content.getOnebest().replace(" ", ""))
                        .append(" ");
                time1.append(content.getBg())
                        .append(",")
                        .append(content.getEd())
                        .append(" ");
            } else {
                //添加到客户
                text2.append(content.getOnebest().replace(" ", ""))
                        .append(" ");
                time2.append(content.getBg())
                        .append(",")
                        .append(content.getEd())
                        .append(" ");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("text1", text1.toString().trim());
        map.put("time1", time1.toString().trim());
        map.put("text2", text2.toString().trim());
        map.put("time2", time2.toString().trim());
        return map;
    }
}