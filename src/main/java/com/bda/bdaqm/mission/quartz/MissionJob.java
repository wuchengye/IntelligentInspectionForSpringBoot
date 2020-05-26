package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;

import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.StringUtils;
import io.swagger.models.auth.In;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MissionJob implements Job {

    @Autowired
    private MissionJobDetailService missionJobDetailService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @Value("#{mqconfig.mq_ready_queue}")
    private String queueId;

    @Value("#{ftpconfig.ftpPath}")
    private String ftpPath;

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


        //sFTP
        String ip = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_IP);
        String name = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_NAME);
        String pwd = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PWD);
        String sftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
        int port = Integer.parseInt(PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PORT));
        SFTPUtil3 sftp = new SFTPUtil3(ip, port, name, pwd);

        String remotePath = ftpPath;
        Map<String,List> result = sftp.bacthUploadFile(remotePath,jobDataMap.getString("missionFilepath") ,uploadFilePath);
        List<InspectionMissionJobDetail> listSuc = new ArrayList<>();
        List<InspectionMissionJobDetail> listFai = new ArrayList<>();
        if(result.get("success") != null){
            List<File> sucList = result.get("success");
            for (File suc : sucList){
                InspectionMissionJobDetail inspectionMissionJobDetail = new InspectionMissionJobDetail();
                inspectionMissionJobDetail.setMissionId(Integer.valueOf(jobDataMap.getString("missionId")));
                inspectionMissionJobDetail.setMissionIstransfer(Integer.valueOf(jobDataMap.getString("missionIstransfer")));
                inspectionMissionJobDetail.setMissionIsinspection(Integer.valueOf(jobDataMap.getString("missionIsinspection")));
                inspectionMissionJobDetail.setFileName(suc.getName());
                inspectionMissionJobDetail.setFilePath(SFTPUtil3.ftpChildPath(suc.getPath(),uploadFilePath));
                inspectionMissionJobDetail.setFileStatus(1);
                inspectionMissionJobDetail.setMissionLevel(Integer.valueOf(jobDataMap.getString("missionLevel")));
                listSuc.add(inspectionMissionJobDetail);
            }
        }
        if(result.get("failure") != null){
            List<File > faiList = result.get("failure");
            for (File fai : faiList){
                InspectionMissionJobDetail inspectionMissionJobDetail = new InspectionMissionJobDetail();
                inspectionMissionJobDetail.setMissionId(Integer.valueOf(jobDataMap.getString("missionId")));
                inspectionMissionJobDetail.setMissionIstransfer(Integer.valueOf(jobDataMap.getString("missionIstransfer")));
                inspectionMissionJobDetail.setMissionIsinspection(Integer.valueOf(jobDataMap.getString("missionIsinspection")));
                inspectionMissionJobDetail.setFileName(fai.getName());
                inspectionMissionJobDetail.setFilePath(remotePath + fai);
                inspectionMissionJobDetail.setFileStatus(0);
                inspectionMissionJobDetail.setFileStatusDescribe("上传文件到ftp服务器失败");
                inspectionMissionJobDetail.setMissionLevel(Integer.valueOf(jobDataMap.getString("missionLevel")));
                listFai.add(inspectionMissionJobDetail);
            }
        }
        if(listSuc.size() > 0 && jobDataMap.getString("missionIstransfer").equals("1")){
            int insert = missionJobDetailService.insertSingleJobWhenTransfer(listSuc);
            if(insert > 0){
                //插入转写等待队列
                for (InspectionMissionJobDetail jobDetail : listSuc){
                    Map<String,String> map = new HashMap<>();
                    map.put("id",jobDetail.getJobId().toString());
                    map.put("missionId",jobDetail.getMissionId().toString());
                    map.put("name",jobDetail.getFileName());
                    map.put("path", StringUtils.split(ftpPath + jobDetail.getFilePath()));
                    rabbitmqProducer.sendQueue(queueId + "_exchange", queueId + "_patt",
                            map,jobDetail.getMissionLevel());
                }
            }
        }
        if(listFai.size() > 0){
            missionJobDetailService.insertSingleJobWhenTransfer(listFai);
        }
    }

}
