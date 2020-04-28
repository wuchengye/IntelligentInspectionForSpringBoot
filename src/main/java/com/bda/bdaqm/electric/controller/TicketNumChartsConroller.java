package com.bda.bdaqm.electric.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.electric.service.TicketNumChartsService;
import com.bda.bdaqm.electric.service.TicketTypeStatisticService;
import com.bda.common.bean.OperaterResult;

@Controller
@RequestMapping("electric/ticketCheck")
public class TicketNumChartsConroller {
	
	@Autowired
	private TicketNumChartsService ticketNumChartsService;
	
	@Autowired
	private TicketTypeStatisticService ticketTypeService;
	
	/**
	 * 根据单位获取工作票数和合格率规范率
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getUnitNumAndCheck")
	@ResponseBody
	public Object getUnitNumAndCheck(@RequestParam(required=false,value="units[]")List<String> units,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getUnitNumAndCheck(units, beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 根据单位获取工作票延期数量和延期时间
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getUnitDelayNumTime")
	@ResponseBody
	public Object getUnitDelayNumTime(@RequestParam(required=false,value="units[]")List<String> units,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getUnitDelayNumTime(units, beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 获取按电压等级统计工作票数量
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getKVLevelNum")
	@ResponseBody
	public Object getKVLevelNum(String units,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getKVLevelNum(units,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 巡检中心工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getQCCenterByNuit")
	@ResponseBody
	public Object getQCCenterByNuit(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getQCCenterByNuit(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 各班组工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getGroupNumAndCheck")
	@ResponseBody
	public Object getGroupNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getGroupNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 供电所工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getPowerNumAndCheck")
	@ResponseBody
	public Object getPowerNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getPowerNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("otherDeptNumAndCheck")
	@ResponseBody
	public Object otherDeptNumAndCheck(String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.otherDeptNumAndCheck(beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("otherPersonNumAndCheck")
	@ResponseBody
	public Object otherPersonNumAndCheck(String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.otherPersonNumAndCheck(beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("getOpenRoomNumAndCheck")
	@ResponseBody
	public Object getOpenRoomNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getOpenRoomNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("getLineNumAndCheck")
	@ResponseBody
	public Object getLineNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getLineNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("getMajorNumAndCheck")
	@ResponseBody
	public Object getMajorNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getMajorNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("getDeviceNumAndCheck")
	@ResponseBody
	public Object getDeviceNumAndCheck(@RequestParam(required=false,value="units[]")List<String> units,String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getDeviceNumAndCheck(units,unit, beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	@RequestMapping("getStationNumAndCheck")
	@ResponseBody
	public Object getStationNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> map = ticketNumChartsService.getStationNumAndCheck(unit,beginTime, endTime);
		return new OperaterResult<Map<String,Object>>(true,"OK",map);
	}
	
	/**
	 * 插入数据到统计分析图表的中间表
	 * 设置定时任务，每天获取前一天终结的工作票，将数据插入到中间表
	 */
	@RequestMapping("insertDataToTemp")
	@ResponseBody
	public void insertDataToTemp(String beginTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
		calendar.add(Calendar.DATE,-1);  //当前时间减去一天，即一天前的时间
		if(StringUtils.isBlank(beginTime)){
			beginTime = sdf.format(calendar.getTime());
		}
		if(StringUtils.isBlank(endTime)){
			endTime = sdf.format(calendar.getTime());
		}
		ticketNumChartsService.insertDataToTemp(beginTime,endTime);
		
		//此中间表入库，时间维度为数据更新时间
		ticketTypeService.insertStateStatistic(beginTime,endTime);
	}
	/**
	 * 解析function_location_name字段
	 * 将开关房、线路等字段解析出来存到中间表
	 */
	@RequestMapping("analysisFunctionName")
	@ResponseBody
	public void analysisFunctionName(String beginTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
		calendar.add(Calendar.DATE,-1);  //当前时间减去一天，即一天前的时间
		if(StringUtils.isBlank(beginTime)){
			beginTime = sdf.format(calendar.getTime());
		}
		if(StringUtils.isBlank(endTime)){
			endTime = sdf.format(calendar.getTime());
		}
		ticketNumChartsService.analysisFunctionName(beginTime,endTime);
	}
	
	@RequestMapping("openDialog")
	@ResponseBody
	public ModelAndView openDialog(String beginTime,String endTime,String unit,String url,String seriesIndex,
			String qcCenter,String group){
		ModelAndView m = new ModelAndView("electric/ticketAmountStatistics/exploreDialog");
		m.addObject("beginTime", beginTime);
		m.addObject("endTime", endTime);
		m.addObject("unit", unit);
		m.addObject("url", url);
		m.addObject("seriesIndex", seriesIndex);
		m.addObject("qcCenter",qcCenter);
		m.addObject("group", group);
		return m;
	}

}
