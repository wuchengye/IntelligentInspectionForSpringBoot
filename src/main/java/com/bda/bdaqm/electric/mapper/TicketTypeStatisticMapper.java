package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.model.TicketTypeStatistic;

import tk.mybatis.mapper.common.Mapper;

public interface TicketTypeStatisticMapper extends Mapper<TicketTypeStatistic>{
	
	//public List<TicketTypeStatistic> statisticQualified();
	
	//按月份统计合格票数，不合格票数，规范票数，不规范票数
	//按月份统计不同票种的数量，
	//按月份统计局内负责人，施工单位拥有的票数
	public List<TicketTypeStatistic> statisticTicketType(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd ,@Param("unit")String unit);
	
	//查询所有的数据，通过程序计算每张工作票延期的时间
	public List<AnalysisStatistic> getDelayData(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd,@Param("unit")String unit);
	
	//工作类型统计（全局）外协单位工作票数量统计
	public List<Map<String,Object>>  getExtriDelayData(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );
	
	//工作类型统计（全局）主配网各单位工作票数量统计
	public List<Map<String,Object>> statisticTypeForUnit(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );
	
	//工作类型统计（全局） 局内负责人和施工单位负责 人工作票数量统计
	public List<Map<String,Object>>	statisticTypeForAll(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );
	
	//工作类型统计（全局）主配网各单位局内负责人和施工单位负责 人工作票数量统计
	public List<Map<String,Object>>	statisticPrincipal(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );
	
	//工作类型统计（全局）主配网各单位局内负责人和施工单位负责 人工作票数量统计
	public List<Map<String,Object>>	statisticPermission(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );

	//工作类型统计（全局）各类状态工作票数量统计
	public List<Map<String,Object>>	statictisState(@Param("beginMonth")String updateTimeBegin,@Param("endMonth")String updateTimeEnd );
	
	//工作票状态中间表入库
	public void insertStateStatistic(@Param("beginTime")String updateTimeBegin,@Param("endTime")String updateTimeEnd);

	//工作类型统计（区局）弹窗
	public List<AnalysisStatistic> selectDetailDialog(@Param("flag")String flag,@Param("staFlag")String staFlag,@Param("ticketType")String ticketType,@Param("fullPath")String fullPath,@Param("isDelay")String delayFlag,@Param("endMonth")String endMonth,@Param("unit")String unit);
	
	//工作类型统计（全局）点击图表弹窗
	public List<AnalysisStatistic> selectAllTypeDialog(@Param("typeFlag")String typeFlag,@Param("isExtrl")String isExtrl,@Param("unit")String unit,@Param("ticketType")String ticketType,@Param("endMonth") String endMonth , @Param("beginMonth") String beginMonth,@Param("isDelay")String isDelay);
	
	//工作类型统计（全局）点击图表弹窗
	public List<AnalysisStatistic> selectAllTypePermissionDialog(@Param("unit")String unit,@Param("endMonth") String endMonth , @Param("beginMonth") String beginMonth);

	//工作类型统计（全局）各类状态工作票统计图表弹窗
	public List<AnalysisStatistic> selectStateDialog(@Param("stateType")String stateType,@Param("endMonth") String endMonth , @Param("beginMonth") String beginMonth);
}
