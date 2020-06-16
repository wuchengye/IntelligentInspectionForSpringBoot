package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.StringUtils;
import com.bda.bdaqm.websocket.WebsocketController;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.*;
import java.util.*;

@Component
@DisallowConcurrentExecution
public class MissionCommonJob implements Job {
    @Autowired
    private MissionJobDetailService missionJobDetailService;

    @Value("#{mqconfig.mq_ready_queue}")
    private String queueId;

    @Value("#{ftpconfig.ftpPath}")
    private String ftpPath;

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Autowired
    private MissionService missionService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        InspectionMission inspectionMission = missionService.getMissionByMissionId(Integer.valueOf(jobDataMap.getString("missionId")));

        if (inspectionMission.getMissionStatus() != 3) {
            //任务的文件上传状态改为1，进行中
            //missionService.updateMissionUploadStatus(inspectionMission.getMissionId(),1);

            String ftp = inspectionMission.getMissionFtp();
            //遍历ftp或sftp结果
            Map<String, List> map = new HashMap<>();
            //差集
            Map<String, List> lessMap = new HashMap<>();
            //记录地址
            String logPath = uploadFilePath + "CommonMission" + "/" + "Logs" + "/" + inspectionMission.getMissionId() + ".txt";
            //临时中转地址
            String tempPath = uploadFilePath + "CommonMission" + "/" + inspectionMission.getMissionId() + "/";
            //sftp服务器地址
            String remotePath = ftpPath;
            if (splitFtp(ftp, 1).equals("ftp")) {
                System.out.println("是ftp");
                FtpUtil ftpUtil = new FtpUtil(splitFtp(ftp, 2), 21, splitFtp(ftp, 3), splitFtp(ftp, 4));
                try {
                    ftpUtil.connect();
                    FTPFile[] files = ftpUtil.getFtpClient().listFiles();
                    for (FTPFile file : files) {
                        System.out.println("ftp量" + file.getName());
                    }

                    map = this.getFtpMapList("/", ftpUtil.getFtpClient());
                    System.out.println("列表后：" + map.get("path").size() + "," + map.get("name").size());
                    Map<String, List> lessAllMap = this.getLessMap(map, logPath);
                    List<String> path = lessAllMap.get("path");
                    List<String> name = lessAllMap.get("name");
                    System.out.println("差集：" + path.size() + "," + name.size());
                    if (path.size() > 0) {
                        File folder = new File(tempPath);
                        System.out.println("临时文件夹" + folder.exists());
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        for (int i = 0; i < path.size(); i++) {
                            boolean flag = false;
                            try {
                                flag = ftpUtil.downloadFiles(tempPath, path.get(i), new Date().getTime() + "_" + i + "_" + name.get(i), name.get(i));
                            } catch (Exception e) {
                                System.out.println("下载出错");
                            }
                            System.out.println(path.get(i) + name.get(i));
                            if (!flag) {
                                path.remove(i);
                                name.remove(i);
                            }
                        }
                        lessMap.put("path", path);
                        lessMap.put("name", name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ftpUtil.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("是sftp");
                SFTPUtil3 sftpUtil3 = new SFTPUtil3(splitFtp(ftp, 2), splitFtp(ftp, 3), splitFtp(ftp, 4));
                try {
                    sftpUtil3.connect();
                    ChannelSftp sftp = sftpUtil3.getSftp();
                    if (sftp.isConnected()) {
                        map = this.getSftpMapList("/", sftp);
                        Map<String, List> lessAllMap = this.getLessMap(map, logPath);
                        List<String> path = lessAllMap.get("path");
                        List<String> name = lessAllMap.get("name");
                        if (path.size() > 0) {
                            File folder = new File(tempPath);
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            for (int i = 0; i < path.size(); i++) {
                                boolean flag = false;
                                try {
                                    flag = sftpUtil3.downloadFile(path.get(i), name.get(i), tempPath, new Date().getTime() + "_" + i + "_" + name.get(i));
                                } catch (Exception e) {
                                    System.out.println("下载失败");
                                }
                                System.out.println(path.get(i) + name.get(i));
                                if (!flag) {
                                    path.remove(i);
                                    name.remove(i);
                                }
                            }
                            //Map<String,List> result = sftpUtil3.bacthUploadFile(remotePath,tempPath,uploadFilePath);
                            lessMap.put("path", path);
                            lessMap.put("name", name);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sftpUtil3.disconnect();
                }
            }

            if (!lessMap.isEmpty()) {
                //下载完成，从服务器上传到sftp服务器
                System.out.println("进入上传阶段");
                //任务的文件上传状态改为1，进行中
                missionService.updateMissionUploadStatus(inspectionMission.getMissionId(),1);
                String ip = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_IP);
                String name = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_NAME);
                String pwd = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PWD);
                String sftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
                int port = Integer.parseInt(PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PORT));
                SFTPUtil3 sftp = new SFTPUtil3(ip, port, name, pwd);
                Map<String, List> result = sftp.bacthUploadFile(remotePath, tempPath, uploadFilePath);
                System.out.println("上传阶段完成");

                List<InspectionMissionJobDetail> listSuc = new ArrayList<>();
                List<InspectionMissionJobDetail> listFai = new ArrayList<>();
                if (result.get("success") != null) {
                    List<File> sucList = result.get("success");
                    System.out.println("进入上传成功：" + sucList.size());
                    for (File suc : sucList) {
                        String[] fileType = suc.getName().split("\\.");
                        System.out.println("比对是否mp3");
                        if (fileType.length > 0 && fileType[fileType.length - 1].equals("mp3")) {
                            System.out.println("进入MP3");
                            InspectionMissionJobDetail jobDetail = new InspectionMissionJobDetail();
                            jobDetail.setMissionId(inspectionMission.getMissionId());
                            jobDetail.setMissionIstransfer(inspectionMission.getMissionIstransfer());
                            jobDetail.setMissionIsinspection(inspectionMission.getMissionIsinspection());
                            jobDetail.setFileName(suc.getName());
                            jobDetail.setFilePath(remotePath + SFTPUtil3.ftpChildPath(suc.getPath(), uploadFilePath));
                            jobDetail.setFileStatus(1);
                            jobDetail.setMissionLevel(inspectionMission.getMissionLevel());
                            listSuc.add(jobDetail);
                        }
                    }
                }
                if (listSuc.size() > 0 && inspectionMission.getMissionIstransfer() == 1) {
                    System.out.println("进入任务文件插入");
                    int insert = missionJobDetailService.insertSingleJobWhenTransfer(listSuc);
                    if (insert > 0) {
                        //任务状态：进行中
                        missionService.updateMissionStatus(Integer.valueOf(jobDataMap.getString("missionId")),1);
                        //插入转写等待队列
                        System.out.println("进入队列");
                        for (InspectionMissionJobDetail jobDetail : listSuc) {
                            Map<String, String> mq = new HashMap<>();
                            mq.put("id", jobDetail.getJobId().toString());
                            mq.put("missionId", jobDetail.getMissionId().toString());
                            mq.put("name", jobDetail.getFileName());
                            mq.put("path", StringUtils.split(jobDetail.getFilePath()));
                            rabbitmqProducer.sendQueue(queueId + "_exchange", queueId + "_patt",
                                    mq, jobDetail.getMissionLevel());
                        }
                    }else {
                        missionService.updateMissionStatus(Integer.valueOf(jobDataMap.getString("missionId")),2);
                    }
                }else {
                    missionService.updateMissionStatus(Integer.valueOf(jobDataMap.getString("missionId")),2);
                }
                missionService.updateMissionUploadStatus(inspectionMission.getMissionId(),2);

                //websocket
                WebsocketController.updateMission(missionService.getMissionByMissionId(inspectionMission.getMissionId()));
                //删除临时文件夹
                deleteTemp(tempPath);
                //添加记录
                addLogs(logPath, lessMap);
            }
        }
    }

    //递归ftp服务器，返回文件所在文件夹和文件名
    private Map<String, List> getFtpMapList(String pathName, FTPClient ftpClient) throws IOException {
        Map<String ,List> map = new HashMap<>();
        List<String> path = new LinkedList<>();
        List<String> name = new LinkedList<>();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    path.add(pathName);
                    name.add(file.getName());
                    System.out.println("ftp列表:" + pathName+file.getName());
                } else if (file.isDirectory()) {
                    // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        Map<String,List> map1 = getFtpMapList(pathName + file.getName() + "/", ftpClient);
                        path.addAll(map1.get("path"));
                        name.addAll(map1.get("name"));
                    }
                }
            }
        }
        map.put("path",path);
        map.put("name",name);
        return map;
    }

    //递归遍历sftp服务器
    private Map<String,List> getSftpMapList(String pathName,ChannelSftp sftp) throws SftpException {
        Map<String ,List> map = new HashMap<>();
        List<String> path = new LinkedList<>();
        List<String> name = new LinkedList<>();
        boolean flag = openDir(pathName,sftp);
        if(flag){
            Vector v = sftp.ls(pathName);
            if(v != null && v.size() > 0){
                for (Object object : v){
                    ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)object;
                    String filename=entry.getFilename();
                    if(".".equals(filename) || "..".equals(filename)){
                        continue;
                    }
                    if(openDir(pathName+filename+"/",sftp)){
                        Map<String,List> map1 = getSftpMapList(pathName+filename+"/",sftp);
                        path.addAll(map1.get("path"));
                        name.addAll(map1.get("name"));
                    }else {
                        path.add(pathName);
                        name.add(filename);
                    }
                }
            }
        }
        map.put("path",path);
        map.put("name",name);
        return map;
    }

    //进入sftp指定目录
    private boolean openDir(String directory,ChannelSftp sftp){
        try{
            sftp.cd(directory);
            return true;
        }catch(SftpException e){
            return false;
        }
    }


    //分割ftp字符串
    private String splitFtp(String ftpPath,int re){
        String[] ftps = ftpPath.split("&|:|\\?");
        String ftpOrSftp = "";
        String path = "";
        String account = "";
        String pwd = "";
        int i = 0;
        for(String ftp : ftps){
            i++;
            switch (i){
                case 1:
                    ftpOrSftp = ftp;
                    if(re == 1){
                        return ftpOrSftp;
                    }
                    break;
                case 2:
                    path = ftp;
                    if(re == 2){
                        return path;
                    }
                    break;
                case 3:
                    account = ftp.substring(ftp.indexOf("=") + 1);
                    if(re == 3){
                        return account;
                    }
                    break;
                case 4:
                    pwd = ftp.substring(ftp.indexOf("=") + 1);
                    if(re == 4){
                        return pwd;
                    }
                    break;
            }
        }
        return "";
    }

    //查找记录文件
    private boolean isFindCommonLog(String logPath){
        File file = new File(logPath);
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }

    //记录文件的读取
    private ArrayList<String> toArrayByFileReader1(File file) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    //返回差集信息
    private Map<String,List> getLessMap(Map<String,List> map,String logPath){
        if(this.isFindCommonLog(logPath)){
            File file = new File(logPath);
            ArrayList<String> logs = this.toArrayByFileReader1(file);
            List<String> path = map.get("path");
            List<String> name = map.get("name");
            List<String> rePath = new LinkedList<>();
            List<String> reName = new LinkedList<>();

            for(int i = 0; i< path.size(); i++){
                boolean flag = true;
                for(String log : logs){
                    if(log.equals(path.get(i) + name.get(i))){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    rePath.add(path.get(i));
                    reName.add(name.get(i));
                }
            }
            Map<String,List> reMap = new HashMap<>();
            reMap.put("path",rePath);
            reMap.put("name",reName);
            return reMap;
        }else {
            return map;
        }
    }

    //删除临时文件夹
    private void deleteTemp(String tempPath){
        String path = tempPath;
        File foldor = new File(path);
        if(foldor.exists()){
            if(foldor.listFiles().length > 0){
                for (File file : foldor.listFiles()){
                    file.delete();
                }
            }
        }
    }

    //添加记录
    private void addLogs(String logPath,Map<String,List> map){
        List<String> path = map.get("path");
        List<String> name = map.get("name");
        FileWriter fw = null;
        try {
            String logParent = StringUtils.split(logPath);
            File folder = new File(logParent);
            if(!folder.exists()){
                folder.mkdirs();
            }
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(logPath);
            if(!f.exists()){
                f.createNewFile();
            }
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i< path.size();i ++){
            pw.println(path.get(i) + name.get(i));
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
