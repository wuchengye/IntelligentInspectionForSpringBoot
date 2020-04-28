package com.bda.bdaqm.electric.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.model.Modulu;
import com.bda.bdaqm.electric.model.MonthReward;
import com.bda.bdaqm.electric.model.ScoreSummary;
import com.bda.bdaqm.electric.model.YearReward;
import com.bda.bdaqm.electric.service.WorkPriScoreService;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
@Controller
@RequestMapping({ "electric/workpriscore" })
public class WorkPriScoreController extends BaseController{
	@Autowired
	private WorkPriScoreService workPriScoreService;
	
	@RequestMapping("getWorkPriScore")
	@ResponseBody
	public Object getWorkPriScore(Page page,String reportType, String year,
			                      String unitName, String personName, 
			                      String specialty, String classes,String orgId,
			                      String level) {
		Date pre = new Date();
		List<ScoreSummary> data= workPriScoreService.getWorkPriScore(reportType, year, unitName, personName, specialty, classes,orgId,level);
		logger.info("查询"+reportType+"得分汇总表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");
		List<ScoreSummary> resList = new ArrayList<ScoreSummary>();
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
			logger.info("获取"+reportType+"得分汇总表分页数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");
		}
		
		PageInfo<ScoreSummary> pager = new PageInfo<ScoreSummary>(data);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("export")
	@ResponseBody
	public Object exportData(String reportType, String year, String unitName, 
			                 String personName, String specialty, String classes,
			                 String orgId,String level) {
		String downloadFileName = "";
		try {
			Date pre = new Date();
			List<ScoreSummary> exportData= workPriScoreService.getWorkPriScore(reportType, year, unitName, personName, specialty, classes,orgId,level);
			logger.info("导出   查询"+reportType+"得分汇总表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");

			String templatePath = request.getSession().getServletContext().getRealPath("/upload/electric")+ File.separatorChar +"WorkTicket_Three_Person_Score.xlsx";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-工作票三种人得分汇总表.xlsx";
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/temp")+ File.separatorChar +downloadFileName;
		
			workPriScoreService.makeExportExcel(reportType, year, exportData, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
		}
		return new OperaterResult<>(true, downloadFileName);
	}
	
	/**
	 * 加载单位下拉框数据
	 * @return
	 */
	@RequestMapping("getUnitCombobox")
	@ResponseBody
	public List<ComboBoxItem> getUnitCombobox(){
		List<ComboBoxItem> res = workPriScoreService.getUnitCombobox();
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	
	/**
	 * 加载专业下拉框数据
	 * @return
	 */
	@RequestMapping("getSpecialtyCombobox")
	@ResponseBody
	public List<ComboBoxItem> getSpecialtyCombobox(){
		List<ComboBoxItem> res = workPriScoreService.getSpecialtyCombobox();
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	
	/**
	 * 加载班组下拉框数据
	 * @return
	 */
	@RequestMapping("getClassCombobox")
	@ResponseBody
	public List<ComboBoxItem> getClassCombobox(String unitName){
		List<ComboBoxItem> res = workPriScoreService.getClassCombobox(unitName);
		res.add(0,new ComboBoxItem("--请选择--","",true));
    	return res;
    }
	/**
	 * 获取月度奖励分数
	 * @return
	 */
	@RequestMapping("getMonthReward")
	@ResponseBody
	public Object getMonthReward(Page page) {
		List<MonthReward> data = workPriScoreService.getMonthReward();
		List<MonthReward> resList = new ArrayList<MonthReward>();
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
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		PageInfo<MonthReward> pager = new PageInfo<MonthReward>(data);
		return new DataGrid(resList, pager.getTotal());
		
	}
	/**
	 * 获取同类型人员月度奖励分数
	 * @return
	 */
	@RequestMapping("getMonthVerify")
	@ResponseBody
	public List<MonthReward> getMonthVerify(Page page,String type) {
		List<MonthReward> data = workPriScoreService.getMonthVerify(type);
		return data;
	}
	
	/**
	 * 获取年度奖励分数
	 * @return
	 */
	@RequestMapping("getYearReward")
	@ResponseBody
	public Object getYearReward(Page page) {
		List<YearReward> data = workPriScoreService.getYearReward();
		List<YearReward> resList = new ArrayList<YearReward>();
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
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		PageInfo<YearReward> pager = new PageInfo<YearReward>(data);
		return new DataGrid(resList, pager.getTotal());
		
	}
	/**
	 * 获取同类型人员年度奖励分数
	 * @return
	 */
	@RequestMapping("getYearVerify")
	@ResponseBody
	public List<YearReward> getYearVerify(Page page,String type) {
		List<YearReward> data = workPriScoreService.getYearVerify(type);
		return data;
	}
	/**
	 * 修改月度奖励分数
	 * @return
	 */
	@RequestMapping("updateMonth")
	@ResponseBody
	public Object updateMonth(Page page,MonthReward monthReward) {
		 workPriScoreService.updateMonth(monthReward);
		 return new OperaterResult<>(true);
	}
	/**
	 * 修改年度奖励分数
	 * @return
	 */
	@RequestMapping("updateYear")
	@ResponseBody
	public Object updateYear(Page page,YearReward yearReward) {
		 workPriScoreService.updateYear(yearReward);
		 return new OperaterResult<>(true);
	}
	
	/**
	 * 获取系数
	 * @return
	 */
	@RequestMapping("getModulus")
	@ResponseBody
	public Object getModulus(Page page) {
		List<Modulu> data = workPriScoreService.getModulus();
		PageInfo<Modulu> pager = new PageInfo<Modulu>(data);
		return new DataGrid(data, pager.getTotal());
		
	}
	/**
	 * 修改系数
	 * @return
	 */
	@RequestMapping("updateModulus")
	@ResponseBody
	public Object updateModulus(Page page,Modulu modulus) {
		workPriScoreService.updateModulus(modulus);
		return new OperaterResult<>(true);
		
	}
}
