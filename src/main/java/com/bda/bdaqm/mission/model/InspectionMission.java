package com.bda.bdaqm.mission.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table
public class InspectionMission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * mission_id 任务Id
     */
    @Id
    private Integer missionId;

    /**
     * mission_name 任务名称
     */
    private String missionName;

    /**
     * mission_describe 任务描述
     */
    private String missionDescribe;

    /**
     * mission_type 任务类型  0：常规任务  1：单次任务
     */
    private Integer missionType;

    /**
     * mission_status 任务状态 0：未开始  1：进行中  2：已完成 3:暂停
     */
    private Integer missionStatus;

    /**
     * mission_begintime 任务开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String missionBegintime;

    /**
     * mission_taboo 服务禁语数量
     */
    private Integer missionTaboo;

    /**
     * mission_istaboo 是否导出服务禁语  0：否  1：是
     */
    private Integer missionIstaboo;

    /**
     * mission_risk 投诉风险数量
     */
    private Integer missionRisk;

    /**
     * mission_isrisk 是否导出投诉风险  0：否  1：是
     */
    private Integer missionIsrisk;

    /**
     * mission_nodubious 非可疑数据数量
     */
    private Integer missionNodubious;

    /**
     * mission_isnodubious 是否导出非可疑数据  0：否  1：是
     */
    private Integer missionIsnodubious;

    /**
     * mission_remaining 任务剩余时间 进行中：时间、未开始："" or null、已完成：1
     */
    private String missionRemaining;

    /**
     * mission_upload_status 上传状态 完成文件数
     */
    private Integer missionUploadStatus;

    /**
     * mission_transfer_status 转写状态 完成文件数
     */
    private Integer missionTransferStatus;

    /**
     * mission_inspection_status 质检状态 完成文件数
     */
    private Integer missionInspectionStatus;

    /**
     * mission_level 任务优先级
     */
    private Integer missionLevel;

    /**
     * mission_istransfer 是否转写 0：否 1：是
     */
    private Integer missionIstransfer;

    /**
     * mission_istnspection 是否质检 0:否 1:是
     */
    private Integer missionIsinspection;

    /**
     * mission_filepath 任务文件夹路径
     */
    private String missionFilepath;

    /**
     * mission_createrid 任务创建者Id
     */
    private Integer missionCreaterid;

    /**
     * mission_totalnum 任务文件总数
     */
    private Integer missionTotalNum;

    /**
     * mission_phasedcompletionnum 阶段完成文件数
     */
    private Integer missionPhasedCompletionNum;

    /**
     * mission_ftp 常规任务远程服务器
     */
    private String missionFtp;

    /**
     * mission_cycle 常规任务周期
     */
    private String missionCycle;

    public void setMissionTotalNum(Integer missionTotalNum) {
        this.missionTotalNum = missionTotalNum;
    }

    public void setMissionPhasedCompletionNum(Integer missionPhasedCompletionNum) {
        this.missionPhasedCompletionNum = missionPhasedCompletionNum;
    }

    public Integer getMissionTotalNum() {
        return missionTotalNum;
    }

    public Integer getMissionPhasedCompletionNum() {
        return missionPhasedCompletionNum;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getMissionDescribe() {
        return missionDescribe;
    }

    public Integer getMissionType() {
        return missionType;
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public String getMissionBegintime() {
        return missionBegintime;
    }

    public Integer getMissionTaboo() {
        return missionTaboo;
    }

    public Integer getMissionIstaboo() {
        return missionIstaboo;
    }

    public Integer getMissionRisk() {
        return missionRisk;
    }

    public Integer getMissionIsrisk() {
        return missionIsrisk;
    }

    public Integer getMissionNodubious() {
        return missionNodubious;
    }

    public Integer getMissionIsnodubious() {
        return missionIsnodubious;
    }

    public String getMissionRemaining() {
        return missionRemaining;
    }

    public Integer getMissionUploadStatus() {
        return missionUploadStatus;
    }

    public Integer getMissionTransferStatus() {
        return missionTransferStatus;
    }

    public Integer getMissionInspectionStatus() {
        return missionInspectionStatus;
    }

    public Integer getMissionLevel() {
        return missionLevel;
    }

    public Integer getMissionIstransfer() {
        return missionIstransfer;
    }

    public Integer getMissionIsinspection() {
        return missionIsinspection;
    }

    public String getMissionFilepath() {
        return missionFilepath;
    }

    public Integer getMissionCreaterid() {
        return missionCreaterid;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public void setMissionDescribe(String missionDescribe) {
        this.missionDescribe = missionDescribe;
    }

    public void setMissionType(Integer missionType) {
        this.missionType = missionType;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }

    public void setMissionBegintime(String missionBegintime) {
        this.missionBegintime = missionBegintime;
    }

    public void setMissionTaboo(Integer missionTaboo) {
        this.missionTaboo = missionTaboo;
    }

    public void setMissionIstaboo(Integer missionIstaboo) {
        this.missionIstaboo = missionIstaboo;
    }

    public void setMissionRisk(Integer missionRisk) {
        this.missionRisk = missionRisk;
    }

    public void setMissionIsrisk(Integer missionIsrisk) {
        this.missionIsrisk = missionIsrisk;
    }

    public void setMissionNodubious(Integer missionNodubious) {
        this.missionNodubious = missionNodubious;
    }

    public void setMissionIsnodubious(Integer missionIsnodubious) {
        this.missionIsnodubious = missionIsnodubious;
    }

    public void setMissionRemaining(String missionRemaining) {
        this.missionRemaining = missionRemaining;
    }

    public void setMissionUploadStatus(Integer missionUploadStatus) {
        this.missionUploadStatus = missionUploadStatus;
    }

    public void setMissionTransferStatus(Integer missionTransferStatus) {
        this.missionTransferStatus = missionTransferStatus;
    }

    public void setMissionInspectionStatus(Integer missionInspectionStatus) {
        this.missionInspectionStatus = missionInspectionStatus;
    }

    public void setMissionLevel(Integer missionLevel) {
        this.missionLevel = missionLevel;
    }

    public void setMissionIstransfer(Integer missionIstransfer) {
        this.missionIstransfer = missionIstransfer;
    }

    public void setMissionIsinspection(Integer missionIsinspection) {
        this.missionIsinspection = missionIsinspection;
    }

    public void setMissionFilepath(String missionFilepath) {
        this.missionFilepath = missionFilepath;
    }

    public void setMissionCreaterid(Integer missionCreaterid) {
        this.missionCreaterid = missionCreaterid;
    }

    public String getMissionFtp() {
        return missionFtp;
    }

    public void setMissionFtp(String missionFtp) {
        this.missionFtp = missionFtp;
    }

    public String getMissionCycle() {
        return missionCycle;
    }

    public void setMissionCycle(String missionCycle) {
        this.missionCycle = missionCycle;
    }
}