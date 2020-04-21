package com.bda.bdaqm.electric.service.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.electric.model.Criterion15Vo;
import com.bda.bdaqm.electric.model.CriterionChildrenResult;
import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.bdaqm.electric.service.TicketCheckService;
import com.bda.bdaqm.electric.service.TicketVerificateService;
import com.bda.bdaqm.util.DateUtils;

@Service
@WebService(serviceName = "ticketCheck")
public class TicketCheckServiceImpl implements TicketCheckService {

	@Autowired
	private TicketVerificateService ticketVerificateService;

	@Override
	@WebMethod(operationName = "check")
	public List<TicketCheckResult> ticketCheck(@WebParam(name = "check") QueryParams check,
			@WebParam(name = "IdList") List<String> list) {
		List<Map<String, Object>> InfoList = null;
		
		if (list != null && !list.isEmpty()) {
			InfoList = ticketVerificateService.getTicketInfo(list);
		} else {
			InfoList = ticketVerificateService.getTicketsData(check);
		}
		int count = 0;
		String permiskId = "";
		List<Integer> removeList = new ArrayList<Integer>();
		
		//将需要检验的票的信息里的单位ID字段放进一个list里，在apply表搜索后
		//若存在停电关联计划记录，则存相应单位ID到map里
		Map<String,Map<String,Object>> existMap = new HashMap<String,Map<String,Object>>();
//		List<String> oidsList=new ArrayList<String>();
//		for(int i=0;i<InfoList.size();i++) {
//			oidsList.add(InfoList.get(i).get("id").toString());
//		}
//		existMap=ticketVerificateService.getIsConnect(oidsList);
		
		//同一张票因为工作间断栏时间不同，会出现多条数据的情况
		//只取最近的工作开始时间那条数据。
		for(int i=0;i<InfoList.size();i++) {
			if(permiskId.equals(InfoList.get(i).get("id").toString())) out:{
				Map<String,Object> map = InfoList.get(count);
				Map<String,Object> newMap = InfoList.get(i);
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if(toStr(newMap.get("gap_start_time")).equals("")) {
						removeList.add(i);
						break out;
					}
					if(toStr(map.get("gap_start_time")).equals("")) {
						removeList.add(count);
						count = i;
						break out;
					}
					long pertimeOne = sim.parse(toStr(map.get("gap_start_time"))).getTime();
//					System.out.println(toStr(map.get("gap_start_time")));
					long endTimeOne = sim.parse(toStr(newMap.get("gap_start_time"))).getTime();
//					System.out.println(toStr(newMap.get("gap_start_time")));
					if(endTimeOne>pertimeOne) {
						removeList.add(count);
						count = i;
					}else {
						removeList.add(i);
					}
					
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}else {
				count = i;
			}
			permiskId = InfoList.get(i).get("id").toString();
		}
		
		// 读文件
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = null;
			is = classloader.getResourceAsStream("separate.json");
			InputStreamReader inputStreamReader;
			inputStreamReader = new InputStreamReader(is, "UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			StringBuffer strbuffer = new StringBuffer();
			String attemp;
			while ((attemp = in.readLine()) != null) {
				strbuffer.append(attemp); // new String(str,"UTF-8")
			}
			in.close();

			
			List<Map<String, String>> tempList = new ArrayList<Map<String, String>>(); 
			List<TicketCheckResult> resultList = new ArrayList<TicketCheckResult>();//校验结果
			List<CriterionChildrenResult> CriterionList = new ArrayList<CriterionChildrenResult>();//判据子项结果
			List<String> locationList = ticketVerificateService.getLocation();
			List<Criterion15Vo> criter15List = new ArrayList<Criterion15Vo>();//判据15校验结果
			List<String> paramsList = new ArrayList<String>();

			
			String batchTime = DateUtils.getChinaTimeByPattern("yyyy-MM-dd HH:mm:ss");
			String userId = "";
			Subject subject = SecurityUtils.getSubject();
			User currentUser = (User) subject.getPrincipal();
			userId = currentUser.getUserId();
			int countOfBatch = InfoList.size() - removeList.size();
			long t1 = System.currentTimeMillis();
			for (int i = 0; i < InfoList.size(); i++) {
				if(removeList.contains(i)) {
					continue;
				}
				Map<String, Object> map = InfoList.get(i);
				TicketCheckResult tcr = new TicketCheckResult();

				// 合格性校验
				List<Map<String, String>> oneList = ticketVerificateService.one(map,existMap);
				tempList.addAll(oneList);
				List<Map<String, String>> twoList  = ticketVerificateService.two(map,existMap);
				tempList.addAll(twoList);
				List<Map<String, String>> threeList = ticketVerificateService.three(map, strbuffer.toString());
				tempList.addAll(threeList);
				// int four = ticketVerificateService.four(map);
				int five = ticketVerificateService.five(map);
				List<Map<String, String>> sixList = ticketVerificateService.six(map,existMap);
				tempList.addAll(sixList);
				List<Map<String, String>> sevenList = ticketVerificateService.seven(map);
				tempList.addAll(sevenList);
				List<Map<String, String>> eightList = ticketVerificateService.eight(map);
				List<Map<String, String>> nineList = ticketVerificateService.nine(map);
				List<Map<String, String>> tenList = ticketVerificateService.ten(map);
				List<Map<String, String>> elevenList = ticketVerificateService.eleven(map);
				// int twelve = ticketVerificateService.twelve(tc);
				List<Map<String, String>> thirteenList = ticketVerificateService.thirteen(map);
				List<Map<String,String>> fourteenList=ticketVerificateService.fourteen(map);
				tempList.addAll(eightList);
				tempList.addAll(nineList);
				tempList.addAll(tenList);
				tempList.addAll(elevenList);
				tempList.addAll(thirteenList);
				tempList.addAll(fourteenList);
				int fifteen = ticketVerificateService.fifteen(map);
				int sixteen = ticketVerificateService.sixteen(map);
				// int seventeen = ticketVerificateService.seventeen(tc);

				int one = Integer.valueOf(oneList.get(0).get("result"));
				int two = Integer.valueOf(twoList.get(0).get("result"));
				int three = Integer.valueOf(threeList.get(0).get("result"));
				int six = Integer.valueOf(sixList.get(0).get("result"));
				int seven = Integer.valueOf(sevenList.get(0).get("result"));
				int eight = Integer.valueOf(eightList.get(0).get("result"));
				int nine = Integer.valueOf(nineList.get(0).get("result"));
				int ten = Integer.valueOf(tenList.get(0).get("result"));
				int eleven = Integer.valueOf(elevenList.get(0).get("result"));
				int thirteen = Integer.valueOf(thirteenList.get(0).get("result"));
				int fourteen = Integer.valueOf(fourteenList.get(0).get("result"));

				int result = 0;
				if (one == 1 || two == 1 || three == 1 || five == 1 || six == 1 || seven == 1 || eight == 1 || nine == 1
						|| ten == 1 || eleven == 1 || thirteen == 1 || fourteen == 1 || fifteen == 1 || sixteen == 1) {
					result = 1;
				} else if (one != 1 && two != 1 && three != 1 && five != 1 && six != 1 && seven != 1 && eight != 1
						&& nine != 1 && ten != 1 && eleven != 1 && thirteen != 1 && fourteen != 1 && fifteen != 1
						&& sixteen != 1) {
					if (one == 2 || two == 2 || three == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
							|| nine == 2 || ten == 2 || eleven == 2 || thirteen == 2 || fourteen == 2 || fifteen == 2
							|| sixteen == 2) {
						result = 1;
					}

				}
				tcr.setTicketId(map.get("id").toString());
				tcr.setTicketType(changeType(map.get("ticket_type")));
				tcr.setClasses(toStr(map.get("department_oname")));
				tcr.setTicketFinalTime(toStr(map.get("ticket_end_time")));
				tcr.setWorkPrincipal(toStr(map.get("pri_name")));
				tcr.setTicketSigner(toStr(map.get("sign_name")));
				tcr.setLicensor(toStr(map.get("permis_name")));
				tcr.setCheckResult(String.valueOf(result));
				tcr.setMisuseTicket(String.valueOf(one));
				tcr.setBeyondPlan(String.valueOf(two));
				tcr.setKeywordError(String.valueOf(three));
				tcr.setWorkMemberCount(String.valueOf(five));
				tcr.setFillinTaskError(String.valueOf(six));
				tcr.setFillinSafeError(String.valueOf(seven));
				tcr.setDoubleIssueError(String.valueOf(eight));
				tcr.setEmptyPermissTime(String.valueOf(nine));
				tcr.setHandleChange(String.valueOf(ten));
				tcr.setHandleDelay(String.valueOf(eleven));
				tcr.setFinalContentError(String.valueOf(thirteen));
				tcr.setLicensorNoSign(String.valueOf(fourteen));
				tcr.setKeepMultiple(String.valueOf(fifteen));
				tcr.setSignError(String.valueOf(sixteen));
				tcr.setTicketNo(toStr(map.get("ticket_no")));
				int standardResult = -1;
				// 规范性校验
				if(result==0) {
					int oneStandard = ticketVerificateService.oneStandard(map);
					List<Map<String, String>> twoStandardList = ticketVerificateService.twoStandard(map, locationList);
					tempList.addAll(twoStandardList);
					int twoStandard = Integer.valueOf(twoStandardList.get(0).get("result"));
					if (oneStandard == 1 || twoStandard == 1) {
						standardResult = 1;
					} else if (twoStandard == 2 && oneStandard == 0) {
						standardResult = 1;
					}else {
						standardResult = 0;
					}
					tcr.setNoSave(String.valueOf(oneStandard));
					tcr.setNonstandardWord(String.valueOf(twoStandard));
				}else {
					tcr.setNoSave("-1");
					tcr.setNonstandardWord("-1");
				}

				tcr.setStandardResult(String.valueOf(standardResult));

				tcr.setBatchTime(batchTime);
				Criterion15Vo cvo = new Criterion15Vo();
				cvo.setTicketId(map.get("id").toString());
				cvo.setTicketType(toStr(map.get("ticket_type")));
				cvo.setUserName(toStr(map.get("pri_name")));
				cvo.setPermissionTime(toStr(map.get("permission_time")));
				cvo.setWorkEndTime(toStr(map.get("work_end_time")));
				criter15List.add(cvo);
				paramsList.add(toStr(map.get("work_principal_uid")));
				resultList.add(tcr);
				
			}
			long t2 = System.currentTimeMillis();
			System.out.println("校验所需时间："+(t2-t1));
			List<Criterion15Vo> criter15ListForSql = ticketVerificateService.selectBySql(paramsList);	
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < criter15List.size(); i++) {
				Criterion15Vo c15 = criter15List.get(i);
				if(c15.getTicketType()==null||c15.getTicketType().equals("")||
						c15.getPermissionTime()==null||c15.getPermissionTime().equals("")
						||c15.getWorkEndTime()==null||c15.getWorkEndTime().equals("")
						||c15.getUserName()==null||c15.getUserName().equals("")) {
					continue;
				}
				String permisstionTime = c15.getPermissionTime();
				String workEndTime = c15.getWorkEndTime();
				if (c15.getTicketType().equals("11") || c15.getTicketType().equals("12")
						|| c15.getTicketType().equals("13") || c15.getTicketType().equals("21")
						|| c15.getTicketType().equals("22") || c15.getTicketType().equals("43")
						|| c15.getTicketType().equals("44") || c15.getTicketType().equals("51")) {
					try {
						long pertime = sim.parse(permisstionTime).getTime();
						long endTime = sim.parse(workEndTime).getTime();

						List<String> strList = new ArrayList<String>();
						List<Criterion15Vo> attempList = new ArrayList<Criterion15Vo>();
						strList.add(criter15List.get(i).getUserName());
						attempList = criter15ListForSql.stream().filter((Criterion15Vo s) -> strList.contains(s.getUserName()))
								.collect(Collectors.toList());
						for (int j = 0; j < attempList.size(); j++) {
							Criterion15Vo cv = attempList.get(j);
							if(cv.getPermissionTime()==null||cv.getPermissionTime().equals("")
								||cv.getWorkEndTime()==null||cv.getWorkEndTime().equals("")) {
								continue;
							}
							String permisstionTimeOne = cv.getPermissionTime();
							String workEndTimeOne = cv.getWorkEndTime();
							long pertimeOne = sim.parse(permisstionTimeOne).getTime();
							long endTimeOne = sim.parse(workEndTimeOne).getTime();
							if ((pertime > pertimeOne && pertime < endTimeOne)
									|| (endTime > pertimeOne && endTime < endTimeOne)) {
								resultList.get(i).setKeepMultiple("1");
								resultList.get(i).setCheckResult("1");
								resultList.get(i).setStandardResult("-1");
								resultList.get(i).setNoSave("-1");
								resultList.get(i).setNonstandardWord("-1");
								Map<String,String> map = new HashMap<String,String>();
								map.put("ticket_id",c15.getTicketId());
								map.put("criterion", "合格判据15");
								map.put("criterion_children", "H15_sub1");
								map.put("description", "不满足合格判据15:在工作许可至终结时段搜索该负责人持有的所有票，记录超过一条;");
								map.put("field", "work_principal_uid@permission_time@work_end_time");
								tempList.add(map);
								break;
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if (resultList != null && !resultList.isEmpty()) {
				ticketVerificateService.saveBatch(batchTime, userId, countOfBatch);
				ticketVerificateService.insertResult(resultList);
				
			}
			for (int i = 0; i < tempList.size(); i++) {
				CriterionChildrenResult ccr = new CriterionChildrenResult();
				Map<String, String> resultMap = tempList.get(i);
				if (resultMap.get("ticket_id") != null) {
					ccr.setTicketId(resultMap.get("ticket_id"));
					ccr.setCriterion(resultMap.get("criterion"));
					ccr.setCriterionChildren(resultMap.get("criterion_children"));
					ccr.setDescription(resultMap.get("description"));
					ccr.setField(resultMap.get("field"));
					ccr.setBatchTime(batchTime);
					CriterionList.add(ccr);
				}

			}
			if (CriterionList != null && !CriterionList.isEmpty()) {
				//ticketVerificateService.deleteResultChildren(CriterionList);
				ticketVerificateService.insertResultChildren(CriterionList);
			}
			return resultList;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	public String changeType(Object obj) {
		String str = "";
		if (obj != null) {
			str = obj.toString();
		} else {
			return "";
		}
		switch (str) {
		case "11":
			str = "厂站第一种工作票";
			break;
		case "12":
			str = "厂站第二种工作票";
			break;
		case "13":
			str = "厂站第三种工作票";
			break;
		case "21":
			str = "线路第一种工作票";
			break;
		case "22":
			str = "线路第二种工作票";
			break;
		case "31":
			str = "一级动火工作票";
			break;
		case "32":
			str = "二级动火工作票";
			break;
		case "41":
			str = "配电第一种工作票";
			break;
		case "42":
			str = "配电第二种工作票";
			break;
		case "43":
			str = "带电作业工作票";
			break;
		case "44":
			str = "低压配电网工作票";
			break;
		case "51":
			str = "紧急抢修工作票";
			break;
		case "61":
			str = "安全技术交底单";
			break;
		case "71":
			str = "二次措施单";
			break;
		case "81":
			str = "书面布置和记录";
			break;
		case "91":
			str = "现场勘察记录";
			break;
		default:
			break;
		}

		return str;

	}

}
