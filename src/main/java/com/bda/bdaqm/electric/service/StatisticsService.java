package com.bda.bdaqm.electric.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.SortUnitMapper;
import com.bda.bdaqm.electric.mapper.StatisticsMapper;
import com.bda.bdaqm.electric.model.ComboxTree;
import com.bda.bdaqm.electric.model.OrganizationVo;
import com.bda.bdaqm.electric.model.Original;
import com.bda.bdaqm.electric.model.Statistics;
import com.bda.bdaqm.electric.model.StatisticsVo;
import com.bda.bdaqm.electric.model.UnitOrder;
import com.bda.bdaqm.electric.model.unitVo;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.service.AbstractService;

@Service("StatisticsService")
public class StatisticsService extends AbstractService<Statistics>{
	@Autowired
	private StatisticsMapper statisticsMapper;
	
	@Autowired
	private SortUnitMapper sortUnitMapper;
	
	
	public List<ComboBoxItem> getComboxSql(){
		return statisticsMapper.getComboxSql();
	}
	public List<ComboBoxItem> getZWComboxSql(String unitType){
		return statisticsMapper.getZWComboxSql(unitType);
	}
	
	public List<Statistics> getPermission(String type,String TimeBegin,String TimeEnd){
		List<StatisticsVo> list = statisticsMapper.getPermission(type,TimeBegin,TimeEnd);
		List<Statistics> data = new ArrayList<Statistics>();
		Statistics sc = null;
		for(int i=0;i<list.size();i++) {
			StatisticsVo sv = list.get(i);
			if(sv.getPermissionUnitName()!=null&&!sv.getPermissionUnitName().contains("外协单位")) {
				String[] perArrays = sv.getPermissionUnitName().split("/");
				if(sv.getDepartName()!=null&&!sv.getDepartName().contains("外协单位")) {
					String[] departArrays = sv.getDepartName().split("/");
					if(perArrays[2].equals(departArrays[2])) {
						continue;
					}else {
						sv.setPermissionUnitName(perArrays[2]);
					}
					
				}else {
					sv.setPermissionUnitName(perArrays[2]);
				}
			}else if(sv.getPermissionUnitName()!=null&&sv.getPermissionUnitName().contains("外协单位")) {
				if(sv.getDepartName()!=null&&sv.getDepartName().contains("外协单位")) {
					continue;
				}else {
					sv.setPermissionUnitName("外协单位");
				}
			}
			
			
			
			if(sv.getTicketType()==null) {
				sv.setTicketType("88");
			}
			int stationOne = 0,stationTwo = 0,stationThree = 0,lineOne = 0,lineTwo = 0,fireOne = 0,fireTwo = 0,liveWorking = 0,lowVoltage = 0,urgentRepairs = 0,unMarked = 0;
			String departmentOname = "";
			switch(sv.getTicketType()){
			case "11" :
				stationOne =1;
				break;
			case "12" :
				stationTwo =1;
				break;
			case "13" :
				stationThree =1;
				break;
			case "21" :
				lineOne =1;
				break;
			case "22" :
				lineTwo =1;
				break;
			case "31" :
				fireOne =1;
				break;
			case "32" :
				fireTwo =1;
				break;
			case "43" :
				liveWorking =1;
				break;
			case "44" :
				lowVoltage =1;
				break;
			case "51" :
				urgentRepairs =1;
				break;
			case "88" :
				unMarked =1;
				break;
			default:
				break;
			}
			departmentOname = sv.getPermissionUnitName();
			if(departmentOname!=null&&departmentOname.endsWith("供电局")) {
				departmentOname = "广州"+departmentOname;
			}
			sc = new Statistics();
			sc.setDepartmentOid(sv.getDepartmentOid());
			sc.setDepartmentOname(departmentOname);
			sc.setMonth(sv.getWorkEndTime());
			sc.setBaseId(sv.getId());
			sc.setStationOne(String.valueOf(stationOne));
			sc.setStationTwo(String.valueOf(stationTwo));
			sc.setStationThree(String.valueOf(stationThree));
			sc.setLineOne(String.valueOf(lineOne));
			sc.setLineTwo(String.valueOf(lineTwo));
			sc.setFireOne(String.valueOf(fireOne));
			sc.setFireTwo(String.valueOf(fireTwo));
			sc.setLiveWorking(String.valueOf(liveWorking));
			sc.setLowVoltage(String.valueOf(lowVoltage));
			sc.setUrgentRepairs(String.valueOf(urgentRepairs));
			sc.setUnMarked(String.valueOf(unMarked));
			data.add(sc);
		}
		
		return data;
	}
	
	public List<Statistics> getSign(String type,String TimeBegin,String TimeEnd){
		List<StatisticsVo> list= statisticsMapper.getSign(type,TimeBegin,TimeEnd);
		List<Statistics> data = new ArrayList<Statistics>();
		Statistics sc = null;
		for(int i=0;i<list.size();i++) {
			StatisticsVo sv = list.get(i);
			
			if(sv.getSignUnitName()!=null&&!sv.getSignUnitName().contains("外协单位")) {
				String[] signArrays = sv.getSignUnitName().split("/");
				if(sv.getPermissionUnitName()!=null&&!sv.getDepartName().contains("外协单位")) {
					String[] perArrays = sv.getPermissionUnitName().split("/");
					if(signArrays[2].equals(perArrays[2])) {
						continue;
					}
				}
				
				
				if(sv.getDepartName()!=null&&!sv.getDepartName().contains("外协单位")) {
					String[] departArrays = sv.getDepartName().split("/");
					if(signArrays[2].equals(departArrays[2])) {
						continue;
					}else {
						sv.setSignUnitName(signArrays[2]);
					}
					
				}else {
					sv.setSignUnitName(signArrays[2]);
				}
			}else if(sv.getSignUnitName()!=null&&sv.getSignUnitName().contains("外协单位")) {
				if(sv.getPermissionUnitName()!=null&&sv.getPermissionUnitName().contains("外协单位")) {
					continue;
				}
				
				if(sv.getDepartName()!=null&&sv.getDepartName().contains("外协单位")) {
					continue;
				}else {
					sv.setSignUnitName("外协单位");
				}
			}
			
			
			if(sv.getTicketType()==null) {
				sv.setTicketType("88");
			}
			int stationOne = 0,stationTwo = 0,stationThree = 0,lineOne = 0,lineTwo = 0,fireOne = 0,fireTwo = 0,liveWorking = 0,lowVoltage = 0,urgentRepairs = 0,unMarked = 0;
			String departmentOname = "";
			switch(sv.getTicketType()){
			case "11" :
				stationOne =1;
				break;
			case "12" :
				stationTwo =1;
				break;
			case "13" :
				stationThree =1;
				break;
			case "21" :
				lineOne =1;
				break;
			case "22" :
				lineTwo =1;
				break;
			case "31" :
				fireOne =1;
				break;
			case "32" :
				fireTwo =1;
				break;
			case "43" :
				liveWorking =1;
				break;
			case "44" :
				lowVoltage =1;
				break;
			case "51" :
				urgentRepairs =1;
				break;
			case "88" :
				unMarked =1;
				break;
			default:
				break;
			}
			departmentOname = sv.getSignUnitName();
			if(departmentOname!=null&&departmentOname.endsWith("供电局")) {
				departmentOname = "广州"+departmentOname;
			}
			sc = new Statistics();
			sc.setDepartmentOid(sv.getDepartmentOid());
			sc.setDepartmentOname(departmentOname);
			sc.setMonth(sv.getWorkEndTime());
			sc.setBaseId(sv.getId());
			sc.setStationOne(String.valueOf(stationOne));
			sc.setStationTwo(String.valueOf(stationTwo));
			sc.setStationThree(String.valueOf(stationThree));
			sc.setLineOne(String.valueOf(lineOne));
			sc.setLineTwo(String.valueOf(lineTwo));
			sc.setFireOne(String.valueOf(fireOne));
			sc.setFireTwo(String.valueOf(fireTwo));
			sc.setLiveWorking(String.valueOf(liveWorking));
			sc.setLowVoltage(String.valueOf(lowVoltage));
			sc.setUrgentRepairs(String.valueOf(urgentRepairs));
			sc.setUnMarked(String.valueOf(unMarked));
			data.add(sc);
		}
		return data;
	}
	
	public List<Statistics> getAllData(String type,String TimeBegin,String TimeEnd){
		return statisticsMapper.getAllData(type,TimeBegin,TimeEnd);
	}
	public List<Original> getOriginData(String unit,String date,String endMonth,String type){
		//String[] list = oidList.split(",");
		//List<String> filtered = array_unique(list);
		//List<String> filtered=oid.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		if(type==null||type.equals("")) {
			type = "inferno";
		}
		return statisticsMapper.getOriginData(unit,date,endMonth,type);
	}
	public  List<String> array_unique(String[] ss) {
	    List<String> list =new ArrayList<String>();
			for(String s:ss){
				if(!list.contains(s))			
					list.add(s);
			}
			return list;
	}
	
	public Map<String,Object> queryUnit(){
		List<Map<String,String>> list = statisticsMapper.queryUnit();
		Map<String,Object> mapOrg = new HashMap<String,Object>();
		OrganizationVo ov = null;
		for(int i=0;i<list.size();i++) {
			//Map<String,String> map = list.get(i);
			ov = new OrganizationVo();
			ov.setOrganizationName(list.get(i).get("org_name"));
			ov.setOrganizationOid(list.get(i).get("org_id"));
			ov.setSuperorganizationId(list.get(i).get("parent_org_id"));
			//ov.setId(map.get("id"));
			mapOrg.put(list.get(i).get("org_id"), ov);
		}
		return mapOrg;
	}
	
	public Map<String,Object> queryFatherUnit(){
		List<Map<String,String>> list = statisticsMapper.queryUnit();
		Map<String,Object> mapOrg = new HashMap<String,Object>();
		for(int i=0;i<list.size();i++) {
			Map<String,String> map = list.get(i);
			OrganizationVo ov = new OrganizationVo();
			ov.setOrganizationName(map.get("org_name"));
			ov.setOrganizationOid(map.get("org_id"));
			ov.setSuperorganizationId(map.get("parent_org_id"));
			//ov.setId(map.get("id"));
			mapOrg.put(map.get("org_id"), ov);
		}
		return mapOrg;
	}

	public List<Statistics> getNewList(List<Statistics> list) {
		LinkedHashMap<unitVo,Statistics> tempMap = new LinkedHashMap<unitVo,Statistics>();
		unitVo unit = null;
		logger.info("overpass");
		for(Statistics ks:list) {
			unit = new unitVo(ks.getMonth(),ks.getDepartmentOname());
			if(tempMap.containsKey(unit)) {
					ks.setStationOne(String.valueOf(Integer.valueOf(tempMap.get(unit).getStationOne())+Integer.valueOf(ks.getStationOne())));
					ks.setStationTwo(String.valueOf(Integer.valueOf(tempMap.get(unit).getStationTwo())+Integer.valueOf(ks.getStationTwo())));
					ks.setStationThree(String.valueOf(Integer.valueOf(tempMap.get(unit).getStationThree())+Integer.valueOf(ks.getStationThree())));
					ks.setLineOne(String.valueOf(Integer.valueOf(tempMap.get(unit).getLineOne())+Integer.valueOf(ks.getLineOne())));
					ks.setLineTwo(String.valueOf(Integer.valueOf(tempMap.get(unit).getLineTwo())+Integer.valueOf(ks.getLineTwo())));
					ks.setFireOne(String.valueOf(Integer.valueOf(tempMap.get(unit).getFireOne())+Integer.valueOf(ks.getFireOne())));
					ks.setFireTwo(String.valueOf(Integer.valueOf(tempMap.get(unit).getFireTwo())+Integer.valueOf(ks.getFireTwo())));
					ks.setLiveWorking(String.valueOf(Integer.valueOf(tempMap.get(unit).getLiveWorking())+Integer.valueOf(ks.getLiveWorking())));
					ks.setLowVoltage(String.valueOf(Integer.valueOf(tempMap.get(unit).getLowVoltage())+Integer.valueOf(ks.getLowVoltage())));
					ks.setUrgentRepairs(String.valueOf(Integer.valueOf(tempMap.get(unit).getUrgentRepairs())+Integer.valueOf(ks.getUrgentRepairs())));
					ks.setUnMarked(String.valueOf(Integer.valueOf(tempMap.get(unit).getUnMarked())+Integer.valueOf(ks.getUnMarked())));
			}
			tempMap.put(unit, ks);
		}
		logger.info("inferno");
		list.clear();
		list.addAll(tempMap.values());
		return list;
	}
	
	public void makeExportExcel(String type,
			                    List<Statistics> exportData, 
			                    String templatePath, 
			                    String downloadPath) throws Exception {
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
		
		int serialNo = 1;//序号
		
		int sone = 0, stwo = 0, sthree = 0, lone = 0, ltwo = 0, liveWorking = 0, lowVol = 0, 
				urgRep = 0, fone = 0, ftwo = 0;
		int countRowNum = 0;//合计的行数
		for (int i = 0; i < exportData.size(); i++) {
			Statistics sta = exportData.get(i);
			XSSFRow xssfRowResult = xssfResultSheet.createRow(i+4+countRowNum);
			
			int rowIndex = 0;
			//序号
			xssfRowResult.createCell(rowIndex++).setCellValue(serialNo++);
			//月份
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getMonth());
			//单位
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getDepartmentOname());
			//厂站第一种
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getStationOne());
			sone +=  Integer.parseInt(sta.getStationOne());
			//第二种
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getStationTwo());
			stwo +=  Integer.parseInt(sta.getStationTwo());
			//第三种
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getStationThree());
			sthree +=  Integer.parseInt(sta.getStationThree());
			//线路第一种
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getLineOne());
			lone +=  Integer.parseInt(sta.getLineOne());
			//第二种
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getLineTwo());
			ltwo +=  Integer.parseInt(sta.getLineTwo());
			//带电作业工作票
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getLiveWorking());
			liveWorking +=  Integer.parseInt(sta.getLiveWorking());
			//低压配网
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getLowVoltage());
			lowVol +=  Integer.parseInt(sta.getLowVoltage());
			//紧急抢修
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getUrgentRepairs());
			urgRep +=  Integer.parseInt(sta.getUrgentRepairs());
			//动火票一级
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getFireOne());
			fone +=  Integer.parseInt(sta.getFireOne());
			//二级
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getFireTwo());
			ftwo +=  Integer.parseInt(sta.getFireTwo());
			//书面形式布置及记录
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getWrittenForm());
			//调度检修申请单
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getDispatching());
			//不合格票
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getUnqualified());
			//不规范票
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getUnstandard());
			//合格票
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getStandard());
			//规范票
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getQualified());
			//总计
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getTotal());
			//合格率
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getPassRate());
			//规范率
			xssfRowResult.createCell(rowIndex++).setCellValue(sta.getStandardRate());
			
			//只有一个单位，不合并
			if(StringUtils.isBlank(type)){
			if(i == exportData.size()-1 || !sta.getMonth().equals(exportData.get(i+1).getMonth())){//最后一条或者当月最后一条
				serialNo = 1;
				countRowNum++;
				XSSFRow xssfRowResult1 = xssfResultSheet.createRow(i+4+countRowNum);
				
				xssfRowResult1.createCell(0).setCellValue("");
				//月份
				xssfRowResult1.createCell(1).setCellValue("合计");
				xssfRowResult1.createCell(2).setCellValue("");
				//厂站第一种
				xssfRowResult1.createCell(3).setCellValue(sone);
				//第二种
				xssfRowResult1.createCell(4).setCellValue(stwo);
				//第三种
				xssfRowResult1.createCell(5).setCellValue(sthree);
				//线路第一种
				xssfRowResult1.createCell(6).setCellValue(lone);
				//第二种
				xssfRowResult1.createCell(7).setCellValue(ltwo);
				//带电作业工作票
				xssfRowResult1.createCell(8).setCellValue(liveWorking);
				//低压配网
				xssfRowResult1.createCell(9).setCellValue(lowVol);
				//紧急抢修
				xssfRowResult1.createCell(10).setCellValue(urgRep);
				//动火票一级
				xssfRowResult1.createCell(11).setCellValue(fone);
				//二级
				xssfRowResult1.createCell(12).setCellValue(ftwo);
				
				CellRangeAddress cra =new CellRangeAddress(i+4+countRowNum, i+4+countRowNum, 1, 2); // 起始行, 终止行, 起始列, 终止列
				xssfResultSheet.addMergedRegion(cra);
				sone = 0; stwo = 0; sthree = 0; lone = 0; ltwo = 0; 
				liveWorking = 0; lowVol = 0; urgRep = 0; fone = 0; ftwo = 0;
				
				for(Cell cell : xssfRowResult1){
					cell.setCellStyle(style);
				}
			}
			}
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
	public List<Map<String,Object>> queryComboTree(){
		List<Map<String,Object>> treeList  =new ArrayList<Map<String,Object>>();
		List<String> comListZP = statisticsMapper.getComboxTreeZP();
		
		List<ComboxTree> comList =  statisticsMapper.getComboxTree();
		List<ComboxTree> comList2 = new ArrayList<ComboxTree>();
		for(int i=0;i<comList.size();i++) {
			ComboxTree c = comList.get(i);
			if(c.getFatherId().equals("1589BAA876FDBD64E053380F0A0A54B2") 
					&& !comListZP.contains(c.getChildrenId()) 
					&& !c.getChildrenId().equals("1589BAA87C16BD64E053380F0A0A54B2")){
				c.setFatherId("qita");
			}
			//南方电力建设企业集团
			if(c.getChildrenId().equals("1589BAA87C16BD64E053380F0A0A54B2")){
				c.setFatherId("waixie");
			}
			comList2.add(c);
		}
		//设置固定记录，父类为广州供电局有限公司，childrenid为随意设定，无实际意义
		ComboxTree waixie = new ComboxTree();
		waixie.setChildrenId("waixie");
		waixie.setFatherId("1589BAA876FDBD64E053380F0A0A54B2");
		waixie.setLevel("3");
		waixie.setName("外协单位");
		comList2.add(waixie);
		waixie = new ComboxTree();
		waixie.setChildrenId("qita");
		waixie.setFatherId("waixie");
		waixie.setName("其他单位");
		waixie.setLevel("4");
		comList2.add(waixie);
		for(int i=0;i<comList2.size();i++) {
			Map<String, Object> map = null;  
			ComboxTree ct = comList2.get(i);
			//父类名称:中国南方电网有限责任公司
			if(ct.getFatherId().equals("D6B3A87D95354B0EA3C05DD346C03486")) {
				map = new HashMap<String, Object>();  
				map.put("id", comList2.get(i).getChildrenId());         //id  
	            map.put("text",comList2.get(i).getName());      //角色名  
	            map.put("level", comList2.get(i).getLevel());	//单位等级
	            map.put("children", queryComboTreeChildren(comList2, ct.getChildrenId()));
			}
			if(map!=null) {
				treeList.add(map);
			}
		}
		return treeList;
	}
	
	public List<Map<String, Object>> queryComboTreeChildren(List<ComboxTree> list, String fid){
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		for(int j=0;j<list.size();j++) {
			Map<String, Object> map = null;  
			ComboxTree ct = list.get(j);
			if(ct.getFatherId().equals(fid)) {
				map = new HashMap<String, Object>();  
				map.put("id", list.get(j).getChildrenId());  
	            map.put("text", list.get(j).getName());
	            map.put("level", list.get(j).getLevel());	//单位等级
	            map.put("children", queryComboTreeChildren(list, ct.getChildrenId())); 
	            if(null != queryComboTreeChildren(list, ct.getChildrenId()) && queryComboTreeChildren(list, ct.getChildrenId()).size() > 0){
	            	map.put("state", "closed");
	            }
			}
			if(map!=null) {
				childList.add(map);
			}
		}
		return childList;  
	}
	
	public List<Statistics> getRealList(List<Statistics> data,Map<String,Object> map){
		//Map<String,Object> map2 = queryFatherUnit();
		logger.info("cold");
		for(int i=0;i<data.size();i++) {
			Statistics st = data.get(i);
			OrganizationVo ov = (OrganizationVo) map.get(st.getDepartmentOid());
			while(ov!=null&&(!ov.getSuperorganizationId().equals("1589BAA876FDBD64E053380F0A0A54B2")&&!ov.getSuperorganizationId().equals("-1"))) {
				ov = (OrganizationVo) map.get(ov.getSuperorganizationId());
			}
			if(ov!=null) {
				st.setDepatrmentOnameOld(st.getDepartmentOname());
				st.setDepartmentOname(ov.getOrganizationName());				
			}else {
				st.setDepatrmentOnameOld(st.getDepartmentOname());
			}
			
		}
		logger.info("hot");
		//data=getNewList(data);
		return data;
	}
	
	public List<UnitOrder> queryUnitOrder(){
		return sortUnitMapper.queryUnitOrder();
	}
	
	public List<Statistics> getExterNalUnit(String TimeBegin,String TimeEnd,Map<String,Object> map){
		List<Statistics> list= statisticsMapper.getExterNalUnit(TimeBegin,TimeEnd);
		for(int i=0;i<list.size();i++) {
			
			Statistics st = list.get(i);
			OrganizationVo ov = (OrganizationVo) map.get(st.getDepartmentOid());
			while(ov!=null&&(!ov.getSuperorganizationId().equals("1589BAA876FEBD64E053380F0A0A54B2")&&!ov.getSuperorganizationId().equals("-1"))) {
				
				ov = (OrganizationVo) map.get(ov.getSuperorganizationId());
			}
			if(ov!=null) {
				st.setDepatrmentOnameOld(st.getDepartmentOname());
				st.setDepartmentOname(ov.getOrganizationName());				
			}else {
				st.setDepatrmentOnameOld(st.getDepartmentOname());
			}
		}	
		return list;
		
	}
	public List<Statistics> getPermissionExter(String type,String TimeBegin,String TimeEnd){
		List<StatisticsVo> list = statisticsMapper.getPermission(type,TimeBegin,TimeEnd);
		List<Statistics> data = new ArrayList<Statistics>();
		Statistics sc = null;
		for(int i=0;i<list.size();i++) {
			StatisticsVo sv = list.get(i);
			if(sv.getPermissionUnitName()!=null&&sv.getPermissionUnitName().contains("外协单位")) {
				String[] perArrays = sv.getPermissionUnitName().split("/");
				if(sv.getDepartName()!=null&&!sv.getDepartName().contains("外协单位")) {
					sv.setPermissionUnitName(perArrays[1]);	
				}else {
					continue;
				}
			}else if(sv.getPermissionUnitName()!=null&&!sv.getPermissionUnitName().contains("外协单位")) {
				continue;
			}
			sc = new Statistics();
			sc.setDepartmentOid(sv.getDepartmentOid());
			sc.setDepartmentOname(sv.getPermissionUnitName());
			sc.setMonth(sv.getWorkEndTime());
			sc.setBaseId(sv.getId());
			sc.setTotal("1");
			data.add(sc);
		}
		
		return data;
	}
	public List<Statistics> getSignExter(String type,String TimeBegin,String TimeEnd){
		List<StatisticsVo> list= statisticsMapper.getSign(type,TimeBegin,TimeEnd);
		List<Statistics> data = new ArrayList<Statistics>();
		Statistics sc = null;
		for(int i=0;i<list.size();i++) {
			StatisticsVo sv = list.get(i);
			
			if(sv.getSignUnitName()!=null&&sv.getSignUnitName().contains("外协单位")) {
				String[] signArrays = sv.getSignUnitName().split("/");
				if(sv.getPermissionUnitName()!=null&&sv.getDepartName().contains("外协单位")) {
						continue;
				}
				
				if(sv.getDepartName()!=null&&!sv.getDepartName().contains("外协单位")) {
					sv.setSignUnitName(signArrays[1]);
				}else {
					continue;
				}
			}else if(sv.getSignUnitName()!=null&&!sv.getSignUnitName().contains("外协单位")) {
				continue;
			}

			sc = new Statistics();
			sc.setDepartmentOid(sv.getDepartmentOid());
			sc.setDepartmentOname(sv.getSignUnitName());
			sc.setMonth(sv.getWorkEndTime());
			sc.setBaseId(sv.getId());
			sc.setTotal("1");
			data.add(sc);
		}
		return data;
	}
	
	public List<Statistics> getNewListExter(List<Statistics> list) {
		LinkedHashMap<String,Statistics> tempMap = new LinkedHashMap<String,Statistics>();
		for(Statistics ks:list) {
			String key = ks.getDepartmentOname();
			if(tempMap.containsKey(key)) {
				ks.setTotal(String.valueOf(Integer.valueOf(tempMap.get(key).getTotal())+Integer.valueOf(ks.getTotal())));
			}
			tempMap.put(key, ks);
		}
		list.clear();
		list.addAll(tempMap.values());
		return list;
	}
}
