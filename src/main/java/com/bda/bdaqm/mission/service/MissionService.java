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
    public void quartzMission(InspectionMission mission) throws SchedulerException {
        SchedulerUtils.startScheduler(mission);
    }
}
