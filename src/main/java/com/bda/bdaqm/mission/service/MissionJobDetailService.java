package com.bda.bdaqm.mission.service;

import com.bda.bdaqm.mission.mapper.MissionJobDetailMapper;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionJobDetailService {
    @Autowired
    private MissionJobDetailMapper missionJobDetailMapper;

    public int insertSingleJobWhenTransfer(List<InspectionMissionJobDetail> list){
        return missionJobDetailMapper.insertSingleJobWhenTransfer(list);
    }
}
