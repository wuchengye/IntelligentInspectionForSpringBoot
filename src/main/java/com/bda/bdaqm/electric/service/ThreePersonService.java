package com.bda.bdaqm.electric.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.ThreePersonMapper;
import com.bda.bdaqm.electric.model.ThpsDialogDetail;
import com.bda.bdaqm.electric.model.ThreePerson;

@Service
public class ThreePersonService {

	@Autowired
	private ThreePersonMapper mapper;
	
	/**
	 * 
	 * @param updateTimeBegin 开始日期
	 * @param updateTimeEnd   结束日期
	 * @param specialty 
	 * @param quickSearch     快速搜索条件
	 * @return List<ThreePerson>
	 */
	public List<ThreePerson> getThreePersonData(String updateTimeBegin, String updateTimeEnd,String unitName,
			                                    String threeType ,String personName, String specialty,String orgId
			                                    ,String level){
		Map<String,String> params = new HashMap<String,String>();
		params.put("updateTimeBegin", updateTimeBegin);
		params.put("updateTimeEnd", updateTimeEnd);
		params.put("unitName", unitName);
		params.put("specialty", specialty);
		params.put("threeType", threeType);
		params.put("personName", personName);
		params.put("orgId", orgId);
		params.put("level", level);
		return mapper.getThreePersonData(params);
	}
	
	/**
	 * 查询持票数
	 * @param updateTimeBegin
	 * @param updateTimeEnd
	 * @param unitName
	 * @param threeType
	 * @param personName
	 * @param specialty
	 * @return
	 */
	public List<ThreePerson> getKeepTicketAmount(String updateTimeBegin, String updateTimeEnd,String unitName,
            String threeType ,String personName, String specialty,String orgId,String level){
		Map<String,String> params = new HashMap<String,String>();
		params.put("updateTimeBegin", updateTimeBegin);
		params.put("updateTimeEnd", updateTimeEnd);
		params.put("unitName", unitName);
		params.put("specialty", specialty);
		params.put("threeType", threeType);
		params.put("personName", personName);
		params.put("orgId", orgId);
		params.put("level", level);
		return mapper.getKeepTicketAmount(params);
	}
	
	/**
	 * 获取源数据
	 * @param ticketNum   
	 * @param personName 
	 * @return
	 */
	public List<ThpsDialogDetail> getOriginalData(String ticketNum,String personName){
		return mapper.getOriginalData(ticketNum, personName);
	}

	
	public void makeExportExcel(List<ThreePerson> exportData, String templatePath, String downloadPath)throws Exception{
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		
		for (int i = 0; i < exportData.size(); i++) {
			ThreePerson tp = exportData.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+2);	
			int rowIndex = 0;
			//单位名称
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getUnitName());
			//部门
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getDeptmentName());
			//班组
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getGroupName());
			//专业类别
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getSpecialty());
			//班/站/所
			//xssfRowResult.createCell(rowIndex++).setCellValue(tp.getClasses());
			//姓名
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getPersonName());
			//“三种人”类别
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getThreeType());
			//持票数
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getKeepTicketAmount());
			//最后一次持工作票信息
			if(null!=tp.getKeepTicketAmount()&&!"0".equals(tp.getKeepTicketAmount())){
				xssfRowResult.createCell(rowIndex++).setCellValue("");
			}else{
				xssfRowResult.createCell(rowIndex++).setCellValue(tp.getTheLast());
			}
			//有效期
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getRigthTime());
			//备注
			xssfRowResult.createCell(rowIndex++).setCellValue(tp.getRemark());
		}
		FileOutputStream os = new FileOutputStream(downloadPath);
		xssfResultWorkbook.write(os);
		
		input.close();
		os.close();
		xssfResultWorkbook.close();
	}
	
	public List<Map<String,Object>> getAmountOfTypeFromKeep(Map<String,String> params){
		return mapper.getAmountOfTypeFromKeep(params);
	}
	
}
