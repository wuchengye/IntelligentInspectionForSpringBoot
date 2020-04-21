package com.bda.bdaqm.electric.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.AnalysisStatisticMapper;
import com.bda.bdaqm.electric.model.AnalysisStatistic;
import com.bda.bdaqm.electric.model.FunctionLocationName;
import com.bda.bdaqm.util.ComboBoxItem;


@Service
public class TicketNumChartsService {
	
	
	@Autowired
	private AnalysisStatisticMapper analysisStatisticMapper;
	
	public final static String[] kvLevel = {"500KV","220KV","110KV","66KV","35KV","20KV","10KV","380V"};
	
	/**
	 * 根据单位获取工作票数和合格率规范率
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public  Map<String,Object> getUnitNumAndCheck(List<String> list,String beginTime,String endTime){
		String regStr = "";
		String[] strings = new String[list.size()];
		list.toArray(strings);
		for(int i=0;i<list.size();i++){
			if(i == list.size()-1){
				regStr += list.get(i);
			}else{
				regStr += list.get(i)  + "|";
			}
		}
		Map<String,Object> objMap = new HashMap<String,Object>();
		//String count = ticketNumChartsMapper.getUnitNumCount(regStr, beginTime, endTime);
		List<AnalysisStatistic> list2= analysisStatisticMapper.getUnitNumAndCheck(regStr,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		for(AnalysisStatistic tnn: list2){
			String[] paths = tnn.getNameFullPath().split("/");
			for(int i=0;i<strings.length;i++){
				if(strings[i].equals(paths[2])){
					if(map.get(strings[i])==null){
						Map<String,String> map2 = new HashMap<String,String>();
						map.put(strings[i], map2);
					}
					//统计总数
					String total = map.get(strings[i]).get("totalNum");
					if(total == null){
						map.get(strings[i]).put("totalNum", 1+"");
					}else{
						map.get(strings[i]).put("totalNum", Integer.parseInt(total)+1+"");
					}
					//统计合格数量				
					//String qualified = map.get()
					String qualified = map.get(strings[i]).get("qualified");
					if("0".equals(tnn.getCheckResult())){
						if(qualified == null){
							map.get(strings[i]).put("qualified", 1+"");
						}else{
							map.get(strings[i]).put("qualified", Integer.parseInt(qualified)+1+"");
						}
					}
					//统计规范数量
					String standard = map.get(strings[i]).get("standard");
					if("0".equals(tnn.getCheckResult())&&"0".equals(tnn.getStandardResult())){
						if(standard == null){
							map.get(strings[i]).put("standard", 1+"");
						}else{
							map.get(strings[i]).put("standard", Integer.parseInt(standard)+1+"");
						}
					}
				}
			}
		}
		//objMap.put("count", count);
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 根据单位获取工作票延期数量和延期时间
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public  Map<String,Object> getUnitDelayNumTime(List<String> list,String beginTime,String endTime){
		String regStr = "";
		String[] strings = new String[list.size()];
		list.toArray(strings);
		//拼接单位参数
		for(int i=0;i<list.size();i++){
			if(i == list.size()-1){
				regStr += list.get(i);
			}else{
				regStr += list.get(i)  + "|";
			}
		}
		Map<String,Object> objMap = new HashMap<String,Object>();
		//String count = ticketNumChartsMapper.getUnitNumCount(regStr, beginTime, endTime);
		List<AnalysisStatistic> list2= analysisStatisticMapper.getUnitNumAndCheck(regStr,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		//循环所有查询记录，对记录结果进行分类计算
		for(AnalysisStatistic tnn: list2){
			String[] paths = tnn.getNameFullPath().split("/");
			for(int i=0;i<strings.length;i++){
				if(strings[i].equals(paths[2])){
					if(map.get(strings[i])==null){
						Map<String,String> map2 = new HashMap<String,String>();
						map.put(strings[i], map2);
					}
					//统计总数
					String total = map.get(strings[i]).get("totalNum");
					if(total == null){
						map.get(strings[i]).put("totalNum", 1+"");
					}else{
						map.get(strings[i]).put("totalNum", Integer.parseInt(total)+1+"");
					}
					//统计延期数量			
					//String qualified = map.get()
					String delayNum = map.get(strings[i]).get("delayNum");
					if("1".equals(tnn.getIsDelay())){
						if(delayNum == null){
							map.get(strings[i]).put("delayNum", 1+"");
						}else{
							map.get(strings[i]).put("delayNum", Integer.parseInt(delayNum)+1+"");
						}
					}else{
						//map.get(strings[i]).put("delayNum", 0+"");
					}
					//统计延期时间
					String delayTime = map.get(strings[i]).get("delayTime");
					if("1".equals(tnn.getIsDelay())){
						long minute = 0;
						long hour = 0;
						try {
							minute = getDatePoor(tnn.getPlanEndTime(),tnn.getWorkEndTime());
							if(minute <= 60){
								hour = 1;
							}else if((minute%60) > 0 ){
								hour = minute / 60 +1;
							}else{
								hour = minute / 60;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if(delayTime == null){
							map.get(strings[i]).put("delayTime", hour+"");
						}else{
							map.get(strings[i]).put("delayTime", (Long.parseLong(delayTime)+hour)+"");
						}
					}else{
						//map.get(strings[i]).put("delayTime", 0+"");
					}
				}
			}
		}
		//objMap.put("count", count);
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 获取按电压等级统计工作票数量
	 * @param list
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> getKVLevelNum(String list,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		
		Map<String,String> map = new HashMap<String,String>();
		List<AnalysisStatistic> list2= analysisStatisticMapper.getKVNumAndCheck(list,beginTime,endTime);
		//循环获取数据每条记录，计算记录数
		for(AnalysisStatistic as:list2){
			//将工作任务所有字段信息转成大写
			if(StringUtils.isBlank(as.getWorkTask())){
				if(StringUtils.isBlank(as.getFunctionLocationName())){
					continue;
				}
				/*for(int i=0;i<kvLevel.length;i++){
					if(as.getFunctionLocationName().toUpperCase().indexOf(kvLevel[i]) != -1){
						String total = map.get(kvLevel[i]);
						if(total == null){
							map.put(kvLevel[i],1+"");
						}else{
							map.put(kvLevel[i],Integer.parseInt(total)+1+"");
						}
					}
				}*/
			}
			String workTask = as.getWorkTask().toUpperCase();
			//设置变量由于判断该条工作票在工作任务是否已经获取电压等级，没获取则去功能位置名称中获取
			boolean isWorkTask = true;
			for(int i=0;i<kvLevel.length;i++){
				if("10KV".equals(kvLevel[i])||"20KV".equals(kvLevel[i])){
					String regex = "(^\\S{0,}[^0-9]"+kvLevel[i]+")|(^"+kvLevel[i]+")";
					Pattern pattern=Pattern.compile(regex);
					Matcher matcher=pattern.matcher(workTask);
					if(matcher.find()){
						isWorkTask = false;
						String total = map.get(kvLevel[i]);
						if(total == null){
							map.put(kvLevel[i],1+"");
						}else{
							map.put(kvLevel[i],Integer.parseInt(total)+1+"");
						}
					}
				}else if("380V".equals(kvLevel[i])){
					if(workTask.indexOf("0.4KV") != -1 || workTask.indexOf("380V") != -1){
						isWorkTask = false;
						String total = map.get(kvLevel[i]);
						if(total == null){
							map.put(kvLevel[i],1+"");
						}else{
							map.put(kvLevel[i],Integer.parseInt(total)+1+"");
						}
					}
				}else{
					if(workTask.indexOf(kvLevel[i]) != -1){
						isWorkTask = false;
						String total = map.get(kvLevel[i]);
						if(total == null){
							map.put(kvLevel[i],1+"");
						}else{
							map.put(kvLevel[i],Integer.parseInt(total)+1+"");
						}
					}
				}
			}
			if(isWorkTask){
				String functionLocationName = as.getFunctionLocationName().toUpperCase();
				for(int i=0;i<kvLevel.length;i++){
					if("10KV".equals(kvLevel[i])||"20KV".equals(kvLevel[i])){
						String regex = "(^\\S{0,}[^0-9]"+kvLevel[i]+")|(^"+kvLevel[i]+")";
						Pattern pattern=Pattern.compile(regex);
						Matcher matcher=pattern.matcher(functionLocationName);
						if(matcher.find()){
							String total = map.get(kvLevel[i]);
							if(total == null){
								map.put(kvLevel[i],1+"");
							}else{
								map.put(kvLevel[i],Integer.parseInt(total)+1+"");
							}
						}
					}else if("380V".equals(kvLevel[i])){
						if(functionLocationName.indexOf("0.4KV") != -1 || functionLocationName.indexOf("380V") != -1){
							String total = map.get(kvLevel[i]);
							if(total == null){
								map.put(kvLevel[i],1+"");
							}else{
								map.put(kvLevel[i],Integer.parseInt(total)+1+"");
							}
						}
					}else{
						if(functionLocationName.indexOf(kvLevel[i]) != -1){
							String total = map.get(kvLevel[i]);
							if(total == null){
								map.put(kvLevel[i],1+"");
							}else{
								map.put(kvLevel[i],Integer.parseInt(total)+1+"");
							}
						}
					}
				}
			}
		}
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 巡检中心工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> getQCCenterByNuit(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<AnalysisStatistic> list = analysisStatisticMapper.getQCCenterByNuit(unit,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		//String[] paths = null;
		for(AnalysisStatistic as : list){
			//paths = as.getNameFullPath().split("/");
			String orgName = as.getOrgName();
			if(map.get(orgName)==null){
				Map<String,String> map2 = new HashMap<String,String>();
				map.put(orgName, map2);
			}
			//统计总数
			String total = map.get(orgName).get("totalNum");
			if(total == null){
				map.get(orgName).put("totalNum", 1+"");
			}else{
				map.get(orgName).put("totalNum", Integer.parseInt(total)+1+"");
			}
			//统计合格数量				
			//String qualified = map.get()
			String qualified = map.get(orgName).get("qualified");
			if("0".equals(as.getCheckResult())){
				if(qualified == null){
					map.get(orgName).put("qualified", 1+"");
				}else{
					map.get(orgName).put("qualified", Integer.parseInt(qualified)+1+"");
				}
			}
			//统计规范数量
			String standard = map.get(orgName).get("standard");
			if("0".equals(as.getCheckResult())&&"0".equals(as.getStandardResult())){
				if(standard == null){
					map.get(orgName).put("standard", 1+"");
				}else{
					map.get(orgName).put("standard", Integer.parseInt(standard)+1+"");
				}
			}
		}
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 各班组工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> getGroupNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<AnalysisStatistic> list = analysisStatisticMapper.getGroupNumAndCheck(unit,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		String orgLevel = "";
		String orgName = "";
		String fullName = "";
		for(AnalysisStatistic as : list){
			orgLevel = as.getOrgLevel();
			if("5".equals(orgLevel)){
				orgName = as.getOrgName();
			}else{
				fullName = as.getNameFullPath();
				orgName = fullName.substring(fullName.lastIndexOf("/")+1);
			}
			if(map.get(orgName)==null){
				Map<String,String> map2 = new HashMap<String,String>();
				map.put(orgName, map2);
			}
			//统计总数
			String total = map.get(orgName).get("totalNum");
			if(total == null){
				map.get(orgName).put("totalNum", 1+"");
			}else{
				map.get(orgName).put("totalNum", Integer.parseInt(total)+1+"");
			}
			//统计合格数量				
			//String qualified = map.get()
			String qualified = map.get(orgName).get("qualified");
			if("0".equals(as.getCheckResult())){
				if(qualified == null){
					map.get(orgName).put("qualified", 1+"");
				}else{
					map.get(orgName).put("qualified", Integer.parseInt(qualified)+1+"");
				}
			}
			//统计规范数量
			String standard = map.get(orgName).get("standard");
			if("0".equals(as.getCheckResult())&&"0".equals(as.getStandardResult())){
				if(standard == null){
					map.get(orgName).put("standard", 1+"");
				}else{
					map.get(orgName).put("standard", Integer.parseInt(standard)+1+"");
				}
			}
		}
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 供电所工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> getPowerNumAndCheck(String unit,String beginTime,String endTime){
		
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<AnalysisStatistic> list = analysisStatisticMapper.getPowerNumAndCheck(unit,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		String orgName = "";
		String[] fullName = null;
		for(AnalysisStatistic as : list){
			fullName = as.getNameFullPath().split("/");
			orgName = fullName[3];
			if(map.get(orgName)==null){
				Map<String,String> map2 = new HashMap<String,String>();
				map.put(orgName, map2);
			}
			//统计总数
			String total = map.get(orgName).get("totalNum");
			if(total == null){
				map.get(orgName).put("totalNum", 1+"");
			}else{
				map.get(orgName).put("totalNum", Integer.parseInt(total)+1+"");
			}
			//统计合格数量				
			//String qualified = map.get()
			String qualified = map.get(orgName).get("qualified");
			if("0".equals(as.getCheckResult())){
				if(qualified == null){
					map.get(orgName).put("qualified", 1+"");
				}else{
					map.get(orgName).put("qualified", Integer.parseInt(qualified)+1+"");
				}
			}
			//统计规范数量
			String standard = map.get(orgName).get("standard");
			if("0".equals(as.getCheckResult())&&"0".equals(as.getStandardResult())){
				if(standard == null){
					map.get(orgName).put("standard", 1+"");
				}else{
					map.get(orgName).put("standard", Integer.parseInt(standard)+1+"");
				}
			}
		}
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 外委单位工作票数量以及合格率规范率
	 * @param unit
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> otherDeptNumAndCheck(String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<AnalysisStatistic> list = analysisStatisticMapper.otherDeptNumAndCheck(beginTime,endTime);
		List<ComboBoxItem> list2 = analysisStatisticMapper.getUnitComboxSql();
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		//List<String> arr = new ArrayList<String>();
		//SetList<String> arr = new SetList<String>();
		for(AnalysisStatistic tnn: list){
			String[] paths = tnn.getNameFullPath().split("/");
			for(int i=0;i<list2.size();i++){
				//arr.add(list2.get(i).getText());
				if(list2.get(i).getValue().equals(paths[2])){
					if(map.get(list2.get(i).getValue())==null){
						Map<String,String> map2 = new HashMap<String,String>();
						map.put(list2.get(i).getValue(), map2);
					}
					//统计总数
					String total = map.get(list2.get(i).getValue()).get("totalNum");
					if(total == null){
						map.get(list2.get(i).getValue()).put("totalNum", 1+"");
					}else{
						map.get(list2.get(i).getValue()).put("totalNum", Integer.parseInt(total)+1+"");
					}
					//统计合格数量				
					//String qualified = map.get()
					String qualified = map.get(list2.get(i).getValue()).get("qualified");
					if("0".equals(tnn.getCheckResult())){
						if(qualified == null){
							map.get(list2.get(i).getValue()).put("qualified", 1+"");
						}else{
							map.get(list2.get(i).getValue()).put("qualified", Integer.parseInt(qualified)+1+"");
						}
					}
					//统计规范数量
					String standard = map.get(list2.get(i).getValue()).get("standard");
					if("0".equals(tnn.getCheckResult())&&"0".equals(tnn.getStandardResult())){
						if(standard == null){
							map.get(list2.get(i).getValue()).put("standard", 1+"");
						}else{
							map.get(list2.get(i).getValue()).put("standard", Integer.parseInt(standard)+1+"");
						}
					}
				}
			}
		}
		//objMap.put("count", count);
		objMap.put("unit", map);
		return objMap;
	}
	
	public Map<String,Object> getOpenRoomNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		unit = unit.substring(2);
		List<Map<String,Object>> list = analysisStatisticMapper.getOpenRoomNumAndCheck(unit,beginTime,endTime);
		objMap.put("unit", list);
		return objMap;
	}
	
	public Map<String,Object> getLineNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = analysisStatisticMapper.getLineNumAndCheck(unit,beginTime,endTime);
		objMap.put("unit", list);
		return objMap;
	}
	
	public Map<String,Object> getStationNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = analysisStatisticMapper.getStationNumAndCheck(unit,beginTime,endTime);
		objMap.put("unit", list);
		return objMap;
	}
	
	public Map<String,Object> getMajorNumAndCheck(String unit,String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = analysisStatisticMapper.getMajorNumAndCheck(unit,beginTime,endTime);
		objMap.put("unit", list);
		return objMap;
	}
	
	public Map<String,Object> otherPersonNumAndCheck(String beginTime,String endTime){
		Map<String,Object> objMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = analysisStatisticMapper.otherPersonNumAndCheck(beginTime,endTime);
		objMap.put("unit", list);
		return objMap;
	}
	
	
	public  Map<String,Object> getDeviceNumAndCheck(List<String> list,String unit,String beginTime,String endTime){
		String regStr = "";
		String[] strings = new String[list.size()];
		list.toArray(strings);
		for(int i=0;i<list.size();i++){
			if(i == list.size()-1){
				regStr += list.get(i);
			}else{
				regStr += list.get(i)  + "|";
			}
		}
		Map<String,Object> objMap = new HashMap<String,Object>();
		//String count = ticketNumChartsMapper.getUnitNumCount(regStr, beginTime, endTime);
		List<AnalysisStatistic> list2= analysisStatisticMapper.getDeviceNumAndCheck(regStr,unit,beginTime,endTime);
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		for(AnalysisStatistic tnn: list2){
			//String[] paths = tnn.getNameFullPath().split("/");
			String workTask = tnn.getWorkTask();
			for(int i=0;i<strings.length;i++){
				if(workTask.contains(strings[i])){
					if(map.get(strings[i])==null){
						Map<String,String> map2 = new HashMap<String,String>();
						map.put(strings[i], map2);
					}
					//统计总数
					String total = map.get(strings[i]).get("totalNum");
					if(total == null){
						map.get(strings[i]).put("totalNum", 1+"");
					}else{
						map.get(strings[i]).put("totalNum", Integer.parseInt(total)+1+"");
					}
					//统计合格数量				
					//String qualified = map.get()
					String qualified = map.get(strings[i]).get("qualified");
					if("0".equals(tnn.getCheckResult())){
						if(qualified == null){
							map.get(strings[i]).put("qualified", 1+"");
						}else{
							map.get(strings[i]).put("qualified", Integer.parseInt(qualified)+1+"");
						}
					}
					//统计规范数量
					String standard = map.get(strings[i]).get("standard");
					if("0".equals(tnn.getCheckResult())&&"0".equals(tnn.getStandardResult())){
						if(standard == null){
							map.get(strings[i]).put("standard", 1+"");
						}else{
							map.get(strings[i]).put("standard", Integer.parseInt(standard)+1+"");
						}
					}
				}
			}
		}
		//objMap.put("count", count);
		objMap.put("unit", map);
		return objMap;
	}
	
	/**
	 * 插入数据到中间表
	 * @param beginTime
	 * @param endTime
	 */
	public void insertDataToTemp(String beginTime,String endTime){
		List<AnalysisStatistic> list = analysisStatisticMapper.selectDateByCondition(beginTime, endTime);
		List<AnalysisStatistic> list2 = new ArrayList<AnalysisStatistic>();
		String[] paths = null;
		String[] signPaths = null;
		String[] pmisPaths = null;
		AnalysisStatistic as2 = null;
		for(AnalysisStatistic as:list){
			as2 = new AnalysisStatistic();
			BeanUtils.copyProperties(as,as2);
			as2.setDataType("0");
			list2.add(as2);
			if(StringUtils.isBlank(as.getPmisFullPath())){
				if(StringUtils.isBlank(as.getSignFullPath())){
					continue;
				}else{
					if(as.getNameFullPath().contains("外协")){
						if(as.getSignFullPath().contains("外协")){
							continue;
						}else{
							as2 = new AnalysisStatistic();
							BeanUtils.copyProperties(as,as2);
							as2.setNameFullPath(as.getSignFullPath());
							signPaths = as.getSignFullPath().split("/"); 
							as2.setOrgName(signPaths[signPaths.length-1]);
							as2.setDataType("1");
							list2.add(as2);
							continue;
						}
					}else{
						paths = as.getNameFullPath().split("/");
						signPaths = as.getSignFullPath().split("/"); 
						if(as.getSignFullPath().contains("外协")){
							as2 = new AnalysisStatistic();
							BeanUtils.copyProperties(as,as2);
							as2.setNameFullPath(as.getSignFullPath());
							as2.setOrgName(signPaths[signPaths.length-1]);
							as2.setDataType("1");
							list2.add(as2);
							continue;
						}else{
							if(paths[2].equals(signPaths[2])){
								continue;
							}else{
								as2 = new AnalysisStatistic();
								BeanUtils.copyProperties(as,as2);
								as2.setNameFullPath(as.getSignFullPath());
								as2.setOrgName(signPaths[signPaths.length-1]);
								as2.setDataType("1");
								list2.add(as2);
								continue;
							}
						}
					}
				}
			}else{
				pmisPaths = as.getPmisFullPath().split("/"); 
				if(as.getNameFullPath().contains("外协")){
					if(!as.getPmisFullPath().contains("外协")){
						as2 = new AnalysisStatistic();
						BeanUtils.copyProperties(as,as2);
						as2.setNameFullPath(as.getPmisFullPath());
						as2.setOrgName(pmisPaths[pmisPaths.length-1]);
						as2.setDataType("1");
						list2.add(as2);
						if(StringUtils.isBlank(as.getSignFullPath())||as.getSignFullPath().contains("外协")){
							continue;
						}else{
							signPaths = as.getSignFullPath().split("/");
							if(!pmisPaths[2].equals(signPaths[2])){
								as2 = new AnalysisStatistic();
								BeanUtils.copyProperties(as,as2);
								as2.setNameFullPath(as.getSignFullPath());
								as2.setOrgName(signPaths[signPaths.length-1]);
								as2.setDataType("1");
								list2.add(as2);
								continue;
							}
						}
					}
					
				}else{
					paths = as.getNameFullPath().split("/");
					if(!as.getPmisFullPath().contains("外协")){//非外协、非外协
						if(!paths[2].equals(pmisPaths[2])){
							as2 = new AnalysisStatistic();
							BeanUtils.copyProperties(as,as2);
							as2.setNameFullPath(as.getPmisFullPath());
							as2.setOrgName(pmisPaths[pmisPaths.length-1]);
							as2.setDataType("1");
							list2.add(as2);
						}
						if(StringUtils.isNotBlank(as.getSignFullPath())){
							signPaths = as.getSignFullPath().split("/");
							if(as.getSignFullPath().contains("外协")){//非外协、非外协、外协
								as2 = new AnalysisStatistic();
								BeanUtils.copyProperties(as,as2);
								as2.setNameFullPath(as.getSignFullPath());
								as2.setOrgName(signPaths[signPaths.length-1]);
								as2.setDataType("1");
								list2.add(as2);
							}else{
								if(!signPaths[2].equals(pmisPaths[2])&&!signPaths[2].equals(paths[2])){
									as2 = new AnalysisStatistic();
									BeanUtils.copyProperties(as,as2);
									as2.setNameFullPath(as.getSignFullPath());
									as2.setOrgName(signPaths[signPaths.length-1]);
									as2.setDataType("1");
									list2.add(as2);
								}
							}
						}
					}else{//非外协，外协
						as2 = new AnalysisStatistic();
						BeanUtils.copyProperties(as,as2);
						as2.setNameFullPath(as.getPmisFullPath());
						as2.setOrgName(pmisPaths[pmisPaths.length-1]);
						as2.setDataType("1");
						list2.add(as2);
						if(StringUtils.isNotBlank(as.getSignFullPath())&&!as.getSignFullPath().contains("外协")){
								signPaths = as.getSignFullPath().split("/");
								if(!signPaths[2].equals(paths[2])){
									as2 = new AnalysisStatistic();
									BeanUtils.copyProperties(as,as2);
									as2.setNameFullPath(as.getSignFullPath());
									as2.setOrgName(signPaths[signPaths.length-1]);
									as2.setDataType("1");
									list2.add(as2);
								}
						}
					}
				}
			}
		}
		
		analysisStatisticMapper.insertDataToTemp(list2);
	}
	
	public void analysisFunctionName(String beginTime,String endTime){
		List<AnalysisStatistic> list = analysisStatisticMapper.analysisFunctionName(beginTime,endTime);
		List<FunctionLocationName> flnList = new ArrayList<FunctionLocationName>();
		for(AnalysisStatistic as:list){
			String fln = as.getFunctionLocationName();
			if(StringUtils.isNotBlank(fln)){
				/*
				 * ①将fln通过分隔符分割
				 */
				String flnStr = fln.replaceAll("[\\p{P}&&[^#＃（）()]]", ",");
				String[] str = flnStr.split(",");
				for(String name:str){
					if(StringUtils.isNotBlank(name)){
						String flnName = "";
						FunctionLocationName f = new FunctionLocationName();
						f.setNameFullPath(as.getNameFullPath());
						f.setTicketId(as.getTicketId());
						/*
						 * ②将分割的每一个字符截取（or( 前的内容
						 */
						if(name.indexOf("(") > 0){
							flnName = name.substring(0, name.indexOf("("));
						}else if(name.indexOf("（") > 0){
							flnName = name.substring(0, name.indexOf("（"));
						}else{
							flnName = name;
						}
						/*
						 * 将截取的字符通过正则判断属于开关房或者线路或其他类型
						 */
						flnName = flnName.toUpperCase();
						f.setFunctionLocationName(flnName);
						String regex = "^\\S{1,}(\\d{1,}|[0-9]+\\.[0-9]{1,})KV\\S{1,}[线|缆]";
						String regex2 = "^\\S{1,}(\\d{1,}|[0-9]+\\.[0-9]{1,})KV\\S{1,}[a-zA-Z][0-9]{1,}$";
						String regex3= "开关房";
						String regex4 = "^\\S{1,}(\\d{1,}|[0-9]+\\.[0-9]{1,})KV\\S{1,}[站]";
						Pattern pattern=Pattern.compile(regex);
						Matcher matcher=pattern.matcher(flnName);
						Pattern pattern2=Pattern.compile(regex2);
						Matcher matcher2=pattern2.matcher(flnName);
						Pattern pattern3=Pattern.compile(regex3);
						Matcher matcher3=pattern3.matcher(flnName);
						Pattern pattern4=Pattern.compile(regex4);
						Matcher matcher4=pattern4.matcher(flnName);
						if(matcher3.find()){
							f.setType("1");//开关房
						}else if(matcher.find()||matcher2.find()){
							f.setType("0");//线路
						}else if(matcher4.find()){
							f.setType("3");//变电站
						}else{
							f.setType("2");//未确定,之后可通过正则表达进行分类
						}
						flnList.add(f);
					}
				}
			}
		}
		analysisStatisticMapper.insertFunctionLocationName(flnList);
	}
	
	public long getDatePoor(String beginTime,String endTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(beginTime.endsWith(".0")){
			beginTime = beginTime.substring(0,endTime.indexOf(".0"));
		}
		if(endTime.endsWith(".0")){
			endTime = endTime.substring(0,endTime.indexOf(".0"));
		}
		long nd = 1000 * 24 * 60 * 60;//每天毫秒数
		long nh = 1000 * 60 * 60;//每小时毫秒数
		long nm = 1000 * 60;//每分钟毫秒数
		// 获得两个时间的毫秒时间差异
		long diff = sdf.parse(endTime).getTime() - sdf.parse(beginTime).getTime();
		long day = diff / nd;
		long hour = diff % nd / nh;
		long minute = diff % nd % nh / nm;
		long time = day * 60 *24 + hour * 60 + minute;
		return time;
	}
}
