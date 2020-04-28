package com.bda.bdaqm.electric.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheck;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.bdaqm.electric.service.TicketCheckResultService;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;

@Controller
@RequestMapping({"electric/ticketCheckRes"})
public class TicketCheckResultController extends BaseController{

	@Autowired
	private TicketCheckResultService ticketCheckResultService;
	
	@RequestMapping("getResultData")
	@ResponseBody
	public Object getResultData(Page page,QueryParams ticket){
		int flag = 0;
		if(ticket.getCheckResult().startsWith("hgyc")) {
			flag = 1;
		}else if(ticket.getCheckResult().startsWith("gfyc")){
			flag = 2;
		}
		ticket.setCheckResult(ticketCheckResultService.handleParam(ticket.getCheckResult()));

		List<TicketCheckResult> allDataList = ticketCheckResultService.getResultData(ticket,flag);
		List<TicketCheckResult> resList = new ArrayList<TicketCheckResult>();
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
		PageInfo<TicketCheckResult> pageInfo = new PageInfo<TicketCheckResult>(allDataList);
		return new DataGrid(resList, pageInfo.getTotal());
	}
	
	/**
	 * 加载班组下拉框数据
	 * @return
	 */
	@RequestMapping("getClassCombobox")
	@ResponseBody
	public List<ComboBoxItem> getClassCombobox(){
		List<ComboBoxItem> res = ticketCheckResultService.getClassCombobox();
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	
	/**
	 * 加载工作票种类下拉框数据
	 * @return
	 */
	@RequestMapping("getTicketType")
	@ResponseBody
	public List<ComboBoxItem> getTicketType(){
		List<ComboBoxItem> res = ticketCheckResultService.getTicketType();
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	
	/**
	 * 加载三种人姓名下拉框数据
	 * @return
	 */
	@RequestMapping("getPersonName")
	@ResponseBody
	public List<ComboBoxItem> getPersonName(String type){
		List<ComboBoxItem> res = ticketCheckResultService.getPersonName(type);
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	
	/**
	 * 获取柱状图数据
	 * @return
	 */
	@RequestMapping("getChartData")
	@ResponseBody
	public Object getChartData(QueryParams ticket){
		ticket.setCheckResult(ticketCheckResultService.handleParam(ticket.getCheckResult()));
		List<Map<String,String>> data = ticketCheckResultService.getChartData(ticket);
		return  new OperaterResult<List<Map<String,String>>>(true,"OK",data);
	}
	
	@RequestMapping("getExceptionBarData")
	@ResponseBody
	public Object getExceptionBarData(QueryParams ticket){
		ticket.setCheckResult(ticketCheckResultService.handleParam(ticket.getCheckResult()));
		List<Map<String,String>> data = ticketCheckResultService.getExceptionBarData(ticket);
		return  new OperaterResult<List<Map<String,String>>>(true,"OK",data);
	}
	
	/**
	 * 获取饼状图数据
	 * @return
	 */
	@RequestMapping("getPieData")
	@ResponseBody
	public Object getPieData(QueryParams ticket){
		ticket.setCheckResult(ticketCheckResultService.handleParam(ticket.getCheckResult()));
		List<Map<String,String>> data = ticketCheckResultService.getPieData(ticket);
		return  new OperaterResult<List<Map<String,String>>>(true,"OK",data);
	}
	
	/**
	 * 校验结果弹出框
	 * @return
	 */
	@RequestMapping("getCheckDialog")
	public Object getDialog(@RequestParam("ticketID") String ticketID ,@RequestParam("batchTime") String batchTime) {
 
		long a1 = System.currentTimeMillis();
		List<TicketCheck> listTC = ticketCheckResultService.selectById(ticketID);
		List<Integer> removeList = new ArrayList<Integer>();
		long a2 = System.currentTimeMillis();
		logger.info("查询所消耗时间"+(a2-a1));
		long a3 = System.currentTimeMillis();
		if(listTC.size()>1) {
			int count = 0;
			String permiskId = "";
			for(int i=0;i<listTC.size();i++) {
				if(permiskId.equals(listTC.get(i).getTicketId())) out:{
					TicketCheck map = listTC.get(count);
					TicketCheck newMap = listTC.get(i);
					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						if(toStr(newMap.getWorkStartTime()).equals("")) {
							removeList.add(i);
							break out;
						}
						if(toStr(map.getWorkStartTime()).equals("")) {
							removeList.add(count);
							count = i;
							break out;
						}
						long pertimeOne = sim.parse(toStr(map.getWorkStartTime())).getTime();
						long endTimeOne = sim.parse(toStr(newMap.getWorkStartTime())).getTime();
						if(endTimeOne>pertimeOne) {
							removeList.add(count);
							count = i;
						}else {
							removeList.add(i);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}else {
					count = i;
				}
				permiskId = listTC.get(i).getTicketId();
				
			}
		}
		long a4 = System.currentTimeMillis();
		logger.info("第一个for所消耗时间"+(a4-a3));
		long b1 = System.currentTimeMillis();
		List<Map<String,String>> list = ticketCheckResultService.getAllUser();
		long b2 = System.currentTimeMillis();
		logger.info("第二个查询所消耗时间"+(b2-b1));
		Map<String,String> map = new HashMap<String,String>();
		long t1 = System.currentTimeMillis();
		for(int i=0;i<list.size();i++) {
			map.put(list.get(i).get("user_id"), list.get(i).get("user_name"));
		}
		long t2 = System.currentTimeMillis();
		logger.info("第二个for所消耗时间"+(t2-t1));
		int listCount = 0;
		for(int i=0;i<listTC.size();i++) {
			if(removeList.contains(i)) {
				continue;
			}
			listCount =i;
		}
		TicketCheck tc = listTC.get(listCount);
		tc.setTicketSigner(map.get(tc.getTicketSigner()));
		tc.setWorkPrincipal(map.get(tc.getWorkPrincipal()));
		tc.setWorkLicensor(map.get(tc.getWorkLicensor()));
		tc.setWorkBreakPrincipal(map.get(tc.getWorkBreakPrincipal()));
		tc.setTicketCounterSigner(map.get(tc.getTicketCounterSigner()));
		tc.setWorkBreakLicensor(map.get(tc.getWorkBreakLicensor()));
		tc.setWorkStartLicensor(map.get(tc.getWorkStartLicensor()));
		tc.setWorkStartPrincipal(map.get(tc.getWorkStartPrincipal()));
		tc.setTicketEndLicensor(map.get(tc.getTicketEndLicensor()));
		tc.setMeasureEndPerUname(map.get(tc.getMeasureEndPerUname()));
		tc.setGuardianName(map.get(tc.getGuardianName()));
		tc.setTicketReceiveUname(map.get(tc.getTicketReceiveUname()));
		tc.setLineGroundingUname(map.get(tc.getLineGroundingUname()));
		tc.setDispatchPriUname(map.get(tc.getDispatchPriUname()));
		tc.setDispatchUname(map.get(tc.getDispatchUname()));
		tc.setChangePrincipal(map.get(tc.getChangePrincipal()));
		tc.setChangeNewPrincipal(map.get(tc.getChangeNewPrincipal()));
		tc.setPermisstionPrincipal(map.get(tc.getPermisstionPrincipal()));
		tc.setWorkEndPrincipal(map.get(tc.getWorkEndPrincipal()));
		tc.setRepairControlUname(map.get(tc.getRepairControlUname()));
		tc.setWatchName(map.get(tc.getWatchName()));
		tc.setSaftyStartUname(map.get(tc.getSaftyStartUname()));
		tc.setWorkGuardianUname(map.get(tc.getWorkGuardianUname()));
		tc.setTicketExecuteUname(map.get(tc.getTicketExecuteUname()));
		tc.setProtectionStartUname(map.get(tc.getProtectionStartUname()));
	    tc.setBureauStartUname(map.get(tc.getBureauStartUname()));
	    tc.setWatchUname(map.get(tc.getWatchUname()));
	    
		String ticketType = tc.getTicketType();
		ModelAndView m = new ModelAndView();
		if(ticketType.equals("11")) {
			m.setViewName("electric/workTicketDialog");
		}else if(ticketType.equals("21")) {
			m.setViewName("electric/detailDialog/lineFirstDialog");
		}else if(ticketType.equals("12")) {
			m.setViewName("electric/detailDialog/stationSecondDialog");
		}else if(ticketType.equals("13")) {
			m.setViewName("electric/detailDialog/stationThirdDialog");

		}else if(ticketType.equals("22")) {
			m.setViewName("electric/detailDialog/lineSecondDialog");
		}else if(ticketType.equals("51")) {
			m.setViewName("electric/detailDialog/emergencyDialog");
		}else if(ticketType.equals("31")) {
			m.setViewName("electric/detailDialog/fireFirstDialog");

		}else if(ticketType.equals("32")) {
			m.setViewName("electric/detailDialog/fireSecondDialog");
		}else if(ticketType.equals("43")) {
			m.setViewName("electric/detailDialog/liveWorkingDialog");
		}else if(ticketType.equals("44")) {
			m.setViewName("electric/detailDialog/lowVoltagePowerDialog");

		}
		long t3 = System.currentTimeMillis();
		TicketCheckResult rs = ticketCheckResultService.getResultById(ticketID,batchTime);
		List<Map<String,String>> childrenList = ticketCheckResultService.selectChildById(ticketID,batchTime);
		long t4 = System.currentTimeMillis();
		logger.info("最后两个所消耗时间"+(t4-t3));
		
		JSONArray jsa = JSONArray.fromObject(childrenList);
		rs.setCheckString();
		rs.setStandardString();
		m.addObject("TicketCheck", tc);
		m.addObject("result", rs);
		m.addObject("childLs", jsa);
		return m;
	}
	
	
	
	@RequestMapping("openBatchManageDialog")
	public Object openBatchManageDialog(){
		ModelAndView m = new ModelAndView("electric/detailDialog/batchManageDialog");
		return m;
	}
	
	@RequestMapping("getManageBatch")
	@ResponseBody
	public Object getBatch(Page page){
		List<Map<String,String>> allDataList = ticketCheckResultService.getBatchsForManage();
		List<Map<String,String>> resList = new ArrayList<Map<String,String>>();
		
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
		PageInfo<Map<String,String>> pageInfo = new PageInfo<Map<String,String>>(allDataList);
		return new DataGrid(resList, pageInfo.getTotal());
	}
	
	@RequestMapping("delBatch")
	@ResponseBody
	public Object delBatch(String batch){
		
		try {
			ticketCheckResultService.delBatch(batch);
			return new OperaterResult<>(true, "删除成功！");
		} catch (Exception e) {
			return new OperaterResult<>(false, "删除失败！");
		}
	}
	
	private String toStr(Object obj) {
		String str = "";
		if (obj == null) {
			str = "";
		} else {
			str = obj.toString();
		}
		return str;
	}
	
	/**
	 * 获取校验批次下拉框
	 * @return
	 */
	@RequestMapping("getAllBatch")
	@ResponseBody
	public List<ComboBoxItem> getAllBatch(){
		List<ComboBoxItem> res = ticketCheckResultService.getAllBatch();
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
	}
 
}
