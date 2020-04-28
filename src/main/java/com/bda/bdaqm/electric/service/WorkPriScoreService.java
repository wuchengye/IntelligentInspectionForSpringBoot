package com.bda.bdaqm.electric.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.WorkPriScoreMapper;
import com.bda.bdaqm.electric.model.Modulu;
import com.bda.bdaqm.electric.model.MonthReward;
import com.bda.bdaqm.electric.model.ScoreSummary;
import com.bda.bdaqm.electric.model.YearReward;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.service.AbstractService;

@Service
public class WorkPriScoreService extends AbstractService<ScoreSummary>{
	@Autowired
	private WorkPriScoreMapper workPriScoreMapper;
	
	public List<ScoreSummary> getWorkPriScore(String reportType, String year, String unitName, 
			                                  String personName, String specialty, String classes,
			                                  String orgId,String level){
		List<Modulu> modules = this.getModulusByType(reportType);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("reportType", reportType);
		params.put("modules", modules.get(0));
		params.put("year", year);
		params.put("unitName", unitName);
		params.put("personName", personName);
		params.put("specialty", specialty);
		params.put("classes", classes);
		params.put("orgId", orgId);
		params.put("level", level);
		List<ScoreSummary> res = workPriScoreMapper.getWorkPriScore(params);
		List<MonthReward> monthRwList = this.getMonthVerify(reportType);
		List<YearReward> yearRwList = this.getYearVerify(reportType);
		Collections.reverse(monthRwList);
//		for (YearReward y : yearRwList) {
//			System.out.println(y.toString());
//		}
//		for (MonthReward m : monthRwList) {
//			System.out.println(m.toString());
//		}
		for (ScoreSummary ss : res) {
			ss.setLevelAndMoney(monthRwList, yearRwList);
		}
		return res;
	}
	
	public void makeExportExcel(String reportType, String year,
			                    List<ScoreSummary> exportData, 
			                    String templatePath, 
			                    String downloadPath) throws Exception {
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		
		XSSFRow firstRow = xssfResultSheet.getRow(0);//获取第一行
		firstRow.getCell(0).setCellValue(year + "年" + reportType + "得分汇总表");
		
		XSSFCellStyle style = xssfResultWorkbook.createCellStyle();//单元格格式
		// 基础样式
		style.setAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框    
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框    
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框    
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框 
		
		int serialNo = 1;//序号
		
		for (int i = 0; i < exportData.size(); i++) {
			ScoreSummary sta = exportData.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+2);
			
			int rowIndex = 0;
			//序号
			xssfRowResult.createCell(rowIndex++).setCellValue(serialNo++);
			//单位名称
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getUnitName());
			//部门
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getDeptmentName());
			//班组
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getGroupName());
			//人员姓名
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getPersonName());
			//所属专业
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getSpecialty());
			//所属班组
			//xssfRowResult.createCell(rowIndex++).setCellValue(sta.getClasses());
			//一月得分
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getOne());
			//二月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getTwo());
			//三月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getThree());
			//四月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getFour());
			//五月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getFive());
			//六月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getSix());
			//七月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getSeven());
			//八月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getEight());
			//九月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getNine());
			//十月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getTen());
			//十一月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getEleven());
			//十二月
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getTwelve());
			//汇总
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getTotal());
			//姓名
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthName());
			//得分
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthScore());
			//三种人资格
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthQualifications());
			//专业
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthMajor());
			//奖励级别
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthLevel());
			//奖励金额
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonthMoney());
			//单位
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearUnitName());
			//姓名
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearName());
			//得分
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearScore());
			//三种人资格
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearQualifications());
			//专业
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearMajor());
			//奖励级别
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearLevel());
			//奖励金额
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getYearMoney());
			
			//给每个单元格设置样式
			for(Cell cell : xssfRowResult){
				cell.setCellStyle(style);
			}
			
		}
		
		FileOutputStream os = new FileOutputStream(downloadPath);
		xssfResultWorkbook.write(os);
		
		input.close();
		os.close();
		xssfResultWorkbook.close();
		
	}
	
	//获取单位信息
    public List<ComboBoxItem> getUnitCombobox(){
    	//List<ComboBoxItem> data = workPriScoreMapper.getUnitCombobox();
    	return workPriScoreMapper.getUnitCombobox();
    }
    
    //获取专业信息
    public List<ComboBoxItem> getSpecialtyCombobox(){
    	return workPriScoreMapper.getSpecialtyCombobox();
    }
    
    //获取班组信息
    public List<ComboBoxItem> getClassCombobox(String unitName){
    	return workPriScoreMapper.getClassCombobox(unitName);
    }
    
    //获取月度奖励分数
    public List<MonthReward> getMonthReward(){
    	return workPriScoreMapper.getMonthReward();
    }
    public List<MonthReward> getMonthVerify(String type){
    	return workPriScoreMapper.getMonthVerify(type);
    }
    //修改月度奖励分数
    public void updateMonth(MonthReward monthReward) {
    	workPriScoreMapper.updateMonth(monthReward);
    }
    //获取年度奖励分数  
    public List<YearReward> getYearReward(){
    	return workPriScoreMapper.getYearReward();
    }
    public List<YearReward> getYearVerify(String type){
    	return workPriScoreMapper.getYearVerify(type);
    }
    //修改年度奖励分数
    public void updateYear(YearReward yearReward) {
    	workPriScoreMapper.updateYear(yearReward);
    }
    //获取系数
    public List<Modulu> getModulus(){
    	return workPriScoreMapper.getModulus();
    }
    public List<Modulu> getModulusByType(String type){
    	return workPriScoreMapper.getModulusByType(type);
    }
    //修改系数
    public void updateModulus(Modulu modulus) {
    	workPriScoreMapper.updateModulus(modulus);
    }
}
