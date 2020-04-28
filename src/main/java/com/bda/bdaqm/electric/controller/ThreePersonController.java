package com.bda.bdaqm.electric.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.electric.model.ThpsDialogDetail;
import com.bda.bdaqm.electric.model.ThreePerson;
import com.bda.bdaqm.electric.service.ThreePersonService;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping({ "electric/threeperson" })
public class ThreePersonController extends BaseController{

	@Autowired
	private ThreePersonService service;
	
	/**
	 * 获取三种人持票情况统计表页面数据
	 * @param page               分页参数
	 * @param updateTimeBegin    查询开始日期
	 * @param updateTimeEnd      查询结束日期
	 * @param unitName           单位
	 * @param threeType          “三种人”类型
	 * @return
	 */
	@RequestMapping("getStatement")
	@ResponseBody
	public Object getAllData(Page page, String updateTimeBegin, String updateTimeEnd, 
			                 String unitName, String threeType, String personName, String specialty,String orgId
			                 ,String level) {
		Date pre = new Date();
		List<ThreePerson> data= service.getThreePersonData( updateTimeBegin, updateTimeEnd, unitName, threeType, personName, specialty ,orgId,level);
		logger.info("查询三种人持票情况统计表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");
		//持票数 数据
		List<ThreePerson> keepAmountData= service.getKeepTicketAmount( updateTimeBegin, updateTimeEnd, unitName, threeType, personName, specialty ,orgId,level);
		for (ThreePerson t : data) {
			for (ThreePerson amount : keepAmountData) {
				if(null != amount && t.getIdd().equals( amount.getIdd() ) ){
					t.setKeepTicketAmount( amount.getKeepTicketAmount() );
					break;
				}
				t.setKeepTicketAmount( "0" );
			}
			t.setThreeType("");
		}
		//根据持票数对查询到的三种人持票情况正序排序
		data.sort(Comparator.reverseOrder());
		
		List<ThreePerson> resList = new ArrayList<ThreePerson>();
		if( data!=null && !data.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > data.size() ) {
				max = data.size();
			} else {
				max = start+pageSize;
			}
			pre = new Date();
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
			logger.info("获取三种人持票情况统计表分页数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");
		}
		
		
	    
		PageInfo<ThreePerson> pager = new PageInfo<ThreePerson>(data);
		return new DataGrid(resList, pager.getTotal());
	}
	
	/**
	 * 获取源数据
	 * @param ticketNum   对应最后一次持票票号
	 * @param personName  对应姓名
	 * @return
	 */
	@RequestMapping("getOriginalData")
	@ResponseBody
	public ModelAndView openDialog(String ticketNum,String personName){
		ModelAndView m = new ModelAndView("electric/threePsDialog");
		ThpsDialogDetail data = service.getOriginalData(ticketNum, personName).get(0);
		m.addObject("originalData", data);
		return m;
	}
	
	/**
	 * 导出报表
	 * @param updateTimeBegin
	 * @param updateTimeEnd
	 * @param unitName
	 * @param threeType
	 * @return
	 */
	@RequestMapping("export")
	@ResponseBody
	public Object exportData(String updateTimeBegin, String updateTimeEnd, String unitName, String threeType, String personName, String specialty,String orgId,
			String level){
		String downloadFileName = "";
		try {
			Date pre = new Date();
			List<ThreePerson> exportData= service.getThreePersonData( updateTimeBegin, updateTimeEnd, unitName, threeType, personName, specialty,orgId,level);
			logger.info("导出   查询三种人持票情况统计表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");

			//持票数 数据
			List<ThreePerson> keepAmountData= service.getKeepTicketAmount( updateTimeBegin, updateTimeEnd, unitName, threeType, personName, specialty,orgId,level);
			for (ThreePerson t : exportData) {
				for (ThreePerson amount : keepAmountData) {
					if(null != amount && t.getIdd().equals( amount.getIdd() ) ){
						t.setKeepTicketAmount( amount.getKeepTicketAmount() );
						break;
					}
				}
				t.setThreeType("");
			}
			//根据持票数对查询到的三种人持票情况正序排序
			exportData.sort(Comparator.reverseOrder());
			
			String templatePath = request.getSession().getServletContext().getRealPath("/upload/electric")+ File.separatorChar +"Three_Person_Keep.xlsx";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-三种人持票情况统计表.xlsx";
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/temp")+ File.separatorChar +downloadFileName;
		
			service.makeExportExcel(exportData, templatePath, downloadPath);
		}catch(Exception e) {
			logger.error("程序异常：" + e);
			e.printStackTrace();
		}
		
		return new OperaterResult<>(true, downloadFileName);
	}
	
	/**
	 * 
	 * @param personName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getAmountOfType")
	@ResponseBody
	public Object getAmountOfType(String personName, String startTime, String endTime){
		Map<String, String> params = new HashMap<String, String>();
		params.put("personName", personName);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return service.getAmountOfTypeFromKeep(params);
	}
	
}
