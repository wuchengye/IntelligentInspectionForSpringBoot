package com.bda.bdaqm.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.AnalysisStatistic;

import tk.mybatis.mapper.common.Mapper;

public interface TicketExploreMapper extends Mapper<AnalysisStatistic>{
	public List<AnalysisStatistic> getUnitNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> getUnitDelayNumTime(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> getKVLevelNum(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex);

	public List<AnalysisStatistic> getQCCenterByNuit(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("qcCenter")String qcCenter,
			@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> getGroupNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("group")String group,
			@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic>  getPowerNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("qcCenter")String qcCenter,
			@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> otherDeptNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex);

	public List<AnalysisStatistic> otherPersonNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("userName")String userName,@Param("seriesIndex")String seriesIndex);
		
	public List<AnalysisStatistic> getOpenRoomNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("group")String group,
			@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> getLineNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("lineName")String lineName,
			@Param("seriesIndex")String seriesIndex);
	
	/*public List<AnalysisStatistic> getStationNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("lineName")String lineName,
			@Param("seriesIndex")String seriesIndex);*/
	
	public List<AnalysisStatistic> getMajorNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex);
	
	public List<AnalysisStatistic> getDeviceNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime,@Param("unit")String unit,@Param("seriesIndex")String seriesIndex,
			@Param("group")String group);
}
