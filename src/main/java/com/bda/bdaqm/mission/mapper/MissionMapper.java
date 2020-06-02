package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MissionMapper extends Mapper<InspectionMission> {
    int createMission(InspectionMission params);
    List<InspectionMission> getListMission(@Param("allUserIds") List<String> allUserIds,@Param("companyName") String companyName,@Param("planName") String planName,
                                           @Param("recordStartDate") String recordStartDate,@Param("recordEndDate") String recordEndDate);
    List<InspectionMission> getListMissionByStatus(@Param("missionStatus") int missionStatus);
    void updateMissionStatus(@Param("missionId") int missionId, @Param("missionStatus") int missionStatus);
    void updateMissionUploadStatus(@Param("missionId") int missionId, @Param("missionStatus") int missionStatus);
    void updateMissionTransferStatus(@Param("missionId") int missionId, @Param("missionStatus") int missionStatus);
    void updateMissionInspectionStatus(@Param("missionId") int missionId, @Param("missionStatus") int missionStatus);
    InspectionMission getMissionByMissionId(@Param("missionId")int missionId);
    int updateSingleMission(InspectionMission params);
    List<InspectionMission> isCreateCommonMission(@Param("userId")String userId,@Param("missionType")String missionType);
    int updateCommonMission(InspectionMission params);
}


