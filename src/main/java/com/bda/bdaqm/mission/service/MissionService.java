package com.bda.bdaqm.mission.service;

import com.bda.bdaqm.mission.mapper.MissionMapper;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
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

    public List<InspectionMission> getListMission(int userId){
        return missionMapper.getListMission(userId);
    }

    public List<InspectionMission> getListMissionByStatus(int missionStatus){
        return missionMapper.getListMissionByStatus(missionStatus);
    }

    public void updateMissionStatus(int missionId, int status) {
        missionMapper.updateMissionStatus(missionId, status);
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

    //修改单次任务
    public int updateSingleMission(InspectionMission params){
        return missionMapper.updateSingleMission(params);
    }

    //常规定时任务
    public void addCommonJob(InspectionMission mission) throws SchedulerException{
        SchedulerUtils.addCommonJob(mission);
    }
}
