package com.bda.bdaqm.electric.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.service.TicketTypeStatisticService;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping({ "electric/ticketTypeStatistic" })
public class TicketTypeStatisticController extends BaseController{
	@Autowired
	private TicketTypeStatisticService ticketTypeStatisticService;
	
	/**
	 * 合格票数，规范票数，票种数量按月份统计
	 * @param updateTimeBegin
	 * @param updateTimeEnd
	 * @param unit
	 * @return
	 */
	@RequestMapping("getStatistic")
	@ResponseBody
	public Object getStatistic(String updateTimeBegin,String updateTimeEnd ,String unit) {
		Map<String,List<String>> map = ticketTypeStatisticService.statisticTicketType(updateTimeBegin,updateTimeEnd,unit);
		return map;
	}
	
	/**
	 * 工作票延期数量，时间按月份统计
	 * @param updateTimeBegin
	 * @param updateTimeEnd
	 * @param unit
	 * @return
	 */
	@RequestMapping("getDelayStatistic")
	@ResponseBody
	public Object getDelayStatistic(String updateTimeBegin,String updateTimeEnd ,String unit) {
		Map<String,List<String>> map = ticketTypeStatisticService.getDelayData(updateTimeBegin,updateTimeEnd,unit);
		return map;
	}
	
	@RequestMapping("getExtrlDelayStatistic")
	@ResponseBody
	public Object getExtrlDelayStatistic(String updateTimeBegin,String updateTimeEnd ) {
		Map<String,List<String>> map = ticketTypeStatisticService.getExtrlDelayData(updateTimeBegin,updateTimeEnd);
		return map; 
	} 
	
	
	@RequestMapping("statisticTypeForUnit")
	@ResponseBody
	public Object statisticTypeForUnit(String updateTimeBegin,String updateTimeEnd) {
		List<Map<String,Object>> list = ticketTypeStatisticService.statisticTypeForUnit(updateTimeBegin,updateTimeEnd);
		return list;
	} 
	
	@RequestMapping("statisticTypeForAllUnit")
	@ResponseBody
	public Object statisticTypeForAllUnit(String updateTimeBegin,String updateTimeEnd) {
		List<Map<String,Object>> list = ticketTypeStatisticService.statisticTypeForAll(updateTimeBegin,updateTimeEnd);
		return list;
	} 
	
	
	@RequestMapping("statisticPrincipal")
	@ResponseBody
	public Object statisticPrincipal(String updateTimeBegin,String updateTimeEnd ) {
		Map<String,List<String>> map = ticketTypeStatisticService.statisticPrincipal(updateTimeBegin,updateTimeEnd);
		return map;
	}
	
	@RequestMapping("statictisState")
	@ResponseBody
	public Object statictisState(String updateTimeBegin,String updateTimeEnd ) {
		Map<String,List<String>> map = ticketTypeStatisticService.statictisState(updateTimeBegin,updateTimeEnd);
		return map;
	}
	
	@RequestMapping("getOrigina")
	@ResponseBody
	public Object getOrigina(Page page,String flag,String staFlag,String ticketType,String fullPath,String delayFlag,String time,
			String monthEnd ,String unit) {
		List<AnalysisStatistic> list = ticketTypeStatisticService.selectDetailDialog(flag,staFlag,ticketType,fullPath,delayFlag,time,monthEnd,unit);
		
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
	@RequestMapping("getOriginaAllUnit")
	@ResponseBody
	public Object getOriginaAllUnit(Page page,String index,String name,String isExtrl,String ticketType,String monthBegin,String monthEnd,String lastFlag ) {
		List<AnalysisStatistic> list = ticketTypeStatisticService.selectDetailDialogAllUnit(index,name,isExtrl,ticketType,monthBegin,monthEnd,lastFlag);
		
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
