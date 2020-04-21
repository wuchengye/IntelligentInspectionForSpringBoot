package com.bda.bdaqm.risk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.risk.model.NotDubious;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.risk.service.DubiousService;
import com.bda.bdaqm.risk.service.UsedTabooService;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping({ "risk/dubious" })
public class DubiousController extends BaseController {

	@Autowired
	public DubiousService dubiousService;
	
	@Autowired
	public UsedTabooService usedTabooService;

	@RequestMapping("/getDubiousDetail")
	@ResponseBody
	public Result getDubiousDetail(Page page, NotDubious params){
		List<NotDubious> list = dubiousService.getDubiousDetail(page, params);
		PageInfo<NotDubious> pageInfo = new PageInfo<NotDubious>(list);
		//return new DataGrid(list, pageInfo.getTotal());
		return Result.success( new DataGrid(list, pageInfo.getTotal()));
	}
	@RequestMapping("/openDialog")
	@ResponseBody
	public Result openDialog(@RequestParam Map<String, String> params){
		//ModelAndView result = new ModelAndView("risk/dubious/edit");
		Map<String,Object> result = new HashMap<>();
		//TabooSession session = usedTabooService.
		try{
			if(!params.containsKey("sessionId") || StringUtils.isBlank(params.get("sessionId"))) {
				
				//throw new Exception("sessionId为空");
				return Result.failure(ResultCode.NO_SESSION_ID);
			}
			
			NotDubious session = dubiousService.getDubiousById(params.get("sessionId"));
			Subject subject = SecurityUtils.getSubject();
			User user = (User)subject.getPrincipal();
//			if(StringUtils.isBlank(session.getCheckAccounts())){
//				session.setCheckAccounts(user.getAccount());
//			}
			result.put("session", session);
			result.put("checkAccounts", user.getAccount());
		}catch(Exception e) {
			logger.error("程序异常：" + e);
			e.printStackTrace();
			return Result.failure();
		}
		//return result;
		return Result.success(result);
	}
	
	@RequestMapping("/getSessionDetail")
	@ResponseBody
	public Result getSessionDetail(String sessionId){
		List<SessionDetail> list = new ArrayList<SessionDetail>();
		list = dubiousService.getSessionDetail(sessionId);
		//return new DataGrid(list, list.size());
		return Result.success( new DataGrid(list, list.size()));
	}
	@RequestMapping("/exportDudiousDetail")
	@ResponseBody
	public Result exportDudiousDetail(NotDubious params) throws Exception{
		String downloadFileName = "";
		try{
			List<NotDubious> exportJudge = dubiousService.getExportDubious(params);
			String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate/")+"非可疑数据导出模板.xlsx";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-exportDubious.xlsx";
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/")+downloadFileName;
			dubiousService.makeNotDubiousExcel(exportJudge, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
			return Result.failure();
		}
		//return new OperaterResult<>(true, downloadFileName);
		return Result.success(downloadFileName);
	}
	
	public void copyFile(String oldPath, String newPath) { 
		InputStream inStream = null;
		try { 
			int bytesum = 0; 
			int byteread = 0; 
			File oldfile = new File(oldPath); 
			if (oldfile.exists()) { //文件存在时 
			inStream =  new FileInputStream(oldPath); //读入原文件 
			FileOutputStream fs = new FileOutputStream(newPath); 
			byte[] buffer = new byte[1444]; 
			int length; 
			while ( (byteread = inStream.read(buffer)) != -1) { 
				bytesum += byteread; //字节数 文件大小 
				System.out.println(bytesum); 
				fs.write(buffer, 0, byteread); 
			} 
				
				} 
			} catch (Exception e) { 
				System.out.println("复制单个文件操作出错"); 
				e.printStackTrace(); 
			} finally{
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		} 
}
