package com.bda.bdaqm.mission.mapper;

import com.bda.bdaqm.mission.model.BdaqmTotal;
import org.apache.ibatis.annotations.Param;

public interface TotalMapper {
    int insertTotal(BdaqmTotal params);
}
