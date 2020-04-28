package com.bda.bdaqm.electric.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.service.StatisticsService;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.controller.BaseController;

@Controller
@RequestMapping({ "electric/organization" })
public class UnitController extends BaseController{
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("getComboxTree")
	@ResponseBody
	public Object getComboxTree() {
		
		long t1 = System.currentTimeMillis();
		List<Map<String,Object>> treeList = statisticsService.queryComboTree();
		long t2 = System.currentTimeMillis();
		
		logger.info("获取单位树所消耗时间："+(t2-t1)+"毫秒");
		return treeList;
	}
	
	@RequestMapping("getCombox")
	@ResponseBody
	public Object getCombox() {
		List<ComboBoxItem> treeList = statisticsService.getComboxSql();
		return treeList;
	}
	
	@RequestMapping("getZWCombox")
	@ResponseBody
	public Object getZWCombox(String unitType) {
		List<ComboBoxItem> treeList = statisticsService.getZWComboxSql(unitType);
		return treeList;
	}
	
	
}
