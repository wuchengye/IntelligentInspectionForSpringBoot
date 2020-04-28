package com.bda.bdaqm.electric.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.TicketExploreMapper;
import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class TicketExploreService {
	
	@Autowired
	private TicketExploreMapper ticketExploreMapper;
	
	public  List<AnalysisStatistic> getUnitNumAndCheck(String beginTime,String endTime,String unit,String seriesIndex){
		return ticketExploreMapper.getUnitNumAndCheck(beginTime, endTime, unit,seriesIndex);
	}

	public List<AnalysisStatistic> getUnitDelayNumTime(String beginTime,String endTime,String unit,String seriesIndex){
		return ticketExploreMapper.getUnitDelayNumTime(beginTime, endTime, unit,seriesIndex);
	}
	
	public List<AnalysisStatistic> getKVLevelNum(String beginTime,String endTime,String unit,String seriesIndex){
		if("0.4KV".equals(seriesIndex)||"380V".equals(seriesIndex)){
			seriesIndex = "0.4KV|380V";
		}else if("10KV".equals(seriesIndex)||"20KV".equals(seriesIndex)){
			seriesIndex = "(^\\S{0,}[^0-9]"+seriesIndex+")|(^"+seriesIndex+")";
		}
		return ticketExploreMapper.getKVLevelNum(beginTime, endTime, unit,seriesIndex);
	}
	
	public List<AnalysisStatistic> getQCCenterByNuit(String beginTime,String endTime,String unit,String qcCenter,String seriesIndex){
		return ticketExploreMapper.getQCCenterByNuit(beginTime,endTime,unit,qcCenter,seriesIndex);
	}
	
	public List<AnalysisStatistic> getGroupNumAndCheck(String beginTime,String endTime,String unit,String group,String seriesIndex){
		return ticketExploreMapper.getGroupNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
	}
	public List<AnalysisStatistic> getPowerNumAndCheck(String beginTime,String endTime,String unit,String qcCenter,String seriesIndex){
		return ticketExploreMapper.getPowerNumAndCheck(beginTime,endTime,unit,qcCenter,seriesIndex);
	}
	
	public List<AnalysisStatistic> otherDeptNumAndCheck(String beginTime,String endTime,String unit,String seriesIndex){
		return ticketExploreMapper.otherDeptNumAndCheck(beginTime,endTime,unit,seriesIndex);
	}
	
	public List<AnalysisStatistic> otherPersonNumAndCheck(String beginTime,String endTime,String userName,String seriesIndex){
		return ticketExploreMapper.otherPersonNumAndCheck(beginTime,endTime,userName,seriesIndex);
	}
	
	public List<AnalysisStatistic> getOpenRoomNumAndCheck(String beginTime,String endTime,String unit,String group,String seriesIndex){
		return ticketExploreMapper.getOpenRoomNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
	}
	
	
	public List<AnalysisStatistic> getLineNumAndCheck(String beginTime,String endTime,String unit,String group,String seriesIndex){
		return ticketExploreMapper.getLineNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
	}
	
	public List<AnalysisStatistic> getStationNumAndCheck(String beginTime,String endTime,String unit,String group,String seriesIndex){
		return ticketExploreMapper.getLineNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
	}
	
	public List<AnalysisStatistic> getMajorNumAndCheck(String beginTime,String endTime,String unit,String seriesIndex){
		return ticketExploreMapper.getMajorNumAndCheck(beginTime,endTime,unit,seriesIndex);
	}
	
	public List<AnalysisStatistic> getDeviceNumAndCheck(String beginTime,String endTime,String unit,String seriesIndex,String group){
		return ticketExploreMapper.getDeviceNumAndCheck(beginTime,endTime,unit,seriesIndex,group);
	}
}
