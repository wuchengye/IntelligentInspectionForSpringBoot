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

import com.bda.bdaqm.risk.mapper.DubiousMapper;
import com.bda.bdaqm.risk.model.NotDubious;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class DubiousService extends AbstractService<NotDubious>{
	
	@Autowired
	public DubiousMapper dubiousMapper;
	
	public List<NotDubious> getDubiousDetail( Page page,NotDubious params){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<NotDubious> resultList = dubiousMapper.getDubiousData(params);
		return resultList;
	}
	public List<NotDubious> getExportDubious(NotDubious params){
		List<NotDubious> resultList = dubiousMapper.getDubiousData(params);
		return resultList;
	}
	public NotDubious getDubiousById(String sessionId){
		return dubiousMapper.getDubiousById(sessionId);
	}
	
	public List<SessionDetail> getSessionDetail(String sessionId){
		return dubiousMapper.getSessionDetail(sessionId);
	}
	
	public void makeNotDubiousExcel(List<NotDubious> exportJudge, String templatePath, String downloadPath) throws Exception{
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		for(int i = 0; i < exportJudge.size(); i++) {
			NotDubious euc = exportJudge.get(i);
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
			if(StringUtils.isBlank(euc.getContactTime())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getContactTime());
			}
		}
		FileOutputStream os = new FileOutputStream(downloadPath);
		xssfResultWorkbook.write(os);
		
		input.close();
		os.close();
		xssfResultWorkbook.close();
	}

	public int updateFileNameAndFilePath(String sessionId, String fileName, String filePath) {
		return dubiousMapper.updateFileNameAndFilePath(sessionId, fileName, filePath);
	}
	
}
