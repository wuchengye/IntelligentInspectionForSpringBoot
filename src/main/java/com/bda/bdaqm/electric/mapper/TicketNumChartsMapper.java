package com.bda.bdaqm.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.TicketNumNorm;

public interface TicketNumChartsMapper {
	
	public String getUnitNumCount(@Param("unitStr")String unitStr,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
}
