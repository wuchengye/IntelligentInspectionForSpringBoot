package com.bda.bdaqm.electric.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.WorkingHoursMapper;
import com.bda.bdaqm.electric.model.HourModulus;
import com.bda.bdaqm.util.DateUtils;

@Service
public class WorkingHoursService {

	@Autowired
	private WorkingHoursMapper workingHousMapper;
	
	public List<Map<String, Object>> getCountOfTicket(Map<String, String> params){
		return workingHousMapper.getCountOfTicket(params);
	}
	
	public List<Map<String, Object>> getDetailOfAll(Map<String, String> params){
		
		return workingHousMapper.getDetailOfAll(params);
	}
	
	/**
	 * 返回个人数据弹框
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getDetailOfPerson(List<Map<String, Object>> allDetailData, String personName, String unitName) throws Exception{
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> map : allDetailData) {
			if(personName.equals(map.get("person_name")) && unitName.equals(map.get("unit_name"))){
				
				String permisTime = map.get("permission_time")==null?"":(String) map.get("permission_time");
				String workEndTime = map.get("work_end_time")==null?"":(String) map.get("work_end_time");
				
				String gapStartTime = map.get("gap_start_time")==null?"":(String) map.get("gap_start_time");
				String gapTime = map.get("gap_time")==null?"":(String) map.get("gap_time");
				
				String allTime = this.countHours(permisTime, workEndTime);
				String gapUseTime = this.countHours(gapTime, gapStartTime);//间断用时
				
				DecimalFormat df=new DecimalFormat("0.00");
				
				float f1 = Float.valueOf(allTime);
				float f2 = Float.valueOf(gapUseTime);
				
				String sumTime = df.format(f1 - f2);//所耗工时
				
				map.put("gap_use_time", gapUseTime);
				map.put("sum_time", sumTime);
				
				resList.add(map);
			}
		}
		
		Map<String,Object> id = new HashMap<String, Object>();
		Map<String,Object>  curVal = null;
		
		int n = 0;
		for (int i = 0; i < resList.size(); i++) {
			
			curVal = resList.get(i);
			String curId = (String)curVal.get("id");
			
			if(id.containsKey(curId)){
				
				String curGapUseTime = (String)curVal.get("gap_use_time");
				
				Map<String,Object> tmp = finalList.get((int)id.get(curId));
				
				String permisTime = tmp.get("permission_time")==null?"":(String) tmp.get("permission_time");
				String workEndTime = tmp.get("work_end_time")==null?"":(String) tmp.get("work_end_time");
				
				String gapUseTime1 = (String)tmp.get("gap_use_time");
				
				String allTime = this.countHours(permisTime, workEndTime);
				DecimalFormat df=new DecimalFormat("0.00");
				
				float f1 = Float.valueOf(allTime);
				float f2 = Float.valueOf(curGapUseTime);
				float f3 = Float.valueOf(gapUseTime1);
				
				float f4 = f2 + f3;
				
				String sumTime = df.format(f1 - f4);//所耗工时
				
				tmp.put("gap_use_time", df.format(f4));
				tmp.put("sum_time", sumTime);
				
				finalList.set((int)id.get((String)curVal.get("id")), tmp);
				
				curVal = null;
			}else{
				finalList.add(curVal);
				id.put((String)curVal.get("id"), n);
				n++;
				curVal = null;
			}
		}
		
		return finalList;
	}
	
	/**
	 * endTime - startTime
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public String countHours(String startTime, String endTime) throws Exception{
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			Date st = DateUtils.strToDate(startTime);
			Date et = DateUtils.strToDate(endTime);
			DecimalFormat df=new DecimalFormat("0.00");
			long ss = (et.getTime() - st.getTime() ) / 1000;
			String hours = df.format((float)ss / 3600);
			return hours;
		}
		return "0";
	}

	
	public void makeExportExcel(List<Map<String, Object>> exportData, String templatePath, String downloadPath) throws Exception {
		InputStream input = new FileInputStream(templatePath);
		XSSFWorkbook xssfResultWorkbook = new XSSFWorkbook(input);
		XSSFSheet xssfResultSheet = xssfResultWorkbook.getSheetAt(0);
		
		XSSFCellStyle style = xssfResultWorkbook.createCellStyle();//单元格格式
		// 基础样式
		style.setAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框    
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框    
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框    
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框 
		
		for (int i = 0; i < exportData.size(); i++ ) {
			Map<String, Object> sta = exportData.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+2);
			int rowIndex = 0;
			
			String sumHours = (String)sta.get("sum_hours");
			//单位	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("unit_name"));
			//部门	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("deptment_name"));
			//班组	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("group_name"));
			//姓名	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("person_name"));
			//厂站第一种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("stationOneHours"));
			//厂站第二种工作票	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("stationTwoHours"));
			//厂站第三种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("stationThreeHours"));
			//线路第一种工作票	
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("lineOneHours"));
			//线路第二种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("lineTwoHours"));
			//一级动火工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("fireOneHours"));
			//二级动火工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("fireTwoHours"));
			//带电作业工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("liveWorkingHours"));
			//低压配电网工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("lowVoltageHours"));
			//紧急抢修工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("urgentRepairsHours"));
			//书面布置和记录
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("writtenFormHours"));
			//特殊工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((String)sta.get("specialHours"));
			/*//厂站第一种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("station_one"));
			//厂站第二种工作票	
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("station_two"));
			//厂站第三种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("station_three"));
			//线路第一种工作票	
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("line_one"));
			//线路第二种工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("line_two"));
			//一级动火工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("fire_one"));
			//二级动火工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("fire_two"));
			//带电作业工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("live_working"));
			//低压配电网工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("low_voltage"));
			//紧急抢修工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("urgent_repairs"));
			//安全技术交底单
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("safety_technology"));
			//二次措施单
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("twice_measure"));
			//书面布置和记录
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("written_form"));
			//现场勘察记录
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("site_survey"));
			//特殊工作票
			xssfRowResult.createCell(rowIndex++).setCellValue((long)sta.get("special"));*/
			//总持票数
			xssfRowResult.createCell(rowIndex++).setCellValue(getSumTicket(sta));
			//实际总工时
			xssfRowResult.createCell(rowIndex++).setCellValue(sumHours);
			
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
	
	private long getSumTicket(Map<String, Object> map){
		long sum = 0;
		map.remove("person_name");
		map.remove("unit_name");
		map.remove("deptment_name");
		map.remove("group_name");
		map.remove("sum_hours");
		map.remove("stationOneHours");
		map.remove("stationTwoHours");
		map.remove("stationThreeHours");
		map.remove("lineOneHours");
		map.remove("lineTwoHours");
		map.remove("fireOneHours");
		map.remove("fireTwoHours");
		map.remove("liveWorkingHours");
		map.remove("lowVoltageHours");
		map.remove("urgentRepairsHours");
		map.remove("writtenFormHours");
		map.remove("specialHours");
		for (Object m : map.values()) {
			sum += (long)m;
		}
		return sum;
	}

	public List<HourModulus> getModulusByType(String personType) {
		return workingHousMapper.getModulusByType(personType);
	}

	public void updateModulus(HourModulus modulus) {
		workingHousMapper.updateModulus(modulus);
	}
	
	
}
