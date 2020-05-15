package com.bda.bdaqm.mission.model;

import java.io.Serializable;

public class InspectionMissionJobDetail implements Serializable {

    /**
     * job_id
     */
    private Integer jobId;

    /**
     * mission_id
     */
    private Integer missionId;

    /**
     * file_name
     */
    private String fileName;

    /**
     * file_path
     */
    private String filePath;

    /**
     * result_path
     */
    private String resultPath;

    /**
     * mission_istransfer
     */
    private Integer missionIstransfer;

    /**
     * mission_isinspection
     */
    private Integer missionIsinspection;

    /**
     * file_hastransfer
     */
    private Integer fileHastransfer;
    /**
     * file_hasinspection
     */
    private Integer fileHasinspection;

    /**
     * file_status
     */
    private Integer fileStatus;

    /**
     * file_status_describe
     */
    private String fileStatusDescribe;

    /**
     * mission_level
     */
    private Integer missionLevel;

    /**
     * is_transfer_failed
     */
    private Integer isTransferFailed;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public Integer getMissionIstransfer() {
        return missionIstransfer;
    }

    public void setMissionIstransfer(Integer missionIstransfer) {
        this.missionIstransfer = missionIstransfer;
    }

    public Integer getMissionIsinspection() {
        return missionIsinspection;
    }

    public void setMissionIsinspection(Integer missionIsinspection) {
        this.missionIsinspection = missionIsinspection;
    }

    public Integer getFileHastransfer() {
        return fileHastransfer;
    }

    public void setFileHastransfer(Integer fileHastransfer) {
        this.fileHastransfer = fileHastransfer;
    }

    public Integer getFileHasinspection() {
        return fileHasinspection;
    }

    public void setFileHasinspection(Integer fileHasinspection) {
        this.fileHasinspection = fileHasinspection;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileStatusDescribe() {
        return fileStatusDescribe;
    }

    public void setFileStatusDescribe(String fileStatusDescribe) {
        this.fileStatusDescribe = fileStatusDescribe;
    }

    public Integer getMissionLevel() {
        return missionLevel;
    }

    public void setMissionLevel(Integer missionLevel) {
        this.missionLevel = missionLevel;
    }

    public Integer getIsTransferFailed() {
        return isTransferFailed;
    }

    public void setIsTransferFailed(Integer isTransferFailed) {
        this.isTransferFailed = isTransferFailed;
    }
}