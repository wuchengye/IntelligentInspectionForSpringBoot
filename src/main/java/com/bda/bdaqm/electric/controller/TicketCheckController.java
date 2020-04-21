package com.bda.bdaqm.electric.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.bdaqm.electric.service.TicketVerificateService;
import com.bda.bdaqm.electric.service.serviceImpl.TicketCheckServiceImpl;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping({"electric/ticketCheck"})
public class TicketCheckController extends BaseController{

	@Autowired
	private TicketVerificateService ticketVerificateService;
	@Autowired
	private TicketCheckServiceImpl ticketCheckServiceImpl;
	
	@RequestMapping("getTicketsData")
	@ResponseBody
	public Object getTicketsData(Page page,QueryParams ticket){
		
		List<Map<String, Object>> isNotNullDataList = ticketVerificateService.getTicketsData(ticket);
		//List<Map<String,Object>> isNullDataList = ticketVerificateService.getTicketsDataIsNull(ticket);
		List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();
		allDataList.addAll(isNotNullDataList);
		//allDataList.addAll(isNullDataList);
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		if( allDataList!=null && !allDataList.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > allDataList.size() ) {
				max = allDataList.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(allDataList.get(i));
			}
		}
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(allDataList);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("all", allDataList);
		map.put("show", resList);
		map.put("total", pageInfo.getTotal());
		//return new DataGrid(resList, pageInfo.getTotal());
		return map;
	}
	
	@RequestMapping("checkTicket")
	@ResponseBody
	public Object checkTicket(@RequestParam(required=false,value="ids[]")List<String> ids,QueryParams ticket){
		
		Date pre = new Date();
		List<TicketCheckResult> resList = ticketCheckServiceImpl.ticketCheck(ticket, ids);
		logger.info("校验耗时 ->  " + (new Date().getTime() - pre.getTime()) + "毫秒" );
		
		PageInfo<TicketCheckResult> p = new PageInfo<TicketCheckResult>(resList);
		return new DataGrid(resList, p.getTotal());
		
	}
	
	@RequestMapping("quartzCheckTicket")
	@ResponseBody
	public Object quartzCheckTicket(String beginTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
		calendar.add(Calendar.DATE,-1);  //当前时间减去一天，即一天前的时间
		if(StringUtils.isBlank(beginTime)){
			beginTime = sdf.format(calendar.getTime());
		}
		if(StringUtils.isBlank(endTime)){
			endTime = sdf.format(calendar.getTime());
		}
		List<String> ids = ticketVerificateService.getQuartzTicketsData(beginTime,endTime);
		QueryParams ticket = new QueryParams();
		ticketCheckServiceImpl.ticketCheck(ticket, ids);
		return null;
	}
	
}
