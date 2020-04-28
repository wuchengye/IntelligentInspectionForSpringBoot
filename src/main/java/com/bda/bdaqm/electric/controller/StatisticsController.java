package com.bda.bdaqm.electric.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.model.Original;
import com.bda.bdaqm.electric.model.Statistics;
import com.bda.bdaqm.electric.model.UnitOrder;
import com.bda.bdaqm.electric.service.StatisticsService;
import com.bda.bdaqm.util.BinarySerchUtils;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
@Controller
@RequestMapping({ "electric/statistics" })
public class StatisticsController extends BaseController{
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("getAllData")
	@ResponseBody
	public Object getAllData(Page page, String updateTimeBegin, String updateTimeEnd,String type) {
		if(type!=null&&type.endsWith("供电局")) {
			type = type.replaceAll("广州", "");
		}
		long t1 = System.currentTimeMillis();
		List<Statistics> data= statisticsService.getAllData(type, updateTimeBegin, updateTimeEnd);
		logger.info("theOne"+(data.size()));
		Map<String,Object> map = statisticsService.queryUnit();
		logger.info("theTwo"+(map.size()));
		data = statisticsService.getRealList(data,map);
		logger.info("theThree"+(data.size()));
		List<Statistics> perData = statisticsService.getPermission(type, updateTimeBegin, updateTimeEnd);
		logger.info("thefour"+(perData.size()));
		List<Statistics> signData = statisticsService.getSign(type, updateTimeBegin, updateTimeEnd);
		logger.info("thefive"+(signData.size()));
		data.addAll(perData);
		data.addAll(signData);
		
		data = statisticsService.getNewList(data);
		logger.info("thesix"+(data.size()));
		long t2 = System.currentTimeMillis();
		logger.info("工作票使用情况消耗时间："+(t2-t1));
		//对data根据单位名顺序显示
		List<UnitOrder> order=statisticsService.queryUnitOrder();
//		System.out.println(order.size());
		logger.info(order.size()+"");
		boolean isIn=false;
		int count=1;//如果查询到的单位名不在数据表中，则优先排序表中的单位，查询到的数据的单位名自动加在后面
		for(Statistics s:data) {
			isIn=false;
			for(UnitOrder u:order) {
				if(s.getDepartmentOname()!=null&&s.getDepartmentOname().equals(u.getUnitname())) {
					s.setUnitOrder(u.getOrderId());
					isIn=true;
					break;
				}
			}
			if(!isIn) {
				s.setUnitOrder(order.size()+count);
				count++;
			}
		}
		data.sort(Comparator.naturalOrder());
		//排序结束
		//若单位名相同，即使月份不同，将相同单位的数据合并
		//利用二分查找
		//Map<Integer,int[]> sameIndexMap=new HashMap<>();
		List<Statistics> removeList=new ArrayList<>();
		removeList.addAll(data);
		int right=-1;
		for(int i=0;i<data.size();i++) {
			int loopCount=0;
			if(i>right) {
				//left=BinarySerchUtils.search(data.get(i).getUnitOrder(), data, 0);
				right=BinarySerchUtils.search(data.get(i).getUnitOrder(), data, 1);
				int sumStationOne = 0,sumStationTwo=0,sumStationThree=0,sumLineOne=0,sumLineTwo=0,sumLiveWorking=0,sumLowVoltage=0,sumUrgentRepairs=0,sumFireOne=0,sumFireTwo=0,sumUnMarked = 0;
				//String sumOidList = "";
				for(int j=i;j<=right;j++) {
					 loopCount++;
					 sumStationOne+=Integer.parseInt(data.get(j).getStationOne());
					 sumStationTwo+=Integer.parseInt(data.get(j).getStationTwo());
					 sumStationThree+=Integer.parseInt(data.get(j).getStationThree());
					 sumLineOne+=Integer.parseInt(data.get(j).getLineOne());
					 sumLineTwo+=Integer.parseInt(data.get(j).getLineTwo());
					 sumLiveWorking+=Integer.parseInt(data.get(j).getLiveWorking());
					 sumLowVoltage+=Integer.parseInt(data.get(j).getLowVoltage());
					 sumUrgentRepairs+=Integer.parseInt(data.get(j).getUrgentRepairs());
					 sumFireOne+=Integer.parseInt(data.get(j).getFireOne());
					 sumFireTwo+=Integer.parseInt(data.get(j).getFireTwo());
					 sumUnMarked+=Integer.parseInt(data.get(j).getUnMarked());
					 //sumOidList = sumOidList.length()>0?sumOidList+","+data.get(j).getOidlist():data.get(j).getOidlist();
				}
				data.get(i).setStationOne(sumStationOne+"");
				data.get(i).setStationTwo(sumStationTwo+"");
				data.get(i).setStationThree(sumStationThree+"");
				data.get(i).setLineOne(sumLineOne+"");
				data.get(i).setLineTwo(sumLineTwo+"");
				data.get(i).setLiveWorking(sumLiveWorking+"");
				data.get(i).setLowVoltage(sumLowVoltage+"");
				data.get(i).setUrgentRepairs(sumUrgentRepairs+"");
				data.get(i).setFireOne(sumFireOne+"");
				data.get(i).setFireTwo(sumFireTwo+"");
				data.get(i).setUnMarked(sumUnMarked+"");
				//data.get(i).setOidlist(sumOidList);
				if(loopCount>1) {
					data.get(i).setMonth(updateTimeBegin+"~"+updateTimeEnd);
				}
				
				
				
			}else {
				data.remove(i);
				i--;
				right--;
			}

		}
	
		List<Statistics> resList = new ArrayList<Statistics>();
		if( data!=null && !data.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > data.size() ) {
				max = data.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		PageInfo<Statistics> pager = new PageInfo<Statistics>(data);
		Map<String,Object> mapTemp = new HashMap<String,Object>();
		mapTemp.put("show", resList);
		mapTemp.put("map", map);
		mapTemp.put("total", pager.getTotal());
		//return new DataGrid(resList, pager.getTotal());
		return mapTemp;
	}
	@RequestMapping("getOriginData")
	@ResponseBody
	public Object getOriginData(Page page, String month, String unit,String type,String field) {
		//String[] array = {"厂站工作票第一种","厂站工作票第二种","厂站工作票第三种","线路工作票第一种","线路工作票第二种","动火票第一种","动火票第二种"};
//		String[] array = {"一级动火工作票","二级动火工作票","线路第一种工作票","线路第二种工作票","厂站第一种工作票","厂站第三种工作票","厂站第二种工作票"};
//		String[] fieldArray = {"fireOne","fireTwo","lineOne","lineTwo","stationOne","stationThree","stationTwo"};
//		if(Arrays.asList(fieldArray).contains(field)) {
//			int index = Arrays.binarySearch(fieldArray,field);
//			type = array[index];
//		}
		
		String endMonth = "";
		String date ="";
		if(month.indexOf("~")!=-1) {
			String[] monthList = month.split("~");
			date = monthList[0];
			endMonth = monthList[1];
			
			endMonth = endMonth.replaceAll("%", "");
			
		}else {
			date=month;
		}
		
		List<Original> data= statisticsService.getOriginData(unit, date,endMonth ,field);
		List<Original> resList = new ArrayList<Original>();
		if( data!=null && !data.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > data.size() ) {
				max = data.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		
		PageInfo<Original> pager = new PageInfo<Original>(data);
		return new DataGrid(resList, pager.getTotal());
	}
	
	@RequestMapping("export")
	@ResponseBody
	public Object exportData(String updateTimeBegin, String updateTimeEnd,String type) {
		String downloadFileName = "";
		try {
			Date pre = new Date();
			if(type.endsWith("供电局")) {
				type = type.replaceAll("广州", "");
			}
			List<Statistics> exportData= statisticsService.getAllData(type, updateTimeBegin, updateTimeEnd);
			Map<String,Object> map = statisticsService.queryUnit();
			exportData = statisticsService.getRealList(exportData,map);
			List<Statistics> perData = statisticsService.getPermission(type, updateTimeBegin, updateTimeEnd);
			List<Statistics> signData = statisticsService.getSign(type, updateTimeBegin, updateTimeEnd);
			exportData.addAll(perData);
			exportData.addAll(signData);
			exportData = statisticsService.getNewList(exportData);
			Collections.sort(exportData,new Comparator<Statistics>() {

				@Override
				public int compare(Statistics o1, Statistics o2) {
					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
					try {
						long t1 = sim.parse(o1.getMonth()).getTime();
						long t2 = sim.parse(o2.getMonth()).getTime();
						if(t1>t2) {
							return -1;
						}else if(t1<t2) {
							return 1;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return 0;
				}
				
			});
			logger.info("导出   查询工作票使用情况统计表数据耗时 - >" + (new Date().getTime() - pre.getTime()) + "毫秒");

			String templatePath = request.getSession().getServletContext().getRealPath("/upload/electric")+ File.separatorChar +"Statistics.xlsx";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			downloadFileName = df.format(new Date()) + "-工作票使用情况统计表.xlsx";
			String downloadPath = request.getSession().getServletContext().getRealPath("/upload/temp")+ File.separatorChar +downloadFileName;
		
			statisticsService.makeExportExcel(type, exportData, templatePath, downloadPath);
		}catch(Exception e){
			logger.error("程序异常：" + e);
			e.printStackTrace();
		}
		return new OperaterResult<>(true, downloadFileName);
	}
	
	@RequestMapping("getExternalUnit")
	@ResponseBody		
	public Object getExternalUnit(Page page,String TimeBegin,String TimeEnd) {
	
		Map<String,Object> map = statisticsService.queryUnit();
		List<Statistics> data = statisticsService.getExterNalUnit(TimeBegin, TimeEnd,map);
		List<Statistics> perData = statisticsService.getPermissionExter("%外协单位%", TimeBegin, TimeEnd);
		List<Statistics> signData = statisticsService.getSignExter("%外协单位%", TimeBegin, TimeEnd);
		data.addAll(perData);
		data.addAll(signData);
		data = statisticsService.getNewListExter(data);
		List<Statistics> resList = new ArrayList<Statistics>();
		if( data!=null && !data.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > data.size() ) {
				max = data.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		PageInfo<Statistics> pager = new PageInfo<Statistics>(data);
		return new DataGrid(resList, pager.getTotal());
	}
}
