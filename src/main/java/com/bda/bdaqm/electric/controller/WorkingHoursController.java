package com.bda.bdaqm.electric.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.electric.model.HourModulus;
import com.bda.bdaqm.electric.service.WorkingHoursService;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping({"electric/workingHours"})
public class WorkingHoursController extends BaseController{

	@Autowired
	private WorkingHoursService workingHousService;
	
	@RequestMapping("getCountOfTicket")
	@ResponseBody
	public Object getCountOfTicket(Page page,
			@RequestParam(value = "unitName", required = false)String unitName, 
			@RequestParam(value = "sumStartTime", required = false)String sumStartTime, 
			@RequestParam(value = "sumEndTime", required = false)String sumEndTime,
			@RequestParam(value = "orgId", required = false)String orgId,
			@RequestParam(value = "level", required = false)String level) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("unitName", unitName);
		params.put("sumStartTime", sumStartTime);
		params.put("sumEndTime", sumEndTime);
		params.put("orgId", orgId);
		params.put("level", level);
		
		Date d1 = new Date();
		List<Map<String, Object>> allData = workingHousService.getCountOfTicket(params);
		logger.info("工时统计 ：查询工作负责人持有票数耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
		
		List<Map<String, Object>> resData = new ArrayList<Map<String, Object>>();
		d1 = new Date();
		List<Map<String, Object>> allDetailData = workingHousService.getDetailOfAll(params);
		logger.info("工时统计 ：查询工作负责人持有票详情耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
		
		d1 = new Date();
		for (Map<String, Object> map : allData) {
			float f = 0;
			List<Map<String, Object>> personData = workingHousService.getDetailOfPerson(allDetailData, (String)map.get("person_name"), (String)map.get("unit_name"));
			float stationOneHours = 0,stationTwoHours = 0, stationThreeHours = 0,
					lineOneHours = 0, lineTwoHours = 0, fireOneHours = 0, fireTwoHours = 0,
					liveWorkingHours = 0, lowVoltageHours = 0, urgentRepairsHours = 0,
					writtenFormHours = 0 , specialHours = 0;
			
			for (Map<String, Object> pd : personData) {
				
				if(null == pd.get("ticket_type")){
					continue;
				}
				String ticketType = pd.get("ticket_type").toString().trim();
				String pdSunTime = (String) pd.get("sum_time");
				f += Float.parseFloat(pdSunTime);
				
				if("11".equals(ticketType)){
					stationOneHours += Float.parseFloat(pdSunTime);
				}else if("12".equals(ticketType)){
					stationTwoHours += Float.parseFloat(pdSunTime);
				}else if("13".equals(ticketType)){
					stationThreeHours += Float.parseFloat(pdSunTime);
				}else if("21".equals(ticketType)){
					lineOneHours += Float.parseFloat(pdSunTime);
				}else if("22".equals(ticketType)){
					lineTwoHours += Float.parseFloat(pdSunTime);
				}else if("31".equals(ticketType)){
					fireOneHours += Float.parseFloat(pdSunTime);
				}else if("32".equals(ticketType)){
					fireTwoHours += Float.parseFloat(pdSunTime);
				}else if("43".equals(ticketType)){
					liveWorkingHours += Float.parseFloat(pdSunTime);
				}else if("44".equals(ticketType)){
					lowVoltageHours += Float.parseFloat(pdSunTime);
				}else if("51".equals(ticketType)){
					urgentRepairsHours += Float.parseFloat(pdSunTime);
				}else if("81".equals(ticketType)){
					writtenFormHours += Float.parseFloat(pdSunTime);
				}else if("special".equals(ticketType)){
					specialHours += Float.parseFloat(pdSunTime);
				}
			}
			DecimalFormat df=new DecimalFormat("0.00");
			map.put("sum_hours", df.format(f));
			
			map.put("stationOneHours", df.format(stationOneHours));
			map.put("stationTwoHours", df.format(stationTwoHours));
			map.put("stationThreeHours", df.format(stationThreeHours));
			map.put("lineOneHours", df.format(lineOneHours));
			map.put("lineTwoHours", df.format(lineTwoHours));
			map.put("fireOneHours", df.format(fireOneHours));
			map.put("fireTwoHours", df.format(fireTwoHours));
			map.put("liveWorkingHours", df.format(liveWorkingHours));
			map.put("lowVoltageHours", df.format(lowVoltageHours));
			map.put("urgentRepairsHours", df.format(urgentRepairsHours));
			map.put("writtenFormHours", df.format(writtenFormHours));
			map.put("specialHours", df.format(specialHours));
		}
		logger.info("工时统计 ：计算每行票种对应工时 耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
		
		d1 = new Date();
		Collections.sort(allData,new Comparator<Map<String,Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				float i = Float.parseFloat((String) o1.get("sum_hours")) - Float.parseFloat((String) o2.get("sum_hours"));
				int f = 0;
				if( i < 0.0 ){
					f = 1;
				}else if( i > 0.0 ){
					f = -1;
				}
				return f;
			}
		});
		logger.info("工时统计 ：排序 耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
		
		if( allData != null && !allData.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > allData.size() ) {
				max = allData.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resData.add(allData.get(i));
			}
		}
		
//		Map<String,Map<String, Object>> dm = new HashMap<String,Map<String, Object>>();
//		
//		for (Map<String, Object> dmap : allDetailData) {
//			dm.put((String)dmap.get("person_name"), dmap);
//		}
		
		/*for (Map<String, Object> map : resData) {
			float f = 0;
			List<Map<String, Object>> personData = workingHousService.getDetailOfPerson(allDetailData, (String)map.get("person_name"));
			for (Map<String, Object> pd : personData) {
				if(null == pd.get("ticket_type")){
					continue;
				}
				String pdSunTime = (String) pd.get("sum_time");
				f += Float.parseFloat(pdSunTime);
			}
			DecimalFormat df=new DecimalFormat("0.00");
			map.put("sum_hours", df.format(f));
		}*/
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(allData);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageData", resData);
		map.put("total", pageInfo.getTotal());
		return map;
		
	}
	
	@RequestMapping("openDialog")
	public Object openDialog(@RequestParam(value = "unitName", required = false)String unitName,
			                 @RequestParam(value = "personName", required = false)String personName,
			                 @RequestParam(value = "ticketType", required = false)String ticketType, 
			                 @RequestParam(value = "startTime", required = false)String startTime, 
			                 @RequestParam(value = "endTime", required = false)String endTime){
		ModelAndView m = new ModelAndView("electric/workingHoursDialog");
		m.addObject("unitName", unitName);
		m.addObject("personName", personName);
		m.addObject("ticketType", ticketType);
		m.addObject("startTime", startTime);
		m.addObject("endTime", endTime);
		return m;
	}
	
	@RequestMapping("getDetailOfPerson")
	@ResponseBody
	public Object getDetailOfPerson(Page page,
			@RequestParam(value = "unitName", required = false)String unitName,
			@RequestParam(value = "personName", required = false)String personName, 
			@RequestParam(value = "ticketType", required = false)String ticketType,
			@RequestParam(value = "startTime", required = false)String startTime, 
			@RequestParam(value = "endTime", required = false)String endTime) throws Exception{
			
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketType", formatType(ticketType));
		params.put("sumStartTime", startTime);
		params.put("sumEndTime", endTime);
		
		Date pre = new Date();
		List<Map<String, Object>> allDetailData = workingHousService.getDetailOfAll(params);
		logger.info("工时统计弹框 ：查询 详情数据耗时： ->  " + (new Date().getTime() - pre.getTime()) + "毫秒");
		
		List<Map<String, Object>> allData = workingHousService.getDetailOfPerson(allDetailData, personName, unitName);
		List<Map<String, Object>> resData = new ArrayList<Map<String, Object>>();
		
		if( allData != null && !allData.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > allData.size() ) {
				max = allData.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resData.add(allData.get(i));
			}
		}
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(allData);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageData", resData);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	@RequestMapping("export")
	@ResponseBody
	public Object exportData(
						@RequestParam(value = "unitName", required = false)String unitName, 
						@RequestParam(value = "sumStartTime", required = false)String sumStartTime, 
						@RequestParam(value = "sumEndTime", required = false)String sumEndTime,
						@RequestParam(value = "orgId", required = false)String orgId,
						@RequestParam(value = "level", required = false)String level) {
		String downloadFileName = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("unitName", unitName);
			params.put("sumStartTime", sumStartTime);
			params.put("sumEndTime", sumEndTime);
			params.put("orgId", orgId);
			params.put("level", level);
			
			Date pre = new Date();
			
			Date d1 = new Date();
			List<Map<String, Object>> exportData = workingHousService.getCountOfTicket(params);
			logger.info("工时统计导出 ：查询工作负责人持有票数耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
			
			d1 = new Date();
			List<Map<String, Object>> allDetailData = workingHousService.getDetailOfAll(params);
			logger.info("工时统计导出 ：查询工作负责人持有票详情耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");
			
			for (Map<String, Object> map : exportData) {
				float f = 0,
						stationOneHours = 0,stationTwoHours = 0, stationThreeHours = 0,
						lineOneHours = 0, lineTwoHours = 0, fireOneHours = 0, fireTwoHours = 0,
						liveWorkingHours = 0, lowVoltageHours = 0, urgentRepairsHours = 0,
						writtenFormHours = 0 , specialHours = 0;
				List<Map<String, Object>> personData = workingHousService.getDetailOfPerson(allDetailData, (String)map.get("person_name"), (String)map.get("unit_name"));
				
				for (Map<String, Object> pd : personData) {
					if(null == pd.get("ticket_type")){
						continue;
					}
					String ticketType = pd.get("ticket_type").toString().trim();
					String pdSunTime = (String) pd.get("sum_time");
					f += Float.parseFloat(pdSunTime);
					
					if("11".equals(ticketType)){
						stationOneHours += Float.parseFloat(pdSunTime);
					}else if("12".equals(ticketType)){
						stationTwoHours += Float.parseFloat(pdSunTime);
					}else if("13".equals(ticketType)){
						stationThreeHours += Float.parseFloat(pdSunTime);
					}else if("21".equals(ticketType)){
						lineOneHours += Float.parseFloat(pdSunTime);
					}else if("22".equals(ticketType)){
						lineTwoHours += Float.parseFloat(pdSunTime);
					}else if("31".equals(ticketType)){
						fireOneHours += Float.parseFloat(pdSunTime);
					}else if("32".equals(ticketType)){
						fireTwoHours += Float.parseFloat(pdSunTime);
					}else if("43".equals(ticketType)){
						liveWorkingHours += Float.parseFloat(pdSunTime);
					}else if("44".equals(ticketType)){
						lowVoltageHours += Float.parseFloat(pdSunTime);
					}else if("51".equals(ticketType)){
						urgentRepairsHours += Float.parseFloat(pdSunTime);
					}else if("81".equals(ticketType)){
						writtenFormHours += Float.parseFloat(pdSunTime);
					}else if("special".equals(ticketType)){
						specialHours += Float.parseFloat(pdSunTime);
					}
				}
				DecimalFormat df=new DecimalFormat("0.00");
				map.put("sum_hours", df.format(f));
				
				map.put("stationOneHours", df.format(stationOneHours));
				map.put("stationTwoHours", df.format(stationTwoHours));
				map.put("stationThreeHours", df.format(stationThreeHours));
				map.put("lineOneHours", df.format(lineOneHours));
				map.put("lineTwoHours", df.format(lineTwoHours));
				map.put("fireOneHours", df.format(fireOneHours));
				map.put("fireTwoHours", df.format(fireTwoHours));
				map.put("liveWorkingHours", df.format(liveWorkingHours));
				map.put("lowVoltageHours", df.format(lowVoltageHours));
				map.put("urgentRepairsHours", df.format(urgentRepairsHours));
				map.put("writtenFormHours", df.format(writtenFormHours));
				map.put("specialHours", df.format(specialHours));
			}
			logger.info("导出   查询工时统计表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");
			
			d1 = new Date();
			Collections.sort(exportData,new Comparator<Map<String,Object>>(){
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					float i = Float.parseFloat((String) o1.get("sum_hours")) - Float.parseFloat((String) o2.get("sum_hours"));
					int f = 0;
					if( i < 0.0 ){
						f = 1;
					}else if( i > 0.0 ){
						f = -1;
					}
					return f;
				}
			});
			logger.info("工时统计 ：排序 耗时 ->" + ( new Date().getTime() - d1.getTime()) + "毫秒");

			String templatePath = request.getSession().getServletContext().getRealPath("/upload/electric")+ File.separatorChar +"WorkTicket_Three_Person_Workhours.xlsx";
			logger.info("模板path  - >  " + templatePath);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-工作负责人工时统计报表.xlsx";
			logger.info("tempPath  - >  " + downloadFileName);
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/temp")+ File.separatorChar +downloadFileName;
		
			workingHousService.makeExportExcel( exportData, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
		}
		return new OperaterResult<>(true, downloadFileName);
	}
	
	/**
	 * 获取系数
	 * @return
	 */
	@RequestMapping("getHourModulus")
	@ResponseBody
	public Object getHourModulus(Page page) {
		List<HourModulus> data = workingHousService.getModulusByType(null);
		PageInfo<HourModulus> pager = new PageInfo<HourModulus>(data);
		return new DataGrid(data, pager.getTotal());
		
	}
	
	/**
	 * 修改系数
	 * @return
	 */
	@RequestMapping("updateHourModulus")
	@ResponseBody
	public Object updateHourModulus(Page page,HourModulus modulus) {
		workingHousService.updateModulus(modulus);
		return new OperaterResult<>(true);
		
	}
	
	public String formatType(String str){
		switch (str) {
		case "厂站第一种工作票":
			str = "11";
			break;
		case "厂站第二种工作票":
			str = "12";
			break;
		case "厂站第三种工作票":
			str = "13";
			break;
		case "线路第一种工作票":
			str = "21";
			break;
		case "线路第二种工作票":
			str = "22";
			break;
		case "一级动火工作票":
			str = "31";
			break;
		case "二级动火工作票":
			str = "32";
			break;
		case "配电第一种工作票":
			str = "41";
			break;
		case "配电第二种工作票":
			str = "42";
			break;
		case "带电作业工作票":
			str = "43";
			break;
		case "低压配电网工作票":
			str = "44";
			break;
		case "紧急抢修工作票":
			str = "51";
			break;
		case "安全技术交底单":
			str = "61";
			break;
		case "二次措施单":
			str = "71";
			break;
		case "书面布置和记录":
			str = "81";
			break;
		case "现场勘察记录":
			str = "91";
			break;
		default:
			break;
		}

		return str;
	}
	
	
}
