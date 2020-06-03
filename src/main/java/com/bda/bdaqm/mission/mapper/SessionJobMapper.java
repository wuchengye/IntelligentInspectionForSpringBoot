package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SessionJobMapper {
    void insertSessionJob(@Param("sessionId") String sessionId, @Param("jobId") int jobId);
}
