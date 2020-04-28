package com.bda.bdaqm.electric.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.model.ThreePerson;
import com.bda.bdaqm.electric.service.TicketExploreService;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("electric/ticketExplore")
public class TicketExploreController {
	
	@Autowired
	private TicketExploreService ticketExploreService;
	
	@RequestMapping("getUnitNumAndCheck")
	@ResponseBody
	public Object getUnitNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getUnitNumAndCheck(beginTime, endTime, unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());

	}
	
	@RequestMapping("getUnitDelayNumTime")
	@ResponseBody
	public Object getUnitDelayNumTime(Page page,String beginTime,String endTime,String unit,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getUnitDelayNumTime(beginTime, endTime, unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getKVLevelNum")
	@ResponseBody
	public Object getKVLevelNum(Page page,String beginTime,String endTime,String unit,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getKVLevelNum(beginTime, endTime, unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getQCCenterByNuit")
	@ResponseBody
	public Object getQCCenterByNuit(Page page,String beginTime,String endTime,String unit,String qcCenter,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getQCCenterByNuit(beginTime,endTime,unit,qcCenter,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getGroupNumAndCheck")
	@ResponseBody
	public Object getGroupNumAndCheck(Page page,String beginTime,String endTime,String unit,String group,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getGroupNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getPowerNumAndCheck")
	@ResponseBody
	public Object getPowerNumAndCheck(Page page,String unit,String beginTime,String endTime,String qcCenter,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.getPowerNumAndCheck(beginTime,endTime,unit,qcCenter,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("otherDeptNumAndCheck")
	@ResponseBody
	public Object otherDeptNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.otherDeptNumAndCheck(beginTime,endTime,unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("otherPersonNumAndCheck")
	@ResponseBody
	public Object otherPersonNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex){
		List<AnalysisStatistic> list = ticketExploreService.otherPersonNumAndCheck(beginTime,endTime,unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getOpenRoomNumAndCheck")
	@ResponseBody
	public Object getOpenRoomNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex,String group){
		unit = unit.substring(2);
		List<AnalysisStatistic> list = ticketExploreService.getOpenRoomNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getLineNumAndCheck")
	@ResponseBody
	public Object getLineNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex,String group){
		unit = unit.substring(2);
		List<AnalysisStatistic> list = ticketExploreService.getLineNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getStationNumAndCheck")
	@ResponseBody
	public Object getStationNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex,String group){
		List<AnalysisStatistic> list = ticketExploreService.getStationNumAndCheck(beginTime,endTime,unit,group,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getMajorNumAndCheck")
	@ResponseBody
	public Object getMajorNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex,String group){
		List<AnalysisStatistic> list = ticketExploreService.getMajorNumAndCheck(beginTime,endTime,unit,seriesIndex);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("getDeviceNumAndCheck")
	@ResponseBody
	public Object getDeviceNumAndCheck(Page page,String beginTime,String endTime,String unit,String seriesIndex,String group){
		List<AnalysisStatistic> list = ticketExploreService.getDeviceNumAndCheck(beginTime,endTime,unit,seriesIndex,group);
		List<AnalysisStatistic> resList = new ArrayList<AnalysisStatistic>();
		if( list!=null && !list.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > list.size() ) {
				max = list.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(list.get(i));
			}
		}
		PageInfo<AnalysisStatistic> pager = new PageInfo<AnalysisStatistic>(list);
		return new DataGrid(resList, pager.getTotal());
	}
	
}
