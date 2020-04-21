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

import com.bda.bdaqm.risk.mapper.DataManageMapper;
import com.bda.bdaqm.risk.model.DataManage;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class DataManageService extends AbstractService<DataManage>{
	
	@Autowired
	public DataManageMapper dataManageMapper;
	
	public List<DataManage> getDataManage( Page page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<DataManage> resultList = dataManageMapper.getDataManage();
		return resultList;
	}
	public List<DataManage> getExportDataManage(){
		List<DataManage> resultList = dataManageMapper.getDataManage();
		return resultList;
	}

	
	public void makeDataManageExcel(List<DataManage> exportJudge, String templatePath, String downloadPath) throws Exception{
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		for(int i = 0; i < exportJudge.size(); i++) {
			DataManage euc = exportJudge.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+1);
			int rowIndex = 0;
			if(StringUtils.isBlank(euc.getDataDate())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getDataDate());
			}
			if(StringUtils.isBlank(euc.getRecordCompression())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getRecordCompression());
			}
			if(StringUtils.isBlank(euc.getRecordVolume())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getRecordVolume());
			}
			if(StringUtils.isBlank(euc.getQualityStartTime())){
				xssfRowResult.createCell(rowIndex++).setCellValue("-");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(euc.getQualityStartTime());
			}
		}
		FileOutputStream os = new FileOutputStream(downloadPath);
		xssfResultWorkbook.write(os);
		
		input.close();
		os.close();
		xssfResultWorkbook.close();
	}
	
}
