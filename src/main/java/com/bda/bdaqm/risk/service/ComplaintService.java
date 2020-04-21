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

import com.bda.bdaqm.risk.mapper.ComplaintMapper;
import com.bda.bdaqm.risk.mapper.UsedTabooMapper;
import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ComplaintService extends AbstractService<TabooSession>{
	
	@Autowired
	public ComplaintMapper complaintMapper;
	
	public List<ComplaintSession> getComplaintJudgeDetail( Page page,ComplaintSession params){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<ComplaintSession> resultList = complaintMapper.getComplaintJudgeDetail(params);
		return resultList;
	}
	
	public List<ComplaintSession> getExportJudge( ComplaintSession params){
		List<ComplaintSession> resultList = complaintMapper.getComplaintJudgeDetail(params);
		return resultList;
	}
	
	public void makeComplaintExportExcel(List<ComplaintSession> exportJudge, String templatePath, String downloadPath) throws Exception {
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		for(int i = 0; i < exportJudge.size(); i++) {
			ComplaintSession euc = exportJudge.get(i);
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
	
	public ComplaintSession getComplaintSessionById(String sessionId){
		return complaintMapper.getComplaintSessionById(sessionId);
	}
	
	public int updateComplaintCheckResult(ComplaintSession params){
		return complaintMapper.updateComplaintCheckResult(params);
	}
	
	public int insertCheckHistory(ComplaintSession params){
		return complaintMapper.insertCheckHistory(params);
	}
	
	
}
