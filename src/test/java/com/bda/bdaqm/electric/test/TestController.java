package com.bda.bdaqm.electric.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.electric.test.TypeAndDate;
import com.bda.bdaqm.electric.model.Statistics;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;

@Controller
@RequestMapping({ "dict/caliberword" })
public class TestController extends BaseController{
	@Autowired
	private TestService testService; 
	
	
	@RequestMapping("testtest")
	@ResponseBody
	public Object selectAndInsert() {
		List<Map<String,String>> Operation  = testService.SelectOperation();
		List<Map<String,String>> WorkLast  = testService.SelectWorkLast();
		List<TypeAndDate> list= new ArrayList<TypeAndDate>();
//		List<String> typeList = new ArrayList<String>();
//		typeList.add("厂站第一种工作票");
//		typeList.add("厂站第二种工作票");
//		typeList.add("厂站第三种工作票");
//		typeList.add("低压配电网工作票");
//		typeList.add("带电作业工作票");
//		typeList.add("紧急抢修工作票");
//		typeList.add("线路第一种工作票");
//		typeList.add("线路第二种工作票");
//		typeList.add("二级动火工作票");
//		typeList.add("一级动火工作票");
		for(int i=0;i<Operation.size();i++) {
			for(int j=0;j<WorkLast.size();j++) {
				if(WorkLast.get(j).get("WORK_LAST_TIME_NEW")==null||WorkLast.get(j).get("WORK_LAST_TIME_NEW").equals("")) {
					continue;
				}
				TypeAndDate tad = new TypeAndDate();
				tad.setType(Operation.get(i).get("OPERATION_NEW"));
				tad.setDate(WorkLast.get(j).get("WORK_LAST_TIME_NEW"));
				list.add(tad);
			}
		}
		Long t1 = System.currentTimeMillis();
		List<Statistics> dataList = new ArrayList<Statistics>();
		for(int i=0;i<list.size();i++) {
			TypeAndDate tad = list.get(i);
			String LIVE_WORKING =testService.SelectLineWorking(tad.getType(),tad.getDate());
			String LOW_VOLTAGE =testService.SelectLowVo(tad.getType(),tad.getDate());
			String URGENT_REPAIRS =testService.SelectUrgent(tad.getType(),tad.getDate());
			String STATION_ONE =testService.SelectStationOne(tad.getType(),tad.getDate());
			String STATION_TWO =testService.SelectStationTwo(tad.getType(),tad.getDate());
			String STATION_THREE =testService.SelectStationThree(tad.getType(),tad.getDate());
			String LINE_ONE =testService.SelectLineOne(tad.getType(),tad.getDate());
			String LINE_TWO =testService.SelectLineTwo(tad.getType(),tad.getDate());
			String FIRE_ONE =testService.SelectFireOne(tad.getType(),tad.getDate());
			String FIRE_TWO =testService.SelectFireTwo(tad.getType(),tad.getDate());
			if(LIVE_WORKING==null||LIVE_WORKING.equals("")) {
				LIVE_WORKING="0";
			}
			if(LOW_VOLTAGE==null||LOW_VOLTAGE.equals("")) {
				LOW_VOLTAGE="0";
			}
			if(URGENT_REPAIRS==null||URGENT_REPAIRS.equals("")) {
				URGENT_REPAIRS="0";
			}
			if(STATION_ONE==null||STATION_ONE.equals("")) {
				STATION_ONE="0";
			}
			if(STATION_TWO==null||STATION_TWO.equals("")) {
				STATION_TWO="0";
			}
			if(STATION_THREE==null||STATION_THREE.equals("")) {
				STATION_THREE="0";
			}
			if(LINE_ONE==null||LINE_ONE.equals("")) {
				LINE_ONE="0";
			}
			if(LINE_TWO==null||LINE_TWO.equals("")) {
				LINE_TWO="0";
			}
			if(FIRE_ONE==null||FIRE_ONE.equals("")) {
				FIRE_ONE="0";
			}
			if(FIRE_TWO==null||FIRE_TWO.equals("")) {
				FIRE_TWO="0";
			}
			Statistics st = new Statistics();
			st.setLiveWorking(LIVE_WORKING);
			st.setLowVoltage(LOW_VOLTAGE);
			st.setUrgentRepairs(URGENT_REPAIRS);
			st.setStationOne(STATION_ONE);
			st.setStationTwo(STATION_TWO);
			st.setStationThree(STATION_THREE);
			st.setLineOne(LINE_ONE);
			st.setLineTwo(LINE_TWO);
			st.setFireOne(FIRE_ONE);
			st.setFireTwo(FIRE_TWO);
			st.setUnit(tad.getType());
			st.setMonth(tad.getDate());
			dataList.add(st);
		}
		Long t2 = System.currentTimeMillis();
		System.out.println("所消耗时间："+(t2-t1));
		testService.addData(dataList);
		return new OperaterResult<>();
	}

}
