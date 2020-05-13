package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MissionJobDetailMapper {

    int insertSingleJobWhenTransfer(@Param("list") List<InspectionMissionJobDetail> list);
}
