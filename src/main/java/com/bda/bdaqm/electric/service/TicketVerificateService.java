package com.bda.bdaqm.electric.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.TicketCheckMapper;
import com.bda.bdaqm.electric.model.Criterion15Vo;
import com.bda.bdaqm.electric.model.CriterionChildrenResult;
import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheck;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.common.service.AbstractService;

//两票校验service
@Service
public class TicketVerificateService extends AbstractService<TicketCheck> {
	@Autowired
	private TicketCheckMapper ticketCheckMapper;

	public List<Map<String,Object>> getTicketsData(QueryParams ticket) {
		return ticketCheckMapper.getTicketsData(ticket);
	}
	/*public List<Map<String,Object>> getTicketsDataIsNull(QueryParams ticket) {
		return ticketCheckMapper.getTicketsDataIsNull(ticket);
	}*/
	public List<Map<String,Object>> getTicketInfo(List<String> ids) {
		return ticketCheckMapper.getTicketInfo(ids);
	}
	
	//根据base表里的单位id去apply表里查找数据，查看是否有停电关联计划
	public Map<String,Map<String,Object>> getIsConnect(List<String> oids){
		return ticketCheckMapper.getIsConnect(oids);
	}
	
	public List<String> getQuartzTicketsData(String beginTime,String endTime){
		return ticketCheckMapper.getQuartzTicketsData(beginTime,endTime);
	}
	
	public List<String> getUser() {
		return ticketCheckMapper.getUser();
	}

	public List<Map<String,String>> getUserType() {
		return ticketCheckMapper.getUserType();
	}
	
	public void deleteResult (List<TicketCheckResult> resultList) {
		ticketCheckMapper.deleteResult(resultList);
	}
	
	public void insertResult (List<TicketCheckResult> resultList) {
		ticketCheckMapper.insertResult(resultList);
	}
	public List<String> getLocation(){
		return ticketCheckMapper.getLocation();
	}
	
	public void deleteResultChildren (List<CriterionChildrenResult> resultList) {
		ticketCheckMapper.deleteResultChildren(resultList);
	}
	
	public void insertResultChildren (List<CriterionChildrenResult> resultList) {
		ticketCheckMapper.insertResultChildren(resultList);
	}
	public List<Criterion15Vo> selectBySql(List<String> list){
		return ticketCheckMapper.selectBySql(list);
	}
	public int saveBatch(String batchTime, String userId, int count) {
		return ticketCheckMapper.saveBatch(batchTime, userId, count);
	}
	public String queryMax() {
		return ticketCheckMapper.queryMax();
	}
	// 固定kV
	public String toUpper(String str) {
		str = str.toUpperCase();
		String newStr = str.replace("KV", "kV");
		return newStr;
	}

	// 规则化
	public String[] standard(String breaker, String pathString) {
//		JSONObject js = (JSONObject) JSONObject.parse(pathString);
		String[] strList = null;
		
		breaker = breaker.replaceAll("\\p{P}", ",");
//		for (Entry<String, Object> entry : js.entrySet()) {
//			Pattern pattern = Pattern.compile(entry.getKey());
//			Matcher matcher = pattern.matcher(breaker);
//			while (matcher.find()) {// 数字结尾
//				if (matcher.group() != null && !matcher.group().equals("")) {
//					strList = getList(matcher.group(), breaker);
//				}
//			}
//		}
		strList = breaker.split(",");
		return strList;

	}

	// 规则化
	public String[] getList(String str, String breaker) {
		String[] strList = null;
		String newStr = "";
		if (str.equals("+") || str.equals("|") || str.equals("*") || str.equals("\\")) {
			newStr = "\\" + str;
			strList = breaker.split(newStr);
		} else {
			strList = breaker.split(str);
		}
		return strList;
	}
	//获取子符串的个数
	public int getStrCount(String atempSt,String str) {
        int index = 0;
        int count = 0;
        while((index = atempSt.indexOf(str, index)) != -1) {
            index += str.length();
            count++;
        }
        return count;
	}
	
	//格式化时间
	public String formateTime(String str) {
		String[] newList = str.split(" ");
		String[] strList = newList[0].split("-");
		if(strList[1].length()<2) {
			strList[1] = "0"+strList[1];
		}
		if(strList[2].length()<2) {
			strList[2] = "0"+strList[1];
		}
		str = strList[1]+"-"+strList[2]+" "+newList[1];
		return str;
	}
	
	/**
	 * 更换数据库后所用到的
	 */
	
	// 17种规则校验 1 是不合格,0是合格,2是异常
	// 第一种
	public List<Map<String,String>> one(Map<String,Object> ticketCheck,Map<String,Map<String,Object>>existMap) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		int flag = 0;
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("ticket_type")==null) {
			return list;
		}
		
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		int flagNew = 0;
//		if(ticketType.equals("11")||ticketType.equals("21")) {
//			Map<String,String> map0=new HashMap<>();
//			if(existMap.containsKey(toStr(ticketCheck.get("id")))) {
//				flagNew = 0;			
//			}else {
//				flagNew=2;
//				map0.put("ticket_id", toStr(ticketCheck.get("id")));
//				map0.put("criterion", "合格判据1");
//				map0.put("criterion_children", "H1_sub1");
//				map0.put("description", "不满足合格判据1：搜索不到关联停电计划");
//				map0.put("field", "department_oid");
//				list.add(map0);
//			}
//		}

		if (ticketType.equals("11")) {
			if(ticketCheck.get("function_location_name")==null||ticketCheck.get("work_task")==null||ticketCheck.get("work_place")==null) {
				flag=0;
			}else {
				String station = toUpper(toStr(ticketCheck.get("function_location_name")));
				String workTask = toUpper(toStr(ticketCheck.get("work_task")));
				String workPlace = toUpper(toStr(ticketCheck.get("work_place")));
				Map<String,String> map2 = new HashMap<String,String>();
				boolean checkFlag = false;
				boolean checkFlag01 = false;
				boolean checkFlag02 = false;
				checkFlag = station.contains("110kV")||station.contains("220kV")||station.contains("500kV")||station.contains("35kV")||station.contains("10kV")||station.contains("0.4kV");
				checkFlag01 = workTask.contains("110kV")||workTask.contains("220kV")||workTask.contains("500kV")||workTask.contains("35kV")||workTask.contains("10kV")||workTask.contains("0.4kV");
				checkFlag02 = workPlace.contains("110kV")||workPlace.contains("220kV")||workPlace.contains("500kV")||workPlace.contains("35kV")||workPlace.contains("10kV")||workPlace.contains("0.4kV");
				if (checkFlag||checkFlag01||checkFlag02) {
					flag = 0;
				} else {
					flag = 2;
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "合格判据1");
					map2.put("criterion_children", "H1_sub2");
					map2.put("description", "不满足合格判据1：票面全部信息中搜索不到“110kV/220kV/500kV/35kV/10kV/0.4kV”关键字;");
					map2.put("field", "work_task@function_location_name@work_place");
					list.add(map2);
				}
			}
			
			if(flagNew!=0||flag!=0) {
				flag = 2;
			}
		} else if (ticketType.equals("21")) {
			
			if(flagNew!=0) {
				flag=2;
			}
			
			if(ticketCheck.get("function_location_name")==null||ticketCheck.get("work_task")==null||ticketCheck.get("work_place")==null) {
				 flag=0;
			}else {
				String station = toUpper(toStr(ticketCheck.get("function_location_name")));
				String workTask = toUpper(toStr(ticketCheck.get("work_task")));
				String workPlace = toUpper(toStr(ticketCheck.get("work_place")));
				boolean checkFlag = false;
				boolean checkFlag01 = false;
				boolean checkFlag02 = false;
				int flag01 = 0;
				int flag02 = 0;
				Map<String,String> map1 = new HashMap<String,String>();
				Map<String,String> map2 = new HashMap<String,String>();
				checkFlag = station.contains("110kV")||station.contains("220kV")||station.contains("500kV")||station.contains("35kV")||station.contains("10kV")||station.contains("0.4kV");
				checkFlag01 = workTask.contains("110kV")||workTask.contains("220kV")||workTask.contains("500kV")||workTask.contains("35kV")||workTask.contains("10kV")||workTask.contains("0.4kV");
				checkFlag02 = workPlace.contains("110kV")||workPlace.contains("220kV")||workPlace.contains("500kV")||workPlace.contains("35kV")||workPlace.contains("10kV")||workPlace.contains("0.4kV");
				if (checkFlag||checkFlag01||checkFlag02) {
					flag01 = 0;
				} else {
					flag01 = 2;
					map1.put("ticket_id",toStr(ticketCheck.get("id")));
					map1.put("criterion", "合格判据1");
					map1.put("criterion_children", "H2_sub4");
					map1.put("description", "不满足合格判据1：票面全部信息中搜索不到“110kV/220kV/500kV/35kV/10kV/0.4kV”关键字;");
					map1.put("field", "work_task@function_location_name@work_place");
					list.add(map1);
				}
				//int t = station.indexOf("110kV")==-1?station.indexOf("220kV")==-1?station.indexOf("500kV")==-1?station.indexOf("35kV")==-1?station.indexOf("0.4kV")==-1?-1:station.indexOf("0.4kV"):station.indexOf("35kV"):station.indexOf("500kV"):station.indexOf("220kV"):station.indexOf("110kV");
				String regex="^\\S{1,}(\\d{1,}|[0-9]+\\.[0-9]{1,})kV\\S{1,}线";
				String regex2 = "^\\S{1,}(\\d{1,}|[0-9]+\\.[0-9]{1,})kV\\S{1,}[a-zA-Z][0-9]{1,2}\\S{1,}";
				String regex3 = "部分停电|全部停电|全停";
				
				Pattern pattern=Pattern.compile(regex);
				Matcher matcher=pattern.matcher(station);
				
				
				if(matcher.find()) {
					flag02 = 0;
				}else {
					String insulationStr = station.replaceAll("[\\p{P}&&[^#＃（）()]]", ",");
					String[] str = insulationStr.split(",");
					boolean a = true;
					for(String s:str){
						if(StringUtils.isNotBlank(s)){
							Pattern pattern2=Pattern.compile(regex2);
							Matcher matcher2=pattern2.matcher(s);
							
							Pattern pattern3=Pattern.compile(regex3);
							Matcher matcher3=pattern3.matcher(s);
							if(!(matcher2.find() && matcher3.find())){
								a = false;
							}
						}
					}
					if(a){
						flag02=0;
					}else{
						flag02 = 1;
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "合格判据1");
						map2.put("criterion_children", "H1_sub5");
						map2.put("description", "不满足合格判据1：停电线路名称信息没有“110kV/220kV/500kV/35kV/10kV/0.4kV****线”关键字;");
						map2.put("field", "function_location_name");
						list.add(map2);
					}
				}
				if(flag02!=0) {
					flag = 1;
				}else {
					flag = flag01;
				}
				

				
			}
		} else if (ticketType.equals("紧急抢修工作票")) {
			Map<String,String> map2 = new HashMap<String,String>();
			String perTime = toStr(ticketCheck.get("permission_time"));
			String endTime = toStr(ticketCheck.get("work_end_time"));
			if(perTime==null||perTime.equals("")||endTime==null||endTime.equals("")) {
				flag = 0;
			}else {
				perTime = formateTime(perTime);
				endTime = formateTime(endTime);
				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				try {
					Date date1 = df.parse(perTime);
					Date date2 = df.parse(endTime);
					long time = date2.getTime() - date1.getTime();
					long hours = (time - (time / (1000 * 60 * 60 * 24)) * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
					if (hours > 12) {
						flag = 1;
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "合格判据1");
						map2.put("criterion_children", "H1_sub6");
						map2.put("description", "不满足合格判据1：工作许可时间到工作终结时间超过12小时;");
						map2.put("field", "permission_time@work_end_time");
						list.add(map2);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} 
//		else if (ticketType.equals("低压配网工作票")) {
//			String isOuterDept = ticketCheck.getIsOuterDept();
//			if (isOuterDept.equals("是")) {
//				flag = 1;
//			}
//		} 
		else {
			flag = 0;
		}
		map.put("result", String.valueOf(flag));
		return list;
	}

	// 第二种
	public List<Map<String,String>> two(Map<String,Object> ticketCheck,Map<String,Map<String,Object>>existMap) {
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		int flag = 0;
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
//		Map<String,String> map0=new HashMap<>();
//		String ticketType = toStr(ticketCheck.get("ticket_type"));
//		if(ticketType.equals("11")||ticketType.equals("21")) {
//			//判断是否能关联到停电计划
//			if(existMap.containsKey(toStr(ticketCheck.get("id")))) {
//				String planEndTime = toStr(ticketCheck.get("plan_end_time"));
//				String applyEndDate = toStr(existMap.get(ticketCheck.get("id")).get("confirm_work_end_date"));
//				//判断计划结束时间与停电时间是否为空
//				if(planEndTime.equals("")||applyEndDate.equals("")) {
//					flag = 0;
//				}else {
//					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					try {
//						long planTime = sim.parse(planEndTime).getTime();
//						long applyEndTime = sim.parse(applyEndDate).getTime();
//							
//						if(planTime>applyEndTime) {
//							flag=1;
//							map0.put("ticket_id", toStr(ticketCheck.get("id")));
//							map0.put("criterion", "合格判据2");
//							map0.put("criterion_children", "H2_sub1");
//							map0.put("description", "不满足合格判据2：计划工作时间超过停电计划时间");
//							map0.put("field", "plan_end_time");
//							list.add(map0);
//						}
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//				}
//			}else {
//				Map<String,String> map1=new HashMap<>();
//				flag=1;
//				map1.put("ticket_id", toStr(ticketCheck.get("id")));
//				map1.put("criterion", "合格判据2");
//				map1.put("criterion_children", "H2_sub1");
//				map1.put("description", "不满足合格判据2：因搜索不到关联停电计划，所以不满足此判据");
//				map1.put("field", "plan_end_time");
//				list.add(map1);
//			}
//		}

		map.put("result", String.valueOf(flag));
		return list;
	}

	// 第三种
	public List<Map<String,String>> three(Map<String,Object> ticketCheck,String path) {
		int flag = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("ticket_type")==null) {
			return list;
		}
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		
		if (ticketType.equals("11")){
			if(ticketCheck.get("work_task")==null) {
				flag =0;
			}else {
				String workTask = toUpper(toStr(ticketCheck.get("work_task")));
				if (workTask.contains("主变")&&!workTask.contains("开关")) {
					if(ticketCheck.get("pull_breaker")==null||toStr(ticketCheck.get("pull_breaker")).equals("")) {
						flag=1; 
					}else {
						String[] strList = standard(toStr(ticketCheck.get("pull_breaker")),path);
						if(strList==null||strList.length<2) {
							flag=1;
						}
					}

				}
				if(flag==1) {
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "合格判据3");
					map2.put("criterion_children", "H3_sub1");
					map2.put("description", "不满足合格判据3：工作要求安全措施栏应拉开的断路器（开关）少于2个;");
					map2.put("field", "work_task@pull_breaker");
					list.add(map2);
				}
			}
			int flag01 = 0;
			if(ticketCheck.get("dcpower_lowp_circle")==null) {
				flag01=0;
			}else {
				String dc = toStr(ticketCheck.get("dcpower_lowp_circle"));
				boolean checkFlag = false;
				boolean checkFlag01 = false;
				boolean checkFlag02 = false;
				checkFlag = dc.contains("压板");
				checkFlag01 = dc.contains("连接片");
				checkFlag02 = dc.contains("熔断器");
				if(checkFlag||checkFlag01||checkFlag02) {
					checkFlag = dc.contains("投上");
					checkFlag01 = dc.contains("取下");
					checkFlag02 = dc.contains("短接");
					if(checkFlag||checkFlag01||checkFlag02) {
						flag01 =  0;
					}else {
						flag01 = 1;
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "合格判据3");
						map2.put("criterion_children", "H3_sub2");
						map2.put("description", "不满足合格判据3：应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路栏有“压板、连接片、熔断器”字眼而无投上、取下、短接字眼;");
						map2.put("field", "dcpower_lowp_circle");
						list.add(map2);
					}
				}
			}
			if(flag01==1||flag==1) {
				flag=1;
			}else {
				flag=0;
			}
			
		} else if (ticketType.equals("12")) {
			if(ticketCheck.get("dcpower_lowp_circle")==null) {
				flag = 0;
			}else {
				String dc = toStr(ticketCheck.get("dcpower_lowp_circle"));
				boolean checkFlag = false;
				boolean checkFlag01 = false;
				boolean checkFlag02 = false;
				checkFlag = dc.contains("压板");
				checkFlag01 = dc.contains("连接片");
				checkFlag02 = dc.contains("熔断器");
				if(checkFlag||checkFlag01||checkFlag02) {
					checkFlag = dc.contains("投上");
					checkFlag01 = dc.contains("取下");
					checkFlag02 = dc.contains("短接");
					if(checkFlag||checkFlag01||checkFlag02) {
						flag =  0;
					}else {
						flag = 1;
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "合格判据3");
						map2.put("criterion_children", "H3_sub2");
						map2.put("description", "不满足合格判据3：应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路栏有“压板、连接片、熔断器”字眼而无投上、取下、短接字眼;");
						map2.put("field", "dcpower_lowp_circle");
						list.add(map2);
					}
				}
			}

		}else if(ticketType.equals("规范性书面记录")||ticketType.equals("紧急抢修工作票")) {
			flag=0;
		}else {
			flag=0;
		}
//		Pattern pattern = Pattern.compile("[0-9]*");
//		String ishandleSecond = ticketCheck.getIsHandleSecondMeasures();
//		if(ishandleSecond!=null&&!ishandleSecond.equals("否")) {
//			if(!pattern.matcher(personCount).matches()&&!pattern.matcher(twiceCount).matches()) {
//				flag=1;
//			}
//		}else {
//			if(!pattern.matcher(personCount).matches()) {
//				flag=1;
//			}
//		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	
	//第四种
	public int four(Map<String,Object> ticketCheck) {
		int flag = 0;
		return flag;
	}
	
	//第五种
	public int five(Map<String,Object> ticketCheck) {
		int flag = 0;
		return flag;
	}
	//第六种
	public List<Map<String,String>> six(Map<String,Object> ticketCheck,Map<String,Map<String,Object>>existMap) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		int flag = 0;
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		String workTask=toUpper(toStr(ticketCheck.get("work_task")));
		String station=toUpper(toStr(ticketCheck.get("function_location_name")));
		station=station.replaceAll("[\\p{P}&&[^#＃（）()]]", ",");
		int outageFlag = 0;
		workTask=workTask.replaceAll("[\\p{P}&&[^#＃（）()]]", ";");
		//station.replace('\\',' ');
//		Map<String,String> map0=new HashMap<>();
//		String ticketType = toStr(ticketCheck.get("ticket_type"));
//		if(ticketType.equals("21")) {
//			if(existMap.containsKey(toStr(ticketCheck.get("department_oid")))) {
//				
//				String planEndTime = toStr(ticketCheck.get("plan_end_time"));
//				String applyEndDate = toStr(existMap.get(ticketCheck.get("department_oid")).get("apply_work_end_date"));
//				
//				if(planEndTime.equals("")||applyEndDate.equals("")) {
//					flag=0;
//				}else {
//					if(!planEndTime.equals(applyEndDate)) {
//						Map<String,String> map1=new HashMap<>();
//						flag=1;
//						map1.put("ticket_id", toStr(ticketCheck.get("id")));
//						map1.put("criterion", "合格判据2");
//						map1.put("criterion_children", "H2_sub1");
//						map1.put("description", "不满足合格判据6：因搜索不到关联停电计划，所以不满足此判据");
//						map1.put("field", "function_location_name");
//						list.add(map1);
//					}else {
//						String deviceName = toStr(existMap.get(ticketCheck.get("department_oid")).get("apply_work_end_date"));
//						String locationName = toStr(ticketCheck.get("function_location_name"));
//						
//						boolean isRight = deviceName.contains(locationName);
//						boolean isLeft = locationName.contains(deviceName);
//						if(isRight||isLeft) {
//							flag = 0;
//						}else {
//							flag=1;
//							map0.put("ticket_id", toStr(ticketCheck.get("id")));
//							map0.put("criterion", "合格判据6");
//							map0.put("criterion_children", "H6_sub1");
//							map0.put("description", "不满足合格判据6：停电线路与停电计划中的停电设备名称无重叠部分");
//							map0.put("field", "function_location_name");
//							list.add(map0);
//						}
//					}
//				}
//			}else {
//				Map<String,String> map1=new HashMap<>();
//				flag=2;
//				map1.put("ticket_id", toStr(ticketCheck.get("id")));
//				map1.put("criterion", "合格判据6");
//				map1.put("criterion_children", "H6_sub1");
//				map1.put("description", "不满足合格判据6：因搜索不到关联停电计划，所以不满足此判据");
//				map1.put("field", "function_location_name");
//				list.add(map1);
//			}
//		}
		
		if(ticketType.equals("11")||ticketType.equals("12")) {
		   	//保留，待以后开发
			//return list;
		}else if(ticketType.equals("21")||ticketType.equals("22")) {
			if(ticketType.equals("21")) {
				if(workTask!=null) {
					String[] mission=workTask.split(";");
					int count=0;
					for(String m:mission) {
						String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}";
						Pattern pattern=Pattern.compile(regex);
						Matcher matcher=pattern.matcher(m);
						if(matcher.find()) {
							if(!m.contains("线")) {
								regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}[a-zA-Z]{1,}[0-9]{1,}";
								pattern=Pattern.compile(regex);
								Matcher newMatcher=pattern.matcher(m);
								if(newMatcher.find()) {
									boolean isPass=false;
									String[] lineName=station.split(",");
									for(String str:lineName) {
										int index=str.indexOf('(')!=-1?str.indexOf('('):str.indexOf('（');
										if(index>0) {
											String temp=str.substring(0,index).trim();
											if(m.contains(temp)) {
												if(str.contains("全部停电")||str.contains("部分停电")||str.contains("全停")) {
													count++;
													isPass=true;
													break;
											}
												
											}
										}
										
									}
									if(!isPass) {
										flag=1;
										break;
									}
								}
								
							}else {
								count++;
								regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)([^（）()])[\u4e00-\u9fa5]{1,}线";
								pattern=Pattern.compile(regex);
								Matcher newMatcher=pattern.matcher(m);
								boolean isPass=false;
								if(!newMatcher.find()) {
									String[] lineName=station.split(",");
									for(String str:lineName) {
										int index=str.indexOf('(')>-1?str.indexOf('('):str.indexOf('（');
										if(index>0) {
											String temp=str.substring(0,index).trim();
											if(m.contains(temp)) {
												if(str.contains("全部停电")||str.contains("部分停电")||str.contains("全停")) {
													isPass=true;
													break;
												}
											}
											
										}
										
										}
									if(!isPass) {
										count--;
										flag=1;
										break;
									}
								}
							}
						}
					}
					if(count==0) {
						flag=1;
					}
					
					
				}
//				if(existMap.containsKey(toStr(ticketCheck.get("id")))) {
//					String deviceName = toStr(existMap.get(ticketCheck.get("id")).get("device_name"));
//					String locationName = toStr(ticketCheck.get("function_location_name"));
//					locationName = locationName.replaceAll("[\\p{P}&&[^#＃()（）]]", ",");
//					String [] listDevice = locationName.split(",");
//					listDevice = removeEmptyForFunction(listDevice);
//					for(int i=0;i<listDevice.length;i++) {
//						if(deviceName.contains(listDevice[i])) {
//							outageFlag = 0;
//						}else {
//							outageFlag = 1;
//						}
//					}
//					
//					if(outageFlag==1) {
//						Map<String,String> map0=new HashMap<>();
//						map0.put("ticket_id", toStr(ticketCheck.get("id")));
//						map0.put("criterion", "合格判据6");
//						map0.put("criterion_children", "H6_sub1");
//						map0.put("description", "不满足合格判据6：停电线路与停电计划中的停电设备名称无重叠部分");
//						map0.put("field", "function_location_name");
//						list.add(map0);	
//					}
//				
//				}else {
//					Map<String,String> map1=new HashMap<>();
//					flag=2;
//					map1.put("ticket_id", toStr(ticketCheck.get("id")));
//					map1.put("criterion", "合格判据6");
//					map1.put("criterion_children", "H6_sub1");
//					map1.put("description", "不满足合格判据6：因搜索不到关联停电计划，所以不满足此判据");
//					map1.put("field", "function_location_name");
//					list.add(map1);
//				}
				
				
			}else {
				String lregex="(\\d{1,}|0.\\d{1,})kV\\s{0,}[a-zA-Z0-9\u4e00-\u9fa5]{1,}\\W{0,}";
				String[] mission=workTask.split(";");
				int count=0;
				for(String m:mission) {
				   String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}";
				   Pattern pattern=Pattern.compile(regex);
					Matcher matcher=pattern.matcher(m);
					if(matcher.find()) {
						String fregex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}线";
						pattern=Pattern.compile(fregex);
						Matcher fMatcher=pattern.matcher(m);
						if(fMatcher.find()) {
							count++;
						}else {
							regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}[a-zA-Z]{1,}\\d+";
							 pattern=Pattern.compile(regex);
							Matcher newMatcher=pattern.matcher(m);
							if(newMatcher.find()&&!m.contains("线")) {
								flag=1;
								break;
							}
						}
						
						
					}
				}
				if(count==0) {
					flag=1;
				}
				
				
			}
			
		}
		if(flag==1) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "合格判据6");
			map2.put("criterion_children", "H6_sub1");
			map2.put("description", "不满足合格判据6：工作任务不明确，有错漏判定;");
			map2.put("field", "work_task");
			list.add(map2);
		}
		//由于开关也算是断路器的一种，当时开关房和开关柜却是另一种设备，因此不能单纯的通过正则表达式匹配开关
		//由于暂时只发现开关房开关柜两个带开关字眼的设备，因此只将这两个加入数组中，这个数组用来过滤出真正的开关
		//考虑到一个工作地段中可能包含开关房开关柜和真正的开关(真正的开关的形式为开关位置，开关检修等)在一起的情况，而分组的分隔符又不固定
		//因此用以下方法：
		//将工作地段用正则表达式匹配，将所有带有开关的字符串匹配出来，再通过过滤查看是否含有真正开关的字符串
		//若存在有真正的开关，我们再用正则表达式匹配，看是否符合规则
		//此时我们用来匹配的还是工作地段，因此若是诸如开关房符合这样的规则，并不是我们想要的结果
		//我们还需要再过滤一次，若最后剩下真正的开关，才说该票是符合该判据的
		//值得注意的一点是，若存在两个或多个真正开关，若其中有不匹配的同时也有匹配的，按照上述情况我们并不能判断出来
		//因此我们可以用计数的方法，计算含有真正开关的数量，再去匹配，查看匹配符合规则的数量是否和前面的计数相等
		
		int flag03=0;
		String workPlace=toUpper(toStr(ticketCheck.get("work_place")));
		//String station = toUpper(toStr(ticketCheck.get("function_location_name")));
		String[] noInclude= {"开关房","开关柜","开关箱","开关室"};
		if(workPlace.contains("断路器")||workPlace.contains("开关")) {
		  if(workPlace.contains("开关")) {
			  int count=0;
				String kregx="开关[\u4e00-\u9fa5]{0,}";
				
				boolean isIn=false,isOther=false;
				Pattern pattern=Pattern.compile(kregx);
				Matcher matcher=pattern.matcher(workPlace);
				while(matcher.find()) {
					String match=matcher.group();
					for(String s:noInclude) {
						if(!match.contains(s)) {
							isIn=true;
						}else {
							isIn=false;
							break;
						}
					}
					if(isIn) {
						count++;
					}
					
				}
				
				
				if(count>0) {
					int realCount=0;
					String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*?)[0-9]{3,}([\\s\\S]*?)开关[\u4e00-\u9fa5]{0,}";
					String stationRegex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{2,}站";
					pattern=Pattern.compile(stationRegex);
					matcher=pattern.matcher(station);
					if(matcher.find()) {
						regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*?)[0-9]{3,}\\s{0,}[(（][a-zA-Z0-9]{1,}[)）]([\\s\\S]*?)开关[\u4e00-\u9fa5]{0,}";
						
					}
					pattern=Pattern.compile(regex);
					matcher=pattern.matcher(workPlace);
				
						while(matcher.find()) {
							String match=matcher.group();
							for(String s:noInclude) {
								if(!match.contains(s)) {
									isOther=false;
								}else {
									isOther=true;
									break;
								}
							}
							if(!isOther) {
								
								realCount++;
							}
						}
						if(count!=realCount) {
							flag03=1;
						}
					
				}
				
		  }
		  if(workPlace.contains("断路器")){
			  String dregex="断路器";
			  Pattern pattern=Pattern.compile(dregex);
			  Matcher matcher=pattern.matcher(workPlace);
			  int count=0;
			  while(matcher.find()) {
				  count++;
			  }
			  String stationRegex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{2,}站";
			  String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*?)[0-9]{3,}([\\s\\S]*?)断路器";
			  pattern=Pattern.compile(stationRegex);
				matcher=pattern.matcher(station);
				if(matcher.find()) {
					regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*?)[0-9]{3,}\\s{0,}[(（][a-zA-Z0-9]{1,}[)）]([\\s\\S]*?)断路器";
					
				}
			  
			  pattern=Pattern.compile(regex);
			  matcher=pattern.matcher(workPlace);
			  int realCount=0;
			  
				  while(matcher.find()) {
					  realCount++;
				  }
			  
			  if(realCount!=count) {
				  flag03=1;
			  }
		  }
			
			
		}
		if(workPlace.contains("刀闸")||workPlace.contains("接地刀闸")) {
			String dregex="刀闸";
			int count=0;
			Pattern pattern=Pattern.compile(dregex);
			Matcher matcher=pattern.matcher(workPlace);
			while(matcher.find()) {
				count++;
			}
			int realCount=0;
			String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[0-9]{3,}[\u4e00-\u9fa5]{0,}([\\s\\S]*)刀闸";
			pattern=Pattern.compile(regex);
			matcher=pattern.matcher(workPlace);
			
				while(matcher.find()) {
					realCount++;
				}
			
			if(realCount!=count) {
				flag03=1;
			}
		}
		if(workPlace.contains("母线")) {
			String mregex="母线";
			Pattern pattern=Pattern.compile(mregex);
			Matcher matcher=pattern.matcher(workPlace);
			int count=0;
			while(matcher.find()) {
				count++;
			}
			int realCount=0;
			String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]{1,})母线";
			pattern=Pattern.compile(regex);
			matcher=pattern.matcher(workPlace);
			
				while(matcher.find()) {
					realCount++;
				}
			
			if(realCount!=count) {
				flag03=1;
			}
		}
		//检验线路是否有电压等级和名称
		String[] lineName=station.split(",");
		List<String> realLineList=new ArrayList<>();
		for(String s:lineName) {
			if(s.contains("全部停电")||s.contains("部分停电")||s.contains("全停")) {
				realLineList.add(s);
			}
		}
		if(realLineList.size()>0||station.contains("线")) {
			workPlace=workPlace.replace("[\\p{P}&&[^#＃（）()]]", ";");
			workPlace=workPlace.replace("；", ";");
			workPlace=workPlace.replace("。", ";");
			String[] message=workPlace.split(";");
			int count=0;
			for(String single:message) {
				
				boolean isInclude=false;
				String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]";
				Pattern pattern=Pattern.compile(regex);
				Matcher matcher=pattern.matcher(single);
				if(matcher.find()) {
					
		
					if(!single.contains("线")) {
						regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{1,}[a-zA-Z]{1,}[0-9]{1,}";
						pattern=Pattern.compile(regex);
						Matcher newMatcher=pattern.matcher(single);
						if(newMatcher.find()) {
							for(String str:realLineList) {
								int index=str.indexOf('(')>-1?str.indexOf('('):str.indexOf('（');
								if(index>0) {
									String temp=str.substring(0,index).trim();
									if(single.contains(temp)) {
										count++;
										isInclude=true;
										break;
									}
								}
								
							}
							if(!isInclude) {
								flag03=1;
								break;
							}
						}
						
					}else {
						count++;
						regex="(\\d{1,}|0.\\d{1,})kV[#a-zA-Z0-9\u4e00-\u9fa5]{1,}线";
						pattern=Pattern.compile(regex);
						Matcher newMatcher=pattern.matcher(single);
						boolean isPass=false;
						if(!newMatcher.find()) {
							for(String str:lineName) {
								int index=str.indexOf('(')>-1?str.indexOf('('):str.indexOf('（');
								if(index>0) {
									String temp=str.substring(0,index).trim();
									if(single.contains(temp)) {
										if(str.contains("全部停电")||str.contains("部分停电")||str.contains("全停")) {
											isPass=true;
											break;
										}
									}
									
								}
								
								}
							if(!isPass) {
								count--;
								flag=1;
								break;
							}
						}
					}
					
				}
			}
			
			if(count==0) {
				flag03=1;
			}
			
			
		}
		
		
		
		if(flag03==1) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "合格判据6");
			map2.put("criterion_children", "H6_sub3");
			map2.put("description", "不满足合格判据6：工作地段不明确，有错漏判定;");
			map2.put("field", "work_place");
			list.add(map2);
		}
		int flag04=0;
		String stationRegex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*)[\u4e00-\u9fa5]{2,}站";
		Pattern pattern=Pattern.compile(stationRegex);
		Matcher matcher=pattern.matcher(station);
		if(workTask!=null&&matcher.find()) {
			if(workTask.contains("开关")) {
				int count=0;
				String kregx="开关[\u4e00-\u9fa5]{0,}";
				
				boolean isIn=false,isOther=false;
			    pattern=Pattern.compile(kregx);
			    matcher=pattern.matcher(workTask);
				while(matcher.find()) {
					String match=matcher.group();
					for(String s:noInclude) {
						if(!match.contains(s)) {
							isIn=true;
						}else {
							isIn=false;
							break;
						}
					}
					if(isIn) {
						count++;
					}
					
				}
				
				
				if(count>0) {
					int realCount=0;
					String regex="(\\d{1,}|0.\\d{1,})kV([\\s\\S]*?)[0-9]{3,}\\s{0,}[(（][a-zA-Z0-9]{1,}[)）]([\\s\\S]*?)开关[\u4e00-\u9fa5]{0,}";
					pattern=Pattern.compile(regex);
					matcher=pattern.matcher(workTask);
				
						while(matcher.find()) {
							String match=matcher.group();
							for(String s:noInclude) {
								if(!match.contains(s)) {
									isOther=false;
								}else {
									isOther=true;
									break;
								}
							}
							if(!isOther) {
								
								realCount++;
							}
						}
						if(count!=realCount) {
							flag04=1;
						}
					
				}
				
//				boolean isIn=true;
//				for(String s:noInclude) {
//					if(workPlace.contains(s)) {
//						isIn=false;
//						break;
//					}
//				}
//				if(isIn) {
//					String regex="(\\d{1,}|0.\\d{1,})kV\\s{0,}[#a-zA-Z0-9\u4e00-\u9fa5]{0,}[a-zA-Z0-9]{1,}[(]\\d{1,}[)]";
//					Pattern pattern=Pattern.compile(regex);
//					Matcher matcher=pattern.matcher(workTask);
//					if(!matcher.find()) {
//						flag04=1;
//					}
//				}
				
			}
			if(flag04==1) {
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("ticket_id",toStr(ticketCheck.get("id")));
				map2.put("criterion", "合格判据6");
				map2.put("criterion_children", "H6_sub5");
				map2.put("description", "不满足合格判据6：设备双重编号不明确，有错漏判定;");
				map2.put("field", "work_task");
				list.add(map2);
			}
		}
        if(flag03==1||flag04==1&&flag==0) {
        	flag=1;
        }
		if(outageFlag==1) {
			flag=1;
		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	//第七种
	public List<Map<String,String>> seven(Map<String,Object> ticketCheck) {
		int flag = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("ticket_type")==null) {
			return list;
		}
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		if(ticketType.equals("21")) {
			if(ticketCheck.get("switch_earthwire_insulation")==null) {
				 flag=0;
			}else {
				String sei = toStr(ticketCheck.get("switch_earthwire_insulation"));
				Pattern p = Pattern.compile(".*\\d+.*");
				Matcher m = p.matcher(sei);
				if(!m.matches()) {
					flag = 2;
				}
				if(!sei.contains("杆")||!sei.contains("塔")) {
					flag = 2;
				}
				if(flag==2) {
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "合格判据7");
					map2.put("criterion_children", "H7_sub1");
					map2.put("description", "不满足合格判据7：应设接地线无“阿拉伯数字”、“杆”或“塔”字眼;");
					map2.put("field", "switch_earthwire_insulation");
					list.add(map2);
				}
			}
			int flag01 = 0;
			if(ticketCheck.get("other_care")==null) {
				flag01 = 0;
			}else {
				String otherCare = toStr(ticketCheck.get("other_care"));
				String preOtherCare = toStr(ticketCheck.get("permission_other_care"));
				if(otherCare.contains("重合闸")&&!preOtherCare.contains("重合闸")) {
					flag01 = 1;
				}
			}
			if(flag01==1) {
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("ticket_id",toStr(ticketCheck.get("id")));
				map2.put("criterion", "合格判据7");
				map2.put("criterion_children", "H7_sub2");
				map2.put("description", "不满足合格判据7：工作要求的安全措施中有停用重合闸而工作许可其他安全注意事项中无该字眼;");
				map2.put("field", "other_care@permission_other_care");
				list.add(map2);
			}
			if(flag01==0&&flag==2) {
				flag = 2;
			}else {
				flag = flag01;
			}
			
		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	//第八种
	public List<Map<String,String>> eight(Map<String,Object> ticketCheck) {
		int flag = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("whether_outer_dept")==null) {
			return list;
		}

//		String isOuter = toStr(ticketCheck.get("whether_outer_dept"));
//		if (isOuter.equals("1")) {
//			if(ticketCheck.get("ticket_counter_sign_uid")==null||ticketCheck.get("ticket_sign_uid")==null
//					&&ticketCheck.get("ticket_sign_time")==null||ticketCheck.get("ticket_counter_sign_time")==null) {
//				flag=1;
//			}else {
//				String ticketCounterSigner = toStr(ticketCheck.get("ticket_counter_sign_uid"));
//				String ticketSigner = toStr(ticketCheck.get("ticket_sign_uid"));
//				String ticketSignTime = toStr(ticketCheck.get("ticket_sign_time"));
//				String ticketCounterSignTime = toStr(ticketCheck.get("ticket_counter_sign_time"));
//				if (ticketSigner != null && !ticketSigner.equals("") && ticketSignTime != null && !ticketSignTime.equals("")
//						&& ticketCounterSigner != null && !ticketCounterSigner.equals("") && ticketCounterSignTime != null
//						&& !ticketCounterSignTime.equals("")) {
//					flag = 0;
//				}else {
//					flag = 1;
//				}
//
//			}
//			String ticketCounterSigner = toStr(ticketCheck.get("ticket_counter_sign_uid"));
//			String ticketSigner = toStr(ticketCheck.get("ticket_sign_uid"));
//			String ticketSignTime = toStr(ticketCheck.get("ticket_sign_time"));
//			String ticketCounterSignTime = toStr(ticketCheck.get("ticket_counter_sign_time"));
//			Map<String,String> m1 = isNull(toStr(ticketCheck.get("id")),ticketCounterSigner,"合格判据8","H8_sub1","不满足合格判据8：应“双签发”的工作票没有“双签发”;","ticket_counter_sign_uid");
//			Map<String,String> m2 = isNull(toStr(ticketCheck.get("id")),ticketSigner,"合格判据8","H8_sub1","不满足合格判据8：应“双签发”的工作票没有“双签发”;","ticket_sign_uid");
//			Map<String,String> m3 = isNull(toStr(ticketCheck.get("id")),ticketSignTime,"合格判据8","H8_sub1","不满足合格判据8：应“双签发”的工作票没有“双签发”;","ticket_sign_time");
//			Map<String,String> m4 = isNull(toStr(ticketCheck.get("id")),ticketCounterSignTime,"合格判据8","H8_sub1","不满足合格判据8：应“双签发”的工作票没有“双签发”;","ticket_counter_sign_time");
//			list.add(m1);
//			list.add(m2);
//			list.add(m3);
//			list.add(m4);
//			if(m1.isEmpty()&&m2.isEmpty()&&m3.isEmpty()&&m4.isEmpty()) {
//				flag = 0;
//			}else {
//				flag = 1;
//			}
//		}
//		if(flag==1) {
//			Map<String,String> map2 = new HashMap<String,String>();
//			map2.put("ticket_id",toStr(ticketCheck.get("id")));
//			map2.put("criterion", "合格判据8");
//			map2.put("criterion_children", "H8_sub1");
//			map2.put("description", "不满足合格判据8：应“双签发”的工作票没有“双签发”;");
//			map2.put("field", "ticket_counter_sign_uid@ticket_sign_uid@ticket_sign_time@ticket_counter_sign_time");
//			list.add(map2);
//		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	//第九种
	public List<Map<String,String>> nine(Map<String,Object> ticketCheck) {
		int flag = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
//		if(ticketCheck.get("permission_time")==null) {
//			flag=1;
//		}else {
//			String time = toStr(ticketCheck.get("permission_time"));
//			if (time ==null || time.equals("")) {
//				flag = 1;
//			}
//		}
//		if(flag==1) {
//			Map<String,String> map2 = new HashMap<String,String>();
//			map2.put("ticket_id",toStr(ticketCheck.get("id")));
//			map2.put("criterion", "合格判据9");
//			map2.put("criterion_children", "H9_sub1");
//			map2.put("description", "不满足合格判据9：工作许可时间未填写;");
//			map2.put("field", "permission_time");
//			list.add(map2);
//		}
//		map.put("result", String.valueOf(flag));
		return list;
	}
	//第十种
	public List<Map<String,String>> ten(Map<String,Object> ticketCheck) {
		int flag = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("ticket_type")==null) {
			return list;
		}

		String ticketType = toStr(ticketCheck.get("ticket_type"));
		if(ticketType.equals("11")||ticketType.equals("12")||ticketType.equals("13")||ticketType.equals("21")||ticketType.equals("22")) {
			if(ticketCheck.get("work_end_principal")==null||ticketCheck.get("permission_work_principal")==null) {
				 if(ticketCheck.get("work_end_principal")==null&&ticketCheck.get("permission_work_principal")==null) {
					 flag = 0;
				 }else {
					 flag = 1;
				 }
				
			}else {
				String workEndPrincipal = toStr(ticketCheck.get("work_end_principal"));
				String perWorkPrincipal = toStr(ticketCheck.get("permission_work_principal"));
				if(!workEndPrincipal.equals(perWorkPrincipal)) {
					if(ticketCheck.get("change_principal_uid")!=null||ticketCheck.get("change_new_principal_uid")!=null||ticketCheck.get("change_time")!=null) {
						flag = 0;
					}else {
						flag=1;
					}
				}
			}
		}
		if(flag==1) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "合格判据10");
			map2.put("criterion_children", "H10_sub1");
			map2.put("description", "不满足合格判据10：工作许可栏负责人签名和终结栏负责人签名不一致时,工作负责人变更栏无内容;");
			map2.put("field", "change_principal_uid@change_new_principal_uid@change_time");
			list.add(map2);
		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	
	//第十一种
	public List<Map<String,String>> eleven(Map<String,Object> ticketCheck) {
		int flag = 1;
		Map<String,String> map = new HashMap<String,String>();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("plan_end_time")==null||ticketCheck.get("work_end_time")==null) {
			 flag =0;
		}else {
			String planEndTime = toStr(ticketCheck.get("plan_end_time"));
			String ticketEndTime = toStr(ticketCheck.get("work_end_time"));
			Integer i = ticketEndTime.compareTo(planEndTime);
			if (i > 0) {
				if(ticketCheck.get("delay_time")==null) {
					flag = 1;
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "合格判据11");
					map2.put("criterion_children", "H11_sub1");
					map2.put("description", "不满足合格判据11：工作终结时间超过计划工作结束时间，且工作延期栏无内容;");
					map2.put("field", "delay_time");
					list.add(map2);
				}else {
					String workDelayTime = toStr(ticketCheck.get("delay_time"));
					if (workDelayTime != null && !workDelayTime.equals("")) {
						Integer j = ticketEndTime.compareTo(workDelayTime);
						if (j < 0) {
							flag = 0;
						}else {
							Map<String,String> map2 = new HashMap<String,String>();
							map2.put("ticket_id",toStr(ticketCheck.get("id")));
							map2.put("criterion", "合格判据11");
							map2.put("criterion_children", "H11_sub2");
							map2.put("description", "不满足合格判据11：工作终结时间超过计划工作结束时间且超过工作延期栏内有效期;");
							map2.put("field", "delay_time");
							list.add(map2);
						}
					}
				}
				if(ticketCheck.get("ticket_type")==null) {
					return list;
				}
				String ticketType = toStr(ticketCheck.get("ticket_type"));
				if(flag == 0&&(ticketType.equals("11")||ticketType.equals("21"))) {
					
					if(ticketCheck.get("delay_fill_time")==null) {
						 flag = 1;
					}else {
						String workDelayApplyTime = toStr(ticketCheck.get("delay_fill_time"));
						
						if(i>0) {
							if(workDelayApplyTime==null||workDelayApplyTime.equals("")) {
								flag = 1;		
							}
							workDelayApplyTime = formateTime(workDelayApplyTime);
							planEndTime = formateTime(planEndTime);
							SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
							try {
								Date date1 = df.parse(workDelayApplyTime);
								Date date2 = df.parse(planEndTime);
								long time = date2.getTime() - date1.getTime();
								long hours = (time - (time / (1000 * 60 * 60 * 24)) * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
								if (hours < 2) {
									flag = 1;
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					if(flag==1) {
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "合格判据11");
						map2.put("criterion_children", "H11_sub3");
						map2.put("description", "不满足合格判据11：工作终结时间超过计划工作结束时间，工作延期栏延期申请时间与计划工作结束时间间隔不足2小时;");
						map2.put("field", "delay_fill_time");
						list.add(map2);
					}

				}
			} else {
				flag = 0;
			}
			int flag01=0;
			if(ticketCheck.get("ticket_type")==null) {
				return list;
			}
			String ticketType = toStr(ticketCheck.get("ticket_type"));
			if(ticketType.equals("11")||ticketType.equals("12")||ticketType.equals("12")||ticketType.equals("21")) {
				
				if(ticketCheck.get("gap_start_time")==null) {
					 flag01 = 0;
				}else {
					String workBreakTime = toStr(ticketCheck.get("gap_start_time"));
					if(workBreakTime==null||workBreakTime.equals("")) {
						flag01 = 2;		
					}
					if(workBreakTime.length()<11) {
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						workBreakTime = year + "-" +workBreakTime;
					}
					workBreakTime = formateTime(workBreakTime);
					ticketEndTime = formateTime(ticketEndTime);
					SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
					try {
						Date date1 = df.parse(workBreakTime);
						Date date2 = df.parse(ticketEndTime);
						long time = date2.getTime() - date1.getTime();
						long days = time / (1000 * 60 * 60 * 24);  
						long hours = (time - (time / (1000 * 60 * 60 * 24)) * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
						if(days>=1) {
							if (hours > 0) {
								flag01 = 2;
							}else {
								flag01 = 0;
							}
						}
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				

			}else {
				flag01 = 0;
			}
			if(flag01==2) {
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("ticket_id",toStr(ticketCheck.get("id")));
				map2.put("criterion", "合格判据11");
				map2.put("criterion_children", "H11_sub4");
				map2.put("description", "不满足合格判据11：工作间断栏最后一次工作开工时间到工作终结栏工作终结时间超过24小时;");
				map2.put("field", "gap_start_time");
				list.add(map2);
			}
			if(flag==0&&flag01==2) {
				flag=2;
			}
		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	
	//第十二种
	public int twelve(Map<String,Object> ticketCheck) {
		int flag = 0;

		return flag;
	}
	
	//第十三种
	public List<Map<String,String>> thirteen(Map<String,Object> ticketCheck) {
		int flag = 0;
		int flag01 = 0;
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		resultList.add(map);
		if(ticketCheck.get("ticket_type")==null) {
			return resultList;
		}
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		
		if(ticketType.equals("11")||ticketType.equals("21")) {
			
			String licensorMeasureEnd = toStr(ticketCheck.get("measure_end_fill_time"));//许可人措施终结填写时间
			String unopenedGroundKnife = toStr(ticketCheck.get("measure_end_per_uid"));//许可人措施终
			String numUnopenedGroundKnife = toStr(ticketCheck.get("dispatch_earthwire_code"));//汇报调度未拆除接地线装设地点及编号
			String unremovedGroundWire = toStr(ticketCheck.get("dispatch_earthwire_count"));//汇报调度未拆除接地线数量
			String numUnremovedGroundWire = toStr(ticketCheck.get("dispatch_switch_count"));//汇报调度未拉开接地刀闸数量
			String dispatchSwitchCode = toStr(ticketCheck.get("dispatch_switch_code"));//汇报调度未拉开接地刀闸名称或编号
			Map<String,String> m1 = isNull(toStr(ticketCheck.get("id")),licensorMeasureEnd,"合格判据13","H13_sub1","不满足合格判据13：许可人措施终结无内容;","measure_end_fill_time");
			Map<String,String> m2 = isNull(toStr(ticketCheck.get("id")),unopenedGroundKnife,"合格判据13","H13_sub1","不满足合格判据13：许可人措施终结无内容;","measure_end_per_uid");
			Map<String,String> m3 = isNull(toStr(ticketCheck.get("id")),numUnopenedGroundKnife,"合格判据13","H13_sub1","不满足合格判据13：汇报调度栏无内容;","dispatch_earthwire_code");
			Map<String,String> m4 = isNull(toStr(ticketCheck.get("id")),unremovedGroundWire,"合格判据13","H13_sub1","不满足合格判据13：汇报调度栏无内容;","dispatch_earthwire_count");
			Map<String,String> m5 = isNull(toStr(ticketCheck.get("id")),numUnremovedGroundWire,"合格判据13","H13_sub1","不满足合格判据13：汇报调度栏无内容;","dispatch_switch_count");
			Map<String,String> m6 = isNull(toStr(ticketCheck.get("id")),dispatchSwitchCode,"合格判据13","H13_sub1","不满足合格判据13：汇报调度栏无内容;","dispatch_switch_code");
			resultList.add(m1);
			resultList.add(m2);
			resultList.add(m3);
			resultList.add(m4);
			resultList.add(m5);
			resultList.add(m6);
			if(m1.isEmpty()&&m2.isEmpty()&&m3.isEmpty()&&m4.isEmpty()&&m5.isEmpty()&&m6.isEmpty()) {
				flag = 0;
			}else {
				flag = 1;
			}
			
			
			if(ticketCheck.get("dispatch_switch_count")==null&&ticketCheck.get("dispatch_switch_code")==null&&ticketCheck.get("switch_earthwire_insulation")==null) {
				flag01 =0;
			}else {	
				numUnopenedGroundKnife = Trim(numUnopenedGroundKnife);//汇报调度未拆除接地线装设地点及编号
				unremovedGroundWire = Trim(unremovedGroundWire);//汇报调度未拆除接地线数量
				numUnremovedGroundWire = Trim(numUnremovedGroundWire);//汇报调度未拉开接地刀闸数量
				dispatchSwitchCode = Trim(dispatchSwitchCode);//汇报调度未拉开接地刀闸名称或编号
				String insulation = Trim(toStr(ticketCheck.get("switch_earthwire_insulation")));//应合的接地刀闸（注明双重名称或编号）或应装的接地线（装设地点）
				
				String newStr = numUnopenedGroundKnife.replaceAll("[\\p{P}&&[^#＃（）().]]", ",");
				String[] list= newStr.split(",");
				list = removeEmptyArrays(list);
				ArrayList<String> knifeList  = new ArrayList<String>(Arrays.asList(list));
				
				String newStr01 = dispatchSwitchCode.replaceAll("[\\p{P}&&[^#＃]]", ",");
				String[] list01= newStr01.split(",");
				list01 = removeEmptyArrays(list01);
				
				//String[] newList = ArrayUtils.addAll(list01);
				ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list01));
			
				String insulationStr = insulation.replaceAll("[\\p{P}&&[^#＃]]", ",");
				String[] lastList = insulationStr.split(",");
				lastList = removeEmptyArrays(lastList);
				ArrayList<String> insList = new ArrayList<String>(Arrays.asList(lastList));
			
				String reg = "^[\\u4e00-\\u9fa5]+$";
				String[] kongList = {"无","空","没有","","不需要","0"};
				int count = 0;

				if(Arrays.asList(kongList).contains(dispatchSwitchCode)
						&&Arrays.asList(kongList).contains(insulation)) {
					flag01 = 0;
				}else if(Arrays.asList(kongList).contains(dispatchSwitchCode)){
					
					for(int i=0;i<insList.size();i++) {
						if(insList.get(i).length()>=3&&insList.get(i).length()<=8&&!insList.get(i).matches(reg)) {
							count +=1;
						}
					}
					if(count!=0) {
						flag01 = 2;
					}
				}else {
					for(int i=0;i<insList.size();i++) {
						if(insList.get(i).length()>=3&&insList.get(i).length()<=8&&!insList.get(i).matches(reg)&&!arrayList.contains(insList.get(i))) {
							flag01=2;
							break;
						}else {
							if(insList.get(i).length()>=3&&insList.get(i).length()<=8&&!insList.get(i).matches(reg)) {
								count +=1;
							}
//							if(i<=arrayList.size()-1) {
//								count +=1;
//							}
						}

//						if(!insList.get(i).contains(arrayList.get(i))) {
//							flag01=2;
//							break;
//						}else {
//							count +=1;
//						}
					}
					if(!(count+"").equals(numUnremovedGroundWire)) {
						flag01=2;
					}

				}
//				if(ticketType.equals("21")) {
//					int fcount = 0;
//					if(Arrays.asList(kongList).contains(numUnopenedGroundKnife)
//							&&Arrays.asList(kongList).contains(insulation)) {
//						flag01 = 0;
//					}else if(Arrays.asList(kongList).contains(numUnopenedGroundKnife)){
//						
//						for(int i=0;i<insList.size();i++) {
//							if(insList.get(i).length()>8&&insList.get(i).equals("调度端")) {
//								fcount +=1;
//							}
//						}
//						if(fcount!=0) {
//							flag01 = 2;
//						}
//					}else {
//						for(int i=0;i<insList.size();i++) {
//							if(insList.get(i).length()>8&&!knifeList.contains(insList.get(i))) {
//								flag01=2;
//								break;
//							}else {
//								if(insList.get(i).length()>8&&insList.get(i).equals("调度端")) {
//									fcount +=1;
//								}
//
//							}
//
//						}
//						if(!(fcount+"").equals(unremovedGroundWire)) {
//							flag01=2;
//						}
//
//					}
//				}
			}
		}


		if(flag01==2) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "合格判据13");
			map2.put("criterion_children", "H13_sub2");
			map2.put("description", "不满足合格判据13：汇报调度栏未拉开接地刀闸双重名称或编号与工作要求的安全措施栏应合的接地刀闸（注明双重编号）或应装的接地线（装设地点）编号或数量不一致;");
			map2.put("field", "dispatch_switch_count@dispatch_switch_code@switch_earthwire_insulation");
			if(ticketType.equals("21")) {
				map2.put("field", "dispatch_earthwire_count@dispatch_earthwire_code@dispatch_switch_count@dispatch_switch_code@switch_earthwire_insulation");
			}
			resultList.add(map2);
		}
		if(flag==0) {
			if(flag01==0) {
				flag=0;
			}else {
				flag=flag01;
			}
		}
		map.put("result", String.valueOf(flag));
		return resultList;
	}
	
	//第十四种
	//public int fourteen(Map<String,Object> ticketCheck) {
		int flag = 0;
//		String ticketType = ticketCheck.get("ticket_type");
//		if(ticketType.equals("厂站第一种工作票")||ticketType.equals("厂站第二种工作票")||ticketType.equals("厂站第三种工作票")) {
//			String workTask = ticketCheck.get("work_task");
//			if(workTask.endsWith("试验")) {
//				String remark = ticketCheck.get("remark");
//				if(!remark.contains("安全措施")) {
//					flag = 1;
//				}
//			}
//		}
		//return flag;
	//}
		public List<Map<String,String>> fourteen(Map<String,Object> ticketCheck) {
			int flag = 0;
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			Map<String,String> map = new HashMap<String,String>();
			map.put("result", String.valueOf(flag));
			list.add(map);
			if(ticketCheck.get("add_content_detail")!=null) {
				if(ticketCheck.get("add_content_permission_uid")==null) {
					flag=1;
				}
			}
			if(flag==1) {
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("ticket_id",toStr(ticketCheck.get("id")));
				map2.put("criterion", "合格判据14");
				map2.put("criterion_children", "H14_sub1");
				map2.put("description", "不满足合格判据14：工作过程中需要变更安全措施时，未经许可人签名同意;");
				map2.put("field", "add_content_detail@add_content_permission_uid");
				list.add(map2);
			}
			map.put("result", String.valueOf(flag));
			return list;
		}

	//第十五种
	public int fifteen(Map<String,Object> ticketCheck) {
		int flag = 0;
		
		return flag;
	}
	
	//第十六种
	public int sixteen(Map<String,Object> ticketCheck) {
		int flag = 0;
//		String workPrincipal = ticketCheck.getWorkPrincipal();//工作负责人
//		String ticketSigner = ticketCheck.getTicketSigner();//工作票签发人
//		String workLicensor = ticketCheck.getWorkLicensor();//工作许可人
//		String ticketCounterSigner = ticketCheck.getTicketCounterSigner();//工作会签人
//		if(workPrincipal!=null&&!workPrincipal.equals("")&&(userMap.get(workPrincipal)==null||!userMap.get(workPrincipal).contains("负责人"))) {
//			flag = 1;
//		}
//		if(ticketSigner!=null&&!ticketSigner.equals("")&&(userMap.get(ticketSigner)==null||!userMap.get(ticketSigner).contains("签发人"))) {
//			flag = 1;
//		}
//		if(workLicensor!=null&&!workLicensor.equals("")&&(userMap.get(workLicensor)==null||!userMap.get(workLicensor).contains("许可人"))) {
//			flag = 1;
//		}
//		if(ticketCounterSigner!=null&&!ticketCounterSigner.equals("")&&(userMap.get(ticketCounterSigner)==null||!userMap.get(ticketCounterSigner).contains("工作票签发人"))) {
//			flag = 1;
//		}
		return flag;
	}
	//第十七种
	public int seventeen(Map<String,Object> ticketCheck) {
		int flag = 0;
		
		return flag;
	}
	
	//不规范校验
	//第一种
	public int oneStandard(Map<String,Object> ticketCheck) {
		int flag = 0;
		return flag;
	}
	
	//第二种
	public List<Map<String,String>> twoStandard(Map<String,Object> ticketCheck,List<String> locationList) {
		int flag = 0;
		int flag01 = 0;
		int nextFlag = 0;
		List<Map<String,String>>list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", String.valueOf(flag));
		list.add(map);
		if(ticketCheck.get("billboard")==null) {
			 flag =0;
		}else {
			String billboard = toStr(ticketCheck.get("billboard"));
			
			if(billboard!=null&&!billboard.equals("")) {
				if(billboard.contains("遮拦")&&!billboard.contains("装设")) {
					flag = 1;
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "规范判据5");
					map2.put("criterion_children", "G5_sub1");
					map2.put("description", "不满足规范判据5：安全措施栏中有遮拦字眼无装设字眼;");
					map2.put("field", "billboard");
					list.add(map2);
				}
//					else {
//					int count =getStrCount(billboard,"遮拦");
//					int trueCount = getStrCount(billboard,"装设");
//					if(count!=trueCount) {
//						flag = 1;
//						Map<String,String> map2 = new HashMap<String,String>();
//						map2.put("ticket_id",ticketCheck.get("id").toString());
//						map2.put("criterion", "规范判据5");
//						map2.put("criterion_children", "G5_sub1");
//						map2.put("description", "规范判据5：安全措施栏中有遮拦字眼无装设字眼;");
//						map2.put("field", "billboard");
//						list.add(map2);
//					}
//				}
				if((billboard.contains("标示牌")&&!billboard.contains("悬挂"))||(billboard.contains("标识牌")&&!billboard.contains("悬挂"))) {
					flag = 1;
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "规范判据5");
					map2.put("criterion_children", "G5_sub2");
					map2.put("description", "不满足规范判据5：安全措施栏中有标识牌字眼无悬挂字眼;");
					map2.put("field", "billboard");
					list.add(map2);
				}else {
					int count =getStrCount(billboard,"标识牌");
					int count01 = getStrCount(billboard,"标示牌");
					int trueCount = getStrCount(billboard,"悬挂");
					if(trueCount<count||trueCount<count01) {
						flag = 1;
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("ticket_id",toStr(ticketCheck.get("id")));
						map2.put("criterion", "规范判据5");
						map2.put("criterion_children", "G5_sub2");
						map2.put("description", "不满足规范判据5：安全措施栏中有标识牌字眼无悬挂字眼;");
						map2.put("field", "billboard");
						list.add(map2);
					}
				}
			}
		}
		if(ticketCheck.get("work_member_count")==null||ticketCheck.get("dispatch_earthwire_count")==null) {
			nextFlag = 0;
		}else {
			List<String> countList = new ArrayList<String>();
			String workMemberCount = toStr(ticketCheck.get("work_member_count"));
			String earthwireCount = toStr(ticketCheck.get("dispatch_earthwire_count"));
			countList.add(workMemberCount);
			countList.add(earthwireCount);
			if(ticketCheck.get("whether_secondbill")!=null) {
				String secondbil = toStr(ticketCheck.get("whether_secondbill"));
				String secondbilCount = toStr(ticketCheck.get("secondbill_count"));
				if(secondbil.equals("1")) out:{
					if(ticketCheck.get("secondbill_count")==null) {
						nextFlag = 1;
						break out;
					}
					countList.add(secondbilCount);
				}
			}
			for(int i=0;i<countList.size();i++) {
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(countList.get(i));
				if(!m.matches()) {
					nextFlag=1;
					break;
				}
			}	
		}
		if(nextFlag==1) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "规范判据5");
			map2.put("criterion_children", "G5_sub3");
			map2.put("description", "不满足规范判据5：工作班人数、二次措施单张数、未拆除接地线组数未使用阿拉伯数字;");
			map2.put("field", "work_member_count@secondbill_count@dispatch_earthwire_count");
			list.add(map2);
		}	
		
		if(flag==1||nextFlag==1) {
			flag=1;
		}else {
			flag=0;
		}
		map.put("result", String.valueOf(flag));
		if(ticketCheck.get("ticket_type")==null) {
			return list;
		}
		String ticketType = toStr(ticketCheck.get("ticket_type"));
		if(ticketType.equals("11")) {
			int tempFlag = 0;
			if(ticketCheck.get("work_place")==null) {
				tempFlag = 0;
			}else {
				String workPlace = toStr(ticketCheck.get("work_place"));
				if(workPlace.contains("隔离开关")&&(!workPlace.contains("母线侧")||!workPlace.contains("线路侧"))) {
					tempFlag = 1;
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("ticket_id",toStr(ticketCheck.get("id")));
					map2.put("criterion", "规范判据5");
					map2.put("criterion_children", "G5_sub4");
					map2.put("description", "不满足规范判据5：工作地点保留带电部位：有隔离开关关键字，未出现“母线侧”或“线路侧”字眼;");
					map2.put("field", "work_place");
					list.add(map2);
				}
			}
			if(flag==0&&tempFlag==1) {
				flag=1;
			}
		}else if(ticketType.equals("21"))out:{
			if(ticketCheck.get("function_location_name")==null) {
				flag01=0;
				break out;
			}
			String str = toStr(ticketCheck.get("function_location_name"));
			Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
	         Matcher matcher = pattern.matcher(str);
	         String newStr = matcher.replaceAll(",");
	         String lastStr = newStr.replace(",,,,,,", ",").replace(",,,,,", ",").replace(",,,,", ",").replace(",,,", ",").replace(",,", ",");
			String[] nameList = lastStr.split(",");
			flag01 = 1;
			for(int i=0;i<nameList.length;i++) {
				if(locationList.contains(nameList[i])) {
					flag01 = 0;
					break;
				}
			}
		}
		if(flag01==1) {
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("ticket_id",toStr(ticketCheck.get("id")));
			map2.put("criterion", "规范判据5");
			map2.put("criterion_children", "G5_sub5");
			map2.put("description", "不满足规范判据5：停电线路信息中无与“CIM”图中线路名称库一致的内容;");
			map2.put("field", "function_location_name");
			list.add(map2);
		}
		if(flag==0) {
			if(flag01==0) {
				flag=0;
			}else {
				flag=flag01;
			}
		}
		map.put("result", String.valueOf(flag));
		return list;
	}
	
	private String toStr(Object obj) {
		String str = "";
		if (obj == null) {
			str = "";
		} else {
			str = obj.toString();
		}
		return str;
	}
	
	private Map<String,String> isNull(String id,String str,String criterion,String criterion_children,String description,String field){
		Map<String,String> map = new HashMap<String,String>();
		
		if(str.equals("")) {
			map.put("ticket_id", id);
			map.put("criterion", criterion);
			map.put("criterion_children", criterion_children);
			map.put("description", description);
			map.put("field", field);
		}
		return map;
	}
	
	
	private String[] removeEmptyArrays(String[] strArray) {
		List<String> strList= Arrays.asList(strArray);
		List<String> strListNew=new ArrayList<>();
		
		for (int i = 0; i <strList.size(); i++) {
			if (strList.get(i)!=null&&!strList.get(i).equals("")){
				strListNew.add(strList.get(i).replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9()（）]", ""));
			}
		}
		String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
		return   strNewArray;
	}
	public String Trim(String str) {
		String newStr = str.replaceAll(" ", "");
		return newStr;
	}
	
	private  String[] removeEmptyForFunction(String[] strArray) {
		List<String> strList= Arrays.asList(strArray);
		List<String> strListNew=new ArrayList<>();
		
		for (int i = 0; i <strList.size(); i++) {
			if (strList.get(i)!=null&&!strList.get(i).equals("")){
				if(strList.get(i).indexOf("(")>0) {
					strListNew.add(strList.get(i).substring(0, strList.get(i).indexOf("(")));
				}else if (strList.get(i).indexOf("（")>0) {
					strListNew.add(strList.get(i).substring(0, strList.get(i).indexOf("（")));
				}else {
					strListNew.add(strList.get(i));
				}
			}
		}
		String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
		return   strNewArray;
	}
}
