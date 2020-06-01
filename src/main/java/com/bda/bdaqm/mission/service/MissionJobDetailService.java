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

    public List<InspectionMissionJobDetail> getListByMissionId(int missionId){
        return missionJobDetailMapper.getListByMissionId(missionId);
    }

    public InspectionMissionJobDetail getByJobId(int jobId) {
        return missionJobDetailMapper.getByJobId(jobId);
    }

    public void updateTransferStatus(int jobId, int fileHasTransfer, int fileStatus, String fileStatusDescribe, int isTransferFailed) {
        missionJobDetailMapper.updateTransferStatus(jobId, fileHasTransfer, fileStatus, fileStatusDescribe, isTransferFailed);
    }

    public void updateInspectionStatus(int jobId, int fileHasInspection, int fileStatus, String fileStatusDescribe) {
        missionJobDetailMapper.updateInspectionStatus(jobId, fileHasInspection, fileStatus, fileStatusDescribe);
    }
}
