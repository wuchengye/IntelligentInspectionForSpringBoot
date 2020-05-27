package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MissionMapper extends Mapper<InspectionMission> {
    int createMission(InspectionMission params);
    List<InspectionMission> getListMission(@Param("userId") int userId);
    List<InspectionMission> getListMissionByStatus(@Param("missionStatus") int missionStatus);
    void updateMissionStatus(@Param("missionId") int missionId, @Param("missionStatus") int missionStatus);
    InspectionMission getMissionByMissionId(@Param("missionId")int missionId);
    int updateSingleMission(InspectionMission params);
    List<InspectionMission> isCreateCommonMission(@Param("userId")String userId,@Param("missionType")String missionType);
    int updateCommonMission(InspectionMission params);
}


