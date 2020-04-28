package com.bda.bdaqm.risk.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.risk.mapper.UsedTabooMapper;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class UsedTabooService extends AbstractService<TabooSession>{
	
	@Autowired
	public UsedTabooMapper usedTabooMapper;
	
	public List<TabooSession> getTabooJudgeDetail( Page page,TabooSession params){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<TabooSession> resultList = usedTabooMapper.getTabooJudgeDetail(params);
		return resultList;
	}
	
	public List<TabooSession> getExportJudge( TabooSession params){
		List<TabooSession> resultList = usedTabooMapper.getTabooJudgeDetail(params);
		return resultList;
	}
	
	public void makeTabooExportExcel(List<TabooSession> exportJudge, String templatePath, String downloadPath) throws Exception {
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		for(int i = 0; i < exportJudge.size(); i++) {
			TabooSession euc = exportJudge.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+2);
			int rowIndex = 0;
			if(StringUtils.isBlank(euc.getFileName())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getFileName());
			}
			if(StringUtils.isBlank(euc.getRecordDate())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getRecordDate());
			}
			if(StringUtils.isBlank(euc.getKeyword())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getKeyword());
			}
			if(StringUtils.isBlank(euc.getIsmute45s())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				if(euc.getIsmute45s().equals("1")){
					xssfRowResult.createCell(rowIndex++).setCellValue("是");
				}else{
					xssfRowResult.createCell(rowIndex++).setCellValue("否");
				}
			}
			if(StringUtils.isBlank(euc.getMuteLocation())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getMuteLocation());
			}
			if(StringUtils.isBlank(euc.getContactTime())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getContactTime());
			}
			if(StringUtils.isBlank(euc.getCheckStatus())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getCheckStatus());
			}
			if(StringUtils.isBlank(euc.getCheckAccounts())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getCheckAccounts());
			}
			if(StringUtils.isBlank(euc.getPersonIsProblem())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getPersonIsProblem());
			}
			if(StringUtils.isBlank(euc.getTransferIstrue())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getTransferIstrue());
			}
			if(StringUtils.isBlank(euc.getProblemDescribe())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getProblemDescribe());
			}
			if(StringUtils.isBlank(euc.getTrueTransferContent())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getTrueTransferContent());
			}
			if(StringUtils.isBlank(euc.getRemark())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getRemark());
			}
		}
		FileOutputStream os = new FileOutputStream(downloadPath);
		xssfResultWorkbook.write(os);
		
		input.close();
		os.close();
		xssfResultWorkbook.close();
	}
	
	public TabooSession getTabooSessionById(String sessionId){
		return usedTabooMapper.getTabooSessionById(sessionId);
	}
	
	public int updateTobooCheckResult(TabooSession params){
		return usedTabooMapper.updateTobooCheckResult(params);
	}
	
	public int insertCheckHistory(TabooSession params){
		return usedTabooMapper.insertCheckHistory(params);
	}
	
	public List<SessionDetail> selectSessionDetail(String sessionId){
		return usedTabooMapper.selectSessionDetail(sessionId);
	}
	
	/**
	 * 下载V3文件，如下载成功返回v3文件绝对路径，失败返回null
	 * @param localPath
	 * @param ftpPath
	 * @param xmlPath
	 * @return
	 * @throws Exception
	 */
	public String getV3File(String localPath,String ftpPath,String xmlPath) throws Exception{
		//初始化FtpUtil
		/*String ftpIp = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.FTP_IP_KEY);
		String ftpAccount = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.FTP_ACCOUNT_KEY);
		String ftpPW = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.FTP_PW_KEY);
		FtpUtil ftpUtil = new FtpUtil(ftpIp, 21, ftpAccount, ftpPW);*/
		String ip = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_IP);
		String name = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_NAME);
		String pwd = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PWD);
		String sftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
		int port = Integer.parseInt(PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PORT));
		SFTPUtil3 sftp = new SFTPUtil3(ip, port, name, pwd);
		String[] tmpPath = xmlPath.split("/");
		//截取v3文件名
		String fileName = tmpPath[tmpPath.length-1];
		int i = xmlPath.indexOf(fileName);
		//截取v3文件的父目录结构
		xmlPath = sftpPath+xmlPath.substring(0, i);
		if((!xmlPath.endsWith("/")) && (!xmlPath.endsWith("\\")))xmlPath += "/";
		//拼接v3父文件夹路径
		ftpPath = ftpPath + xmlPath;
		//拼接本地v3文件夹路径
		//localPath = localPath;
		
		try{
			sftp.connect();
			/**
			 * @param remotPath：远程下载目录(以路径符号结束)
		     * @param remoteFileName：下载文件名
		     * @param localPath：本地保存目录(以路径符号结束)
		     * @param localFileName：保存文件名
		     * @return
			 */
			sftp.downloadFile(xmlPath, fileName, localPath, fileName);
			//sftp.downloadFile(localPath,ftpPath,fileName);
			return localPath+fileName;
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e.toString());
			return null;
		}finally{
			try {
				sftp.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error(e.toString());
			}
		}
		
	}
}
