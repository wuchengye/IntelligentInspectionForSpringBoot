package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.model.FunctionLocationName;
import com.bda.bdaqm.util.ComboBoxItem;

import tk.mybatis.mapper.common.Mapper;

public interface AnalysisStatisticMapper extends Mapper<AnalysisStatistic>{
	public List<AnalysisStatistic> selectDateByCondition(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	//public int insertDataToTemp(String beginTime,String endTime);
	public int insertDataToTemp(@Param("list")List<AnalysisStatistic> list);
	
	public List<AnalysisStatistic> getUnitNumAndCheck(@Param("unitStr")String unitStr,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> getKVNumAndCheck(@Param("unitStr")String unitStr,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> getQCCenterByNuit(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> getGroupNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> getPowerNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> otherDeptNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<Map<String,Object>> otherPersonNumAndCheck(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<Map<String,Object>> getOpenRoomNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<Map<String,Object>> getLineNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<Map<String,Object>> getStationNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<Map<String,Object>> getMajorNumAndCheck(@Param("unit")String unit,@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> getDeviceNumAndCheck(@Param("unitStr")String unitStr,@Param("unit")String unit,
			@Param("beginTime")String beginTime,@Param("endTime")String endTime);
	
	public List<AnalysisStatistic> analysisFunctionName(@Param("beginTime")String beginTime,
			@Param("endTime")String endTime);
	
	public int insertFunctionLocationName(@Param("flnList")List<FunctionLocationName> flnList);
	
	public List<ComboBoxItem> getUnitComboxSql();
}
