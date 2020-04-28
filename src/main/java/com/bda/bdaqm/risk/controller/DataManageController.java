package com.bda.bdaqm.risk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bda.bdaqm.RESTful.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.risk.model.DataManage;
import com.bda.bdaqm.risk.service.DataManageService;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({ "risk/dataManage" })
public class DataManageController extends BaseController {

	@Autowired
	public DataManageService dataManageService;
	
	@RequestMapping("/getDataManage")
	@ResponseBody
	public Result getDataManage(Page page){
		List<DataManage> list = dataManageService.getDataManage(page);
		PageInfo<DataManage> pageInfo = new PageInfo<DataManage>(list);
		//return new DataGrid(list, pageInfo.getTotal());
		return Result.success(new DataGrid(list, pageInfo.getTotal()));
	}

	@RequestMapping("/exportDataManage")
	@ResponseBody
	public Result exportDudiousDetail() throws Exception{
		String downloadFileName = "";
		try{
			List<DataManage> exportJudge = dataManageService.getExportDataManage();
			String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate/")+"数据管理导出模板.xlsx";

			//打印地址
			System.out.println("数据导出地址=======" + templatePath);

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-exportDataManage.xlsx";
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/")+downloadFileName;
			dataManageService.makeDataManageExcel(exportJudge, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
			return Result.failure();
		}
		//return new OperaterResult<>(true, downloadFileName);
		return Result.success(downloadFileName);
	}
	
}
