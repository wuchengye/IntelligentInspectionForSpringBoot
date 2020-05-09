package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.util.PropertyMgr;
import com.bda.bdaqm.util.SFTPUtil3;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MissionJob implements Job {

    private String missionId;
    private String missionName;
    private String missionFilepath;
    private String missionTotalNum;

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionFilepath() {
        return missionFilepath;
    }

    public void setMissionFilepath(String missionFilepath) {
        this.missionFilepath = missionFilepath;
    }

    public String getMissionTotalNum() {
        return missionTotalNum;
    }

    public void setMissionTotalNum(String missionTotalNum) {
        this.missionTotalNum = missionTotalNum;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        setMissionId(jobDataMap.getString("missionId"));
        setMissionFilepath(jobDataMap.getString("missionFilepath"));
        setMissionName(jobDataMap.getString("missionName"));


        //sFTP
        String ip = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_IP);
        String name = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_NAME);
        String pwd = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PWD);
        String sftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
        int port = Integer.parseInt(PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PORT));
        SFTPUtil3 sftp = new SFTPUtil3(ip, port, name, pwd);

        String remotePath = getMissionId() + "/" + new Date().toString();
        sftp.bacthUploadFile(remotePath,getMissionFilepath());
    }
}
