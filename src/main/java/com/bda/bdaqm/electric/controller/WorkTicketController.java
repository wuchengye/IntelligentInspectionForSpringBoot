package com.bda.bdaqm.electric.controller;


import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.runner.manipulation.Sortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bda.bdaqm.electric.model.WorkTicket;
import com.bda.bdaqm.electric.service.WorkTaskHandler;
import com.bda.bdaqm.electric.service.WorkTicketService;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;

@Controller
@RequestMapping("electric/workticket")
public class WorkTicketController extends BaseController {

	@Autowired
	private WorkTicketService workTicketService;
	
	/**
	 * 获取标题
	 * @return
	 */
	@RequestMapping("getTitles")
	@ResponseBody
	public List<ComboBoxItem> getTitles(){
		return workTicketService.getTitles();
	}
	
	/**
	 * 保存工作票
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveTicket")
	@ResponseBody
	public Object saveTicket(WorkTicket ticket){
		int result = 0;
		ticket.setTicketType("厂站第一种工作票");
		result = workTicketService.add(ticket);
		
		return new OperaterResult<>(result > 0);
	}
	
	/**
	 * 获取工作任务
	 * @param title
	 * @return
	 */
	@RequestMapping("getTaskByTitle")
	@ResponseBody
	public List<ComboBoxItem> getTaskByTitle(String title){
		return workTicketService.getTaskByTitle(title);
	}
	
	/**
	 * 获取作业类型
	 * @param ticketType 工作票种类
	 * @return
	 */
	@RequestMapping("getWorkType")
	@ResponseBody
	public List<String> getWorkType(String ticketType){
		return workTicketService.getWorkType(ticketType);
	}
	
	/**
	 * 生成填充内容
	 * @param title  标题
	 * @param task   工作任务
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("genarate")
	@ResponseBody
	public Object getContent(String title,String task) throws Exception{
		
		String templatePath = request.getSession().getServletContext().getRealPath("/assets/config")+File.separatorChar+"wrong_words.json";
		logger.info("json配置文件路径：" + templatePath);
		String s = WorkTaskHandler.readJsonData(templatePath);
		//JSONObject js = (JSONObject) JSONObject.parse(s);
		LinkedHashMap<String, Object> json = JSON.parseObject(s,LinkedHashMap.class, Feature.OrderedField);
		JSONObject jsonObject=new JSONObject(true);
		jsonObject.putAll(json);
		for(Entry<String, Object> entry : jsonObject.entrySet()) {
			//System.out.println(entry.getKey());
			Pattern pattern = Pattern.compile(entry.getKey());
			Matcher matcher = pattern.matcher(task);
			while(matcher.find()){
				task = task.replace(matcher.group(), (String)entry.getValue());
			}
		}
		//获取作业类型
		List<String> workTypes = this.getWorkType(WorkTicketService.STATION_FIRST_TYPE);
		
		String workType = WorkTaskHandler.checkWorkType(workTypes, task);
		if(WorkTaskHandler.ERROR.equals(workType)){
			return  new OperaterResult<>(true,"工作类型有误！");
		}
		
		String expr =  WorkTaskHandler.generateCondition(task,workType);//生成查询条件
		if(expr.equals("none")||expr.equals("nothing")) {
			return  new OperaterResult<>(true,"工作任务中开关编号有误！");
		}
		String temp = "";
		if(task.indexOf("CT") > -1){
			temp += "CT";
		}
		task = task.replace("检修", "").replace("CT", "");
		String dis = "";
		String isl = "";
		String brk = "";
		boolean flag = compare(workType);
		List<String> discnts = null;
		List<String> breakers = null;
		List<String> isLanding = null;
		if(flag) {
			discnts = workTicketService.getDisconnectorOpen(title, expr);
			breakers = workTicketService.getBreakerOpen(title, expr);
			isLanding = workTicketService.getIsLandingOpen(title, expr);
			if(discnts.isEmpty()&&breakers.isEmpty()&&isLanding.isEmpty()) {
				return  new OperaterResult<>(true,"工作任务中开关编号有误！");
			}else if(breakers.size()>1||breakers.isEmpty()) {
				return  new OperaterResult<>(true,"工作任务中开关编号有误！");
			}
		}else {
			if("主变本体检修".equals(workType)){
				flag = true;
			}else{
				flag = false;
			}
			List<String> equipNum = workTicketService.getEquipNumber(title, expr);
			discnts = workTicketService.getDisconnector(title, expr, equipNum, flag);
			breakers = workTicketService.getBreaker(title, expr, flag);
			isLanding = workTicketService.getIsLanding(title, expr, equipNum, flag);
		}
		if(breakers.size() > 0){
			for (int i = 0; i < breakers.size(); i++) {
				if(brk.length() > 0){
					brk += "、";
				}
				brk += breakers.get(i);
			}
		}else{
			brk = "无";
		}
		if( !"无".equals(brk) && task.indexOf("(") > -1 && !task.contains(brk) ){//如果包含括号，则校验断路器
			return  new OperaterResult<>(true,"断路器有误！");
		}
		
		
		if(discnts.size() > 0){
			for (int i = 0; i < discnts.size(); i++) {
				if(dis.length() > 0){
					dis += "、";
				}
				dis += discnts.get(i);
			}
		}else{
			dis = "无";
		}
		
		if(isLanding.size() > 0){
			for (int i = 0; i < isLanding.size(); i++) {
				if(isl.length() > 0){
					isl += "、";
				}
				isl += isLanding.get(i);
			}
		}else{
			isl = "无";
		}
		
		if("无".equals(brk) && "无".equals(dis) && "无".equals(isl) ){
			return  new OperaterResult<>(true,"查不到相关安全措施，请检查工作任务是否填写正确。");
		}
		
		if("开关投产前试验".equals(workType) && dis.indexOf("DZ") > -1){
			temp = "开关小车";
		}else if("开关投产前试验".equals(workType) && dis.indexOf("DZ") == -1){
			temp = "开关B修";
		}else if("开关检修".equals(workType) || "开关防拒动检查".equals(workType)){
			temp = "开关检修";
		}else if("主变本体检修".equals(workType)){
			temp = "主变本体检修";
		}else if ("保护定检".equals(workType)){
			if(task.contains("站用变")){
				temp = "保护定检站用变";
			}else if(task.contains("电容器组")){
				temp = "保护定检电容器组";
				task = task.replace(workType, "");
			}else if(task.contains("母线分段")){
				temp = "保护定检母线分段";
			}else if(task.contains("开关")){
				temp = "保护定检开关";
				task = task.replaceAll("\\d+开关", "").replace(workType, "");
			}
			
		}
		List<Map<String,String>> content = workTicketService.getContent(task+temp); 
		for (Map<String, String> map : content) {
			map.put("CONTENT_1", formateStr(map.get("CONTENT_1"),task));
			map.put("CONTENT_2",isl + formateStr(map.get("CONTENT_2"),task));
			map.put("NEED_SET_SIGN",formateStr(map.get("NEED_SET_SIGN"),task));
		}
		if(content == null || content.size() == 0){//如果没有模板
			return  new OperaterResult<>(true,"无对应安全措施生成模板！");
		}
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("brks", brk);
		m.put("dis", dis);
		
		content.add(m);
		
		return  new OperaterResult<List<Map<String,String>>>(true,"OK",content);
	}
	
	private String formateStr(String str, String task){
		if(StringUtils.isNotBlank(str)){
			return str.replace("source", task);
		}
		return "";
	}
	private boolean compare(String workType) {
		boolean flag = true;
		if(!workType.equals("开关投产前试验")&&!workType.equals("开关B修")&&!workType.equals("开关检修")&&!workType.equals("开关防拒动检查")) {
			flag = false;
		}
		return flag;
	}
	
}
