package com.bda.bdaqm.electric.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.TicketTypeStatisticMapper;
import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.model.TicketTypeStatistic;

@Service
public class TicketTypeStatisticService {
	@Autowired
	private TicketTypeStatisticMapper ticketTypeStatisticMapper;

	private final String[] monthArr = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	private final String[] mainUnitArr = { "调控中心", "输电管理一所", "输电管理二所", "变电管理一所", "变电管理二所", "变电管理三所" };

	private final String[] UnitArr = { "越秀", "荔湾", "海珠", "天河", "黄埔", "白云", "南沙", "番禺", "花都", "增城", "从化" };

	private final String[] otherUnitArr = { "计量中心", "试研院", "通信中心" };

	private final String[] typeArr = { "11", "12", "13", "21", "22", "44", "43", "51", "31", "88", "66" };

	private final String[] stateArr = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };

	public Map<String, List<String>> statisticTicketType(String updateTimeBegin, String updateTimeEnd, String unit) {

		List<TicketTypeStatistic> list = ticketTypeStatisticMapper.statisticTicketType(updateTimeBegin, updateTimeEnd,
				unit);

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> quality = new ArrayList<String>();// 合格票数数组
		List<String> noquality = new ArrayList<String>();// 不合格票数数组
		List<String> standard = new ArrayList<String>(); // 规范票数数组
		List<String> noStandard = new ArrayList<String>();// 不规范票数数组
		List<String> stationOne = new ArrayList<String>();// 厂站一种票数组
		List<String> stationTwo = new ArrayList<String>();// 厂站二种票数组
		List<String> stationThree = new ArrayList<String>();// 厂站三种票数组
		List<String> lineOne = new ArrayList<String>();// 线路一种票数组
		List<String> lineTwo = new ArrayList<String>();// 线路二种票数组
		List<String> fire = new ArrayList<String>();// 动火票数组
		List<String> liveWorking = new ArrayList<String>();// 带电作业工作票数组
		List<String> lowVoltage = new ArrayList<String>();// 低压配电网工作票数组
		List<String> urgentRepairs = new ArrayList<String>();// 紧急抢修工作票数组
		List<String> principal = new ArrayList<String>();// 局内负责人工作票数组
		List<String> constructUnit = new ArrayList<String>();// 施工单位工作票数组
		// 把计算出来的数据，转换成前端图表需要的数组结构数据
		// 双循环两个数组，判断统计的数据是否有某一月无数据，如果无数据则赋值0
		for (int i = 0; i < monthArr.length; i++) {

			for (int j = 0; j < list.size(); j++) {
				TicketTypeStatistic tts = list.get(j);
				// 对时间格式yyyy-MM截取月份
				if (tts.getTime().length() >= 7) {
					tts.setTime(tts.getTime().substring(5, 7));
				}
				// 给前端图表需要的数组赋值
				if (tts.getTime().contains(monthArr[i])) {
					quality.add(tts.getQualified());
					noquality.add(tts.getNoQualified());
					standard.add(tts.getStandard());
					noStandard.add(tts.getNoStandard());
					stationOne.add(tts.getStationOne());
					stationTwo.add(tts.getStationTwo());
					stationThree.add(tts.getStationThree());
					lineOne.add(tts.getLineOne());
					lineTwo.add(tts.getLineTwo());
					fire.add(String.valueOf(Integer.valueOf(tts.getFireOne()) + Integer.valueOf(tts.getFireTwo())));
					liveWorking.add(tts.getLiveWorking());
					lowVoltage.add(tts.getLowVoltage());
					urgentRepairs.add(tts.getUrgentRepairs());
					principal.add(tts.getPrincipal());
					constructUnit.add(tts.getConstructUnit());
					break;
				} else {
					// sql统计出来的数据如果没有某一月的就赋值0
					if (j == list.size() - 1) {
						quality.add("0");
						noquality.add("0");
						standard.add("0");
						noStandard.add("0");
						stationOne.add("0");
						stationTwo.add("0");
						stationThree.add("0");
						lineOne.add("0");
						lineTwo.add("0");
						fire.add("0");
						liveWorking.add("0");
						lowVoltage.add("0");
						urgentRepairs.add("0");
						principal.add("0");
						constructUnit.add("0");
					}
				}

			}

		}
		// 数组放入map，返回给前端
		map.put("quality", quality);
		map.put("noquality", noquality);
		map.put("standard", standard);
		map.put("noStandard", noStandard);
		map.put("stationOne", stationOne);
		map.put("stationTwo", stationTwo);
		map.put("stationThree", stationThree);
		map.put("lineOne", lineOne);
		map.put("lineTwo", lineTwo);
		map.put("fire", fire);
		map.put("liveWorking", liveWorking);
		map.put("lowVoltage", lowVoltage);
		map.put("urgentRepairs", urgentRepairs);
		map.put("principal", principal);
		map.put("constructUnit", constructUnit);
		return map;

	}

	// 工作票类型统计（各单位） 工作票延期时间与延期数量统计图表计算
	public Map<String, List<String>> getDelayData(String updateTimeBegin, String updateTimeEnd, String unit) {

		long a1 = System.currentTimeMillis();
		List<AnalysisStatistic> list = ticketTypeStatisticMapper.getDelayData(updateTimeBegin, updateTimeEnd, unit);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LinkedHashMap<String, AnalysisStatistic> tempMap = new LinkedHashMap<String, AnalysisStatistic>();
		long a2 = System.currentTimeMillis();
		System.out.println("查询所消耗时间" + (a2 - a1));

		// 计算延期的工作票需要延期的时间
		for (int i = 0; i < list.size(); i++) {
			AnalysisStatistic as = list.get(i);
			// 判断此工作票是否需要延期
			if (as.getIsDelay().equals("1")) {
				String workEndTime = as.getWorkEndTime();// 工作终结时间
				String planEndTime = as.getPlanEndTime();// 计划结束时间
				try {
					// 计算工作终结时间超出计划结束时间小时数
					Date date1 = df.parse(workEndTime);
					Date date2 = df.parse(planEndTime);
					long time = (date1.getTime() - date2.getTime()) / 1000;// 转换为秒
					long hours = time / 3600; // 相差的小时数
					long min = time % 3600 / 60;// 相差的分钟数，小于60分钟算一小时
					if (min <= 60) {
						hours += 1;
					}
					as.setDelayTime(hours + "");// 重新赋值
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {
				as.setDelayTime("0");// 如果是不需要延期的工作票，则赋值0
			}
			// 设置时间格式为yyyy-MM
			as.setWorkEndTime(as.getWorkEndTime().substring(0, 7));
			String key = as.getWorkEndTime();
			// 相同时间的数据相加
			if (tempMap.containsKey(key)) {
				AnalysisStatistic km = tempMap.get(key);
				String time = km.getDelayTime();
				String isDelay = km.getIsDelay();
				String newTime = String.valueOf(Integer.valueOf(time) + Integer.valueOf(as.getDelayTime()));
				String newIsDelay = String.valueOf(Integer.valueOf(isDelay) + Integer.valueOf(as.getIsDelay()));
				as.setDelayTime(newTime);
				as.setIsDelay(newIsDelay);
			}
			tempMap.put(key, as);
		}
		list.clear();
		list.addAll(tempMap.values());

		List<String> isDelay = new ArrayList<String>();// 工作票延期数量数组
		List<String> delayTime = new ArrayList<String>();// 工作票延期时间数组
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// 把计算出来的数据，转换成前端图表需要的数组结构数据
		// 双循环两个数组，判断统计的数据是否有某一月无数据，如果无数据则赋值0

		for (int i = 0; i < monthArr.length; i++) {

			for (int j = 0; j < list.size(); j++) {
				AnalysisStatistic as = list.get(j);
				// 对时间格式yyyy-MM截取月份
				if (as.getWorkEndTime().length() >= 7) {
					as.setWorkEndTime(as.getWorkEndTime().substring(5, 7));
				}
				// 给前端图表需要的数组赋值
				if (as.getWorkEndTime().contains(monthArr[i])) {
					isDelay.add(as.getIsDelay());
					delayTime.add(as.getDelayTime());
					break;
				} else {
					// sql统计出来的数据如果没有某一月的就赋值0
					if (j == list.size() - 1) {
						isDelay.add("0");
						delayTime.add("0");
					}
				}
			}
		}

		// 数组放入map，返回给前端
		map.put("isDelay", isDelay);
		map.put("delayTime", delayTime);
		return map;
	}

	public Map<String, List<String>> getExtrlDelayData(String updateTimeBegin, String updateTimeEnd) {
		Map<String, List<String>> objMap = new HashMap<String, List<String>>();
		long a1 = System.currentTimeMillis();
		List<Map<String, Object>> list = ticketTypeStatisticMapper.getExtriDelayData(updateTimeBegin, updateTimeEnd);
		long a2 = System.currentTimeMillis();
		System.out.println("查询所消耗时间" + (a2 - a1));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LinkedHashMap<String, Map<String, Object>> tempMap = new LinkedHashMap<String, Map<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> as = list.get(i);
			as.put("total", new Integer(1));
			if (as.get("check_result") != null && as.get("check_result").equals("0")) {
				as.put("checkResultTotal", new Integer(1));
			} else {
				as.put("checkResultTotal", new Integer(0));
			}
			if (as.get("is_delay").equals("1")) {
				String workEndTime = as.get("work_end_time").toString();
				String planEndTime = as.get("plan_end_time").toString();
				try {
					Date date1 = df.parse(workEndTime);
					Date date2 = df.parse(planEndTime);
					long time = (date1.getTime() - date2.getTime()) / 1000;
					long hours = time / 3600;
					long min = time % 3600 / 60;
					if (min <= 60) {
						hours += 1;
					}
					as.put("delayTime", hours + "");
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {
				as.put("delayTime", "0");
			}

			if (as.get("ticket_type") == null) {
				as.put("ticket_type", "66");
			} else if (as.get("ticket_type").equals("32")) {
				as.put("ticket_type", "31");
			}
			String key = as.get("ticket_type").toString();
			if (tempMap.containsKey(key)) {
				Map<String, Object> km = tempMap.get(key);
				String checkResult = km.get("checkResultTotal").toString();
				String time = km.get("delayTime").toString();
				String isDelay = km.get("is_delay").toString();
				String total = km.get("total").toString();
				String newCheckResult = String
						.valueOf(Integer.valueOf(checkResult) + Integer.valueOf(as.get("checkResultTotal").toString()));
				String newTime = String
						.valueOf(Integer.valueOf(time) + Integer.valueOf(as.get("delayTime").toString()));
				String newIsDelay = String
						.valueOf(Integer.valueOf(isDelay) + Integer.valueOf(as.get("is_delay").toString()));
				String newTotal = String
						.valueOf(Integer.valueOf(total) + Integer.valueOf(as.get("total").toString()));
				as.put("delayTime", newTime);
				as.put("is_delay", newIsDelay);
				as.put("checkResultTotal", newCheckResult);
				as.put("total", newTotal);
			}
			tempMap.put(key, as);
		}

		list.clear();
		list.addAll(tempMap.values());

		List<String> totalList = new ArrayList<String>();
		List<String> checkList = new ArrayList<String>();
		List<String> delayList = new ArrayList<String>();
		List<String> delayTimeList = new ArrayList<String>();

		for (int i = 0; i < typeArr.length; i++) {

			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> map = list.get(j);
				if (map.get("ticket_type").equals(typeArr[i])) {

					totalList.add(map.get("total").toString());
					checkList.add(map.get("checkResultTotal").toString());
					delayList.add(map.get("is_delay").toString());
					delayTimeList.add(map.get("delayTime").toString());
					break;
				} else {

					if (j == list.size() - 1) {
						totalList.add("0");
						checkList.add("0");
						delayList.add("0");
						delayTimeList.add("0");
					}
				}

			}
		}

		objMap.put("totalList", totalList);
		objMap.put("checkList", checkList);
		objMap.put("delayList", delayList);
		objMap.put("delayTimeList", delayTimeList);
		return objMap;
	}

	public List<Map<String, Object>> statisticTypeForUnit(String updateTimeBegin, String updateTimeEnd) {
		List<Map<String, Object>> list = ticketTypeStatisticMapper.statisticTypeForUnit(updateTimeBegin, updateTimeEnd);

		return list;
	}

	public List<Map<String, Object>> statisticTypeForAll(String updateTimeBegin, String updateTimeEnd) {

		List<Map<String, Object>> list = ticketTypeStatisticMapper.statisticTypeForAll(updateTimeBegin, updateTimeEnd);

		return list;
	}

	public Map<String, List<String>> statisticPrincipal(String updateTimeBegin, String updateTimeEnd) {

		List<Map<String, Object>> listPrincipal = ticketTypeStatisticMapper.statisticPrincipal(updateTimeBegin,
				updateTimeEnd);
		List<Map<String, Object>> listPermission = ticketTypeStatisticMapper.statisticPermission(updateTimeBegin,
				updateTimeEnd);
		listPrincipal.addAll(listPermission);
		Map<String, Map<String, Object>> tempMap = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < listPrincipal.size(); i++) {
			Map<String, Object> map = listPrincipal.get(i);
			String key = map.get("unitname").toString();
			if (tempMap.containsKey(key)) {
				Map<String, Object> km = tempMap.get(key);
				String constructUnit = km.get("constructunit").toString();
				String principal = km.get("principal").toString();
				String newCon = String
						.valueOf(Integer.valueOf(constructUnit) + Integer.valueOf(map.get("constructunit").toString()));
				String newPri = String
						.valueOf(Integer.valueOf(principal) + Integer.valueOf(map.get("principal").toString()));
				map.put("constructunit", newCon);
				map.put("principal", newPri);

			}
			tempMap.put(key, map);
		}
		listPrincipal.clear();
		listPrincipal.addAll(tempMap.values());

		Map<String, List<String>> objMap = new HashMap<String, List<String>>();
		List<String> mainUnitPriList = new ArrayList<String>();
		List<String> mainUnitConList = new ArrayList<String>();
		for (int i = 0; i < mainUnitArr.length; i++) {

			for (int j = 0; j < listPrincipal.size(); j++) {
				Map<String, Object> map = listPrincipal.get(j);
				// 给前端图表需要的数组赋值
				if (map.get("unitname").toString().contains(mainUnitArr[i])) {
					mainUnitPriList.add(map.get("principal").toString());
					mainUnitConList.add(map.get("constructunit").toString());
					break;
				} else {
					// sql统计出来的数据如果没有某一月的就赋值0
					if (j == listPrincipal.size() - 1) {
						mainUnitPriList.add("0");
						mainUnitConList.add("0");
					}
				}

			}
		}

		List<String> unitPriList = new ArrayList<String>();
		List<String> unitConList = new ArrayList<String>();
		for (int i = 0; i < UnitArr.length; i++) {

			for (int j = 0; j < listPrincipal.size(); j++) {
				Map<String, Object> map = listPrincipal.get(j);
				// 给前端图表需要的数组赋值
				if (map.get("unitname").toString().contains(UnitArr[i])) {
					unitPriList.add(map.get("principal").toString());
					unitConList.add(map.get("constructunit").toString());
					break;
				} else {
					// sql统计出来的数据如果没有某一月的就赋值0
					if (j == listPrincipal.size() - 1) {
						unitPriList.add("0");
						unitConList.add("0");
					}
				}
			}
		}

		List<String> otherUnitPriList = new ArrayList<String>();
		List<String> otherUnitConList = new ArrayList<String>();
		for (int i = 0; i < otherUnitArr.length; i++) {

			for (int j = 0; j < listPrincipal.size(); j++) {
				Map<String, Object> map = listPrincipal.get(j);
				// 给前端图表需要的数组赋值
				if (map.get("unitname").toString().contains(otherUnitArr[i])) {
					otherUnitPriList.add(map.get("principal").toString());
					otherUnitConList.add(map.get("constructunit").toString());
					break;
				} else {
					// sql统计出来的数据如果没有某一月的就赋值0
					if (j == listPrincipal.size() - 1) {
						otherUnitPriList.add("0");
						otherUnitConList.add("0");
					}
				}
			}
		}
		objMap.put("mainUnitPriList", mainUnitPriList);
		objMap.put("mainUnitConList", mainUnitConList);
		objMap.put("unitPriList", unitPriList);
		objMap.put("unitConList", unitConList);
		objMap.put("otherUnitPriList", otherUnitPriList);
		objMap.put("otherUnitConList", otherUnitConList);
		return objMap;
	}

	public Map<String, List<String>> statictisState(String updateTimeBegin, String updateTimeEnd) {

		List<Map<String, Object>> list = ticketTypeStatisticMapper.statictisState(updateTimeBegin, updateTimeEnd);
		List<String> stateList = new ArrayList<String>();
		for (int i = 0; i < stateArr.length; i++) {

			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> map = list.get(j);
				String name = map.get("work_state") == null ? "" : map.get("work_state").toString();
				if (name.equals(stateArr[i])) {
					stateList.add(map.get("num").toString());
					break;
				} else {
					if (j == list.size() - 1) {
						stateList.add("0");
					}
				}
			}
		}
		Map<String, List<String>> objMap = new HashMap<String, List<String>>();
		objMap.put("stateList", stateList);
		return objMap;
	}

	public List<AnalysisStatistic> selectDetailDialog(String flag, String staFlag, String ticketType, String fullPath,
		String delayFlag, String time, String monthEnd,String unit) {
		
		if(monthEnd.length()==1) {
			monthEnd = "0"+monthEnd;
		}
		String endMonth = time + "-" + monthEnd;
		List<AnalysisStatistic> list = ticketTypeStatisticMapper.selectDetailDialog(flag, staFlag, ticketType, fullPath,delayFlag, endMonth,unit);
		return list;

	}

	public List<AnalysisStatistic> selectDetailDialogAllUnit(String index,String name,String isExtrl,String ticketType,String monthBegin,String monthEnd,String lastFlag) {
		String typeFlag = "";//图表X轴维度
		String unit = "";//单位
	    String flag = "1";//判断使用哪个sql
	    String isDelay = "";//判断点击为标注工作票的时，是否延期
	    //确定点击图表的维度
		if(name.equals("工作票数量")) {
			typeFlag = "0";
		}else if(name.equals("合格数量")) {
			typeFlag = "1";
		}else if(name.equals("延期数量")||name.equals("延期时间")) {
			typeFlag = "2";
		}else if(name.equals("局内负责人")) {
			typeFlag = "";
		}else if(name.equals("施工单位负责人")) {
			flag = "2";
		}else {
			typeFlag = "0";
			switch (name) {
			case "厂站第一种工作票":
				ticketType = "11";
				break;
			case "厂站第二种工作票":
				ticketType = "12";
				break;
			case "厂站第三种工作票":
				ticketType = "13";
				break;
			case "线路第一种工作票":
				ticketType = "21";
				break;
			case "线路第二种工作票":
				ticketType = "22";
				break;
			case "动火工作票":
				ticketType = "31";
				break;
			case "带电作业工作票":
				ticketType = "43";
				break;
			case "低压配电网工作票":
				ticketType = "44";
				break;
			case "紧急抢修工作票":
				ticketType = "51";
				break;
			case "调度检修单":
				ticketType = "66";
				break;
			case "未标注工作票":
				ticketType = "88";
				typeFlag = "8";
				break;
			default:
				break;
			}
		}
		if(isExtrl.equals("施工单位负责人")){
			isExtrl = "2";
		}else if(isExtrl.equals("局内负责人")) {
			isExtrl = "8";
		}
		//0-主网,1-配网,2-外协,3-其他单位
		if(isExtrl.equals("0")) {
			unit =mainUnitArr[Integer.valueOf(index)];
		}else if(isExtrl.equals("1")) {
			unit =UnitArr[Integer.valueOf(index)];
			unit +="供电局";
		}else if(isExtrl.equals("3")) {
			unit =otherUnitArr[Integer.valueOf(index)];
		}

		if(ticketType==null||ticketType.equals("")) {
			ticketType=typeArr[Integer.valueOf(index)];
			
		}
		if(ticketType.equals("66")) {
			typeFlag = "8";
		}
		if((name.equals("延期数量")||name.equals("延期时间"))&&typeFlag.equals("8")) {
			isDelay = "1";
		}
		List<AnalysisStatistic> list = new ArrayList<AnalysisStatistic>();
		if(lastFlag.equals("1")) {
			String stateType = stateArr[Integer.valueOf(index)];
			list = ticketTypeStatisticMapper.selectStateDialog(stateType,monthEnd, monthBegin);
			return list;
		}
		if(flag.equals("1")) {
			list = ticketTypeStatisticMapper.selectAllTypeDialog(typeFlag, isExtrl, unit, ticketType,monthEnd, monthBegin,isDelay);
		}else if(flag.equals("2")) {
			list = ticketTypeStatisticMapper.selectAllTypePermissionDialog(unit,monthEnd, monthBegin);
		}

		return list;

	}
	
	/**
	 * 工作票状态中间表入库
	 */
	public void insertStateStatistic(String timeBegin, String timeEnd) {
		ticketTypeStatisticMapper.insertStateStatistic(timeBegin, timeEnd);
	}
}
