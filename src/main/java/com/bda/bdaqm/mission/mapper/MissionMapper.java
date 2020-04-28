package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MissionMapper extends Mapper<InspectionMission> {
    int createMission(InspectionMission params);
    List<InspectionMission> getListMission(@Param("userId") int userId);
}
