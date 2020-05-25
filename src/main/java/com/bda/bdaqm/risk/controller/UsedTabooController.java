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


import com.bda.admin.util.UserUtil;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.risk.service.UsedTabooService;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.bdaqm.util.V3toWAV;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping({ "risk/usedtaboo" })
public class UsedTabooController extends BaseController {
	
	@Autowired
	public UsedTabooService usedTabooService;
	
	@RequestMapping("/getTabooJudgeDetail")
	@ResponseBody
	public Result getTabooJudgeDetail(Page page, TabooSession params){
		List<TabooSession> list = usedTabooService.getTabooJudgeDetail(page, params);
		PageInfo<TabooSession> pageInfo = new PageInfo<TabooSession>(list);
		//return new DataGrid(list, pageInfo.getTotal());
		return Result.success(new DataGrid(list, pageInfo.getTotal()));
	}
	
	@RequestMapping("/openDialog")
	@ResponseBody
	public Result openDialog(@RequestParam Map<String, String> params){
		//ModelAndView result = new ModelAndView("risk/usedtaboo/edit");
		Map<String,Object> result = new HashMap<>();
		//TabooSession session = usedTabooService.
		try{
			if(!params.containsKey("sessionId") || StringUtils.isBlank(params.get("sessionId"))) {
				
				//throw new Exception("sessionId为空");
				return Result.failure(ResultCode.NO_SESSION_ID);
			}
			
			TabooSession session = usedTabooService.getTabooSessionById(params.get("sessionId"));
			Subject subject = SecurityUtils.getSubject();
			User user = (User)subject.getPrincipal();
			if(StringUtils.isBlank(session.getCheckAccounts())){
				session.setCheckAccounts(user.getAccount());
			}
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
	
	@RequestMapping("/submitCheck")
	@ResponseBody
	public Result submitCheck(TabooSession params){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", true);
		try{
			//更新结果表数据
			usedTabooService.updateTobooCheckResult(params);
			//将核查结果插入历史表
			usedTabooService.insertCheckHistory(params);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		//return map;
		return Result.success(map);
	}
	
	@RequestMapping("/getSessionDetail")
	@ResponseBody
	public Result getSessionDetail(String sessionId){
		List<SessionDetail> list = new ArrayList<SessionDetail>();
		list = usedTabooService.selectSessionDetail(sessionId);
		//return new DataGrid(list, list.size());
		return Result.success(new DataGrid(list, list.size()));
	}
	
	@RequestMapping("/playV3")
	@ResponseBody
	public Result playV3(@RequestParam(required=true)String filePath,String fileName) throws Exception{
		
		String ftpDebugModel = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.FTP_DEBUG_MODEL_KEY);
		if(ftpDebugModel.equals("1")){
			//return new OperaterResult<>(true, "", "/bdaqm/assets/audio/test.mp3");
			return Result.success(new OperaterResult<>(true, "", "/bdaqm/assets/audio/test.mp3"));
		}else{
			//本地V3文件相对路径
			/*String localRelateV3path = "/upload/dataManage/v3/";*/
			//本地WAV文件相对路径
			//File endDirection=new File("E:/path2");
			
			//本地V3文件相对路径
			//本地WAV文件相对路径
			String localRelateWAVpath = "/upload/dataManage/wav/";
			//根据相对路径获取绝对路径
			//根据相对路径获取绝对路径
			String localWAVpath = request.getSession().getServletContext().getRealPath(localRelateWAVpath);
			
			String ftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
			if(!ftpPath.endsWith("/"))ftpPath += "/"; 
			if((!localWAVpath.endsWith("/")) && (!localWAVpath.endsWith("\\")))localWAVpath += "/";
			//打印本地地址
			//System.out.println("wcy=======" + localWAVpath);

			String v3Path = usedTabooService.getV3File(localWAVpath,ftpPath,filePath);
			if(StringUtils.isBlank(v3Path) || v3Path == null){
				//throw new RuntimeException("下载失败");
				return Result.failure(ResultCode.DOWNLOAD_FAILURE);
			}
			//return new OperaterResult<>(true, "", "/bdasjs/upload/dataManage/wav/"+fileName);
			return Result.success(new OperaterResult<>(true, "", localRelateWAVpath + fileName));
		}
		
	}
	
	
	@RequestMapping("/exportJudgeDetail")
	@ResponseBody
	public Result exportJudgeDetail(TabooSession params) throws Exception{
		String downloadFileName = "";
		String downloadPath = "";
		try{
			List<TabooSession> exportJudge = usedTabooService.getExportJudge(params);
			String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate")+"/可疑使用禁语会话数据导出模板.xlsx";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-exportTaboo.xlsx";
			downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/")+downloadFileName;
			usedTabooService.makeTabooExportExcel(exportJudge, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
			return Result.failure();
		}
		//return new OperaterResult<>(true, downloadFileName);
		return Result.success(downloadPath);
	}
	
	@RequestMapping("/downloadResult")
	@ResponseBody
	public void downloadResult(@RequestParam(required=false)String fileName) throws IOException{
		if(fileName.startsWith(",")){
			fileName = fileName.substring(1,fileName.length());
		}
		FileUtils.downloadFile(request.getSession().getServletContext().getRealPath("/upload/qualityJudge"), fileName, response);
	}
	
	@RequestMapping("/cleanRecordDir")
	@ResponseBody
	public Result cleanRecordDir(){
		//本地V3文件相对路径
		String localRelateV3path = "/upload/dataManage/v3/";
		//本地WAV文件相对路径
		String localRelateWAVpath = "/upload/dataManage/wav/";
		
		//根据相对路径获取绝对路径
		URL v3url = this.getClass().getClassLoader().getResource("../.."+localRelateV3path);
		if(null != v3url){
			String localV3path = v3url.getPath();
			logger.info("删除V3文件"+localV3path);
			FileUtils.delAllFile(localV3path);
			logger.info("删除V3文件"+localV3path+"成功");
		}
		
		//根据相对路径获取绝对路径
		URL WAVurl = this.getClass().getClassLoader().getResource("../.."+localRelateWAVpath);
		if(null != WAVurl){
			String localWAVpath = WAVurl.getPath();
			logger.info("删除WAV文件"+localWAVpath);
			FileUtils.delAllFile(localWAVpath);
			logger.info("删除WAV文件"+localWAVpath+"成功");
		}
		//return new OperaterResult<>(true,"","清除缓存文件夹成功！");
		return Result.success("清除缓存文件夹成功");
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
