package com.bda.bdaqm.mission.service;

import com.bda.bdaqm.mission.mapper.MissionMapper;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService {

    @Autowired
    private MissionMapper missionMapper;

    public int createMission(InspectionMission params){
        return missionMapper.createMission(params);
    }

    public List<InspectionMission> getListMission(Page page, List<String> allUserIds, String companyName, String planName, String recordStartDate, String recordEndDate){
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return missionMapper.getListMission(allUserIds,companyName,planName,recordStartDate,recordEndDate);
    }

    public List<InspectionMission> getListMissionByStatus(int missionStatus){
        return missionMapper.getListMissionByStatus(missionStatus);
    }

    public void updateMissionStatus(int missionId, int status) {
        missionMapper.updateMissionStatus(missionId, status);
    }

    public List<InspectionMission> isCreateCommonMission(String userId,String missionType){
        return missionMapper.isCreateCommonMission(userId,missionType);
    }

    public void updateMissionUploadStatus(int missionId,int status){
        missionMapper.updateMissionUploadStatus(missionId,status);
    }


    //定时任务
    public void addSingleJob(InspectionMission mission) throws SchedulerException {
        SchedulerUtils.addSingleJob(mission);
    }

    //根据任务id查找
    public InspectionMission getMissionByMissionId(int missionId){
        return missionMapper.getMissionByMissionId(missionId);
    }

    //移除定时任务
    public void removeSingleJob(String missionId) throws SchedulerException {
        SchedulerUtils.removeSingleJob(missionId);
    }

    public void removeCommonJob(String missionId) throws SchedulerException{
        SchedulerUtils.removeCommonJob(missionId);
    }

    //修改单次任务
    public int updateSingleMission(InspectionMission params){
        return missionMapper.updateSingleMission(params);
    }

    //修改常规任务
    public int updateCommonMission(InspectionMission params){
        return missionMapper.updateCommonMission(params);
    }
    //常规定时任务
    public void addCommonJob(InspectionMission mission) throws SchedulerException{
        SchedulerUtils.addCommonJob(mission);
    }

    //是否正在运行任务
    public boolean isCurrentlyExe(String missionId){
        return SchedulerUtils.isCurrentlyExe(missionId);
    }
}
