package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MissionJobDetailMapper {

    int insertSingleJobWhenTransfer(@Param("list") List<InspectionMissionJobDetail> list);
    List<InspectionMissionJobDetail> getListByMissionId(@Param("missionId") int missionId);
    InspectionMissionJobDetail getByJobId(@Param("jobId") int jobId);
    void updateTransferStatus(@Param("jobId") int jobId, @Param("fileHasTransfer") int fileHasTransfer,
                              @Param("fileStatus") int fileStatus, @Param("fileStatusDescribe") String fileStatusDescribe,
                              @Param("isTransferFailed") int isTransferFailed);
    void updateInspectionStatus(@Param("jobId") int jobId, @Param("fileHasInspection") int fileHasInspection,
                              @Param("fileStatus") int fileStatus, @Param("fileStatusDescribe") String fileStatusDescribe);
}
