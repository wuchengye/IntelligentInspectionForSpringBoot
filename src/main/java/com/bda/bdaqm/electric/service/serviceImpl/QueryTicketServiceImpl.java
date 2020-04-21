package com.bda.bdaqm.electric.service.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bda.bdaqm.electric.mapper.WorkTicketMapper;
import com.bda.bdaqm.electric.model.RespondMsg;
import com.bda.bdaqm.electric.model.WebServiceVo;
import com.bda.bdaqm.electric.service.QueryTicketService;
import com.bda.bdaqm.electric.service.WorkTaskHandler;
import com.bda.bdaqm.electric.service.WorkTicketService;





@Service
@WebService(targetNamespace = "http://el.soa.csg.cn/",serviceName="queryTicket")
public class QueryTicketServiceImpl implements QueryTicketService{

	private final static Logger LOGGER = LoggerFactory.getLogger(QueryTicketServiceImpl.class);
	@Autowired
	private WorkTicketMapper workTicketMapper;
	@Autowired
	private WorkTicketService workTicketService;

	
	
	@Override
	@WebMethod(operationName="queryTicketRequest")
	public RespondMsg queryTicket(@WebParam(name="ticketID",targetNamespace="http://el.soa.csg.cn/")String ticketID,@WebParam(name="ticketType",targetNamespace="http://el.soa.csg.cn/")String ticketType,
			@WebParam(name="workTask",targetNamespace="http://el.soa.csg.cn/")String workTask,@WebParam(name="station",targetNamespace="http://el.soa.csg.cn/")String stationNew) {
		try{
			LOGGER.info("ticketID======>>"+ticketID);
			LOGGER.info("ticketType======>>"+ticketType);
			LOGGER.info("workTask======>>"+workTask);
			LOGGER.info("station======>>"+stationNew);
			workTask = workTicketService.Trim(workTask);
			//String oldStation = getChinese(stationNew);
			String realStr = getChinese(stationNew);
	        int tt = realStr.indexOf("站");
	        String station = realStr.substring(0, tt);
			String recodeTask = workTask;
			WebServiceVo wsv = workTicketService.selectInfoById(ticketID,ticketType,workTask,station);
			if(wsv!=null) {
				WebServiceVo wvv = new WebServiceVo();
				wvv.setTicketId(wsv.getTicketId());
				wvv.setTicketType(wsv.getTicketType());
				wvv.setPullBreaker(wsv.getPullBreaker());
				wvv.setPullSwitch(wsv.getPullSwitch());
				wvv.setDcpowerLowpCircle(wsv.getDcpowerLowpCircle());
				wvv.setSwitchEarthwireInsulation(wsv.getSwitchEarthwireInsulation());
				wvv.setBillboard(wsv.getBillboard());
				wvv.setSafeotherCare(wsv.getSafeotherCare());
				wvv.setHighpDeviceState(wsv.getHighpDeviceState());
				wvv.setPowerCircleState(wsv.getPowerCircleState());
				wvv.setRequiredSafty(wsv.getRequiredSafty());
				wvv.setPullBreakerSwitch(wsv.getPullBreakerSwitch());
				wvv.setCloseSwitchEarthwire(wsv.getCloseSwitchEarthwire());
				wvv.setSaftyAndCare(wsv.getSaftyAndCare());
				wvv.setRunningSafty(wsv.getRunningSafty());
				wvv.setRepareSafty(wsv.getRepareSafty());
				return new RespondMsg("OK", "", wvv);
			}
			workTask = workTicketService.Trim(workTask);
			String[] strList = null;
			ClassLoader classloader =Thread.currentThread().getContextClassLoader();
			//URL templatePath =classloader.getResource("wrong_words.json");
			//String templatePath = this.getClass().getResource("wrong_words.json").getPath();
			//String templatePath = getClass().getClassLoader().getResource("wrong_words.json");
			InputStream is = null;
			is = classloader.getResourceAsStream("wrong_words.json");
			InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
			BufferedReader in  = new BufferedReader(inputStreamReader);
			StringBuffer strbuffer = new StringBuffer();
			String str;
			while ((str = in.readLine()) != null) {
				strbuffer.append(str);  //new String(str,"UTF-8")
			}
			in.close();
			
			
			//String s = WorkTaskHandler.readJsonData(templatePath.toString());
			JSONObject js = (JSONObject) JSONObject.parse(strbuffer.toString());
			for(Entry<String, Object> entry : js.entrySet()) {
				Pattern pattern = Pattern.compile(entry.getKey());
				Matcher matcher = pattern.matcher(workTask);
				while(matcher.find()){//数字结尾
					if(matcher.group()!=null&&!matcher.group().equals("")) {
						strList = workTicketService.getList(strList, matcher.group(), workTask);
//						String str = "";
//						if(strList==null&&!matcher.group().equals("）")&&!matcher.group().equals("（")) {
//							if(matcher.group().equals("+")||matcher.group().equals("|")||matcher.group().equals("*")||matcher.group().equals("\\")) {
//								str="\\"+matcher.group();
//								strList = workTask.split(str);
//							}else {
//								strList = workTask.split(matcher.group());
//							}
//						}
						
					}	
					workTask = workTask.replace(matcher.group(), (String)entry.getValue());
				}
			}
			
			station = workTicketService.toUpper(station);
			List<String> titles = workTicketMapper.getAllTitle();
			boolean flag = titles.contains(station);
			//sif(titles.contains(station)||titles.contains(oldStation))
			if(!flag) {
				return  new RespondMsg("Failed","厂站名称有误！",null);
			}
			//获取作业类型
			List<String> workTypes = this.getWorkType(ticketType);
			if(workTypes.isEmpty()) {
				return  new RespondMsg("Failed","工作票类型有误！",null);
			}
			if(!ticketType.equals("厂站第一种工作票")) {
				return  new RespondMsg("Failed","此票种正在开发中！",null);
			}
			String workType = WorkTaskHandler.checkWorkType(workTypes, workTask);
			if(strList!=null&&strList.length==2) {
				String pate = workTicketService.getSameChar(strList[0], strList[1]);
				String newStr = workTask.replaceFirst(pate, "");
				workTask = newStr;
			}
			if(WorkTaskHandler.ERROR.equals(workType)){
				return  new RespondMsg("Failed","工作任务未能正确识别，请检查确认！",null);
			}
			
			String expr = WorkTaskHandler.generateCondition(workTask,workType);
			
			expr = expr.trim();
			String temp = "";
			if(workTask.indexOf("CT") > -1){
				temp += "CT";
			}
			workTask = workTask.replace("检修", "").replace("CT", "");
			String brk = "";
			String isl = "";
			String dis = "";
			List<String> discnts = null;
			List<String> breakers = null;
			List<String> isLanding = null;
			boolean flagType = compare(workType);
			if(flagType) {
				discnts = workTicketService.getDisconnectorOpen(station, expr);
				breakers = workTicketService.getBreakerOpen(station, expr);
				isLanding = workTicketService.getIsLandingOpen(station, expr);
				if(discnts.isEmpty()&&breakers.isEmpty()&&isLanding.isEmpty()) {
					return  new RespondMsg("Failed","工作任务中无开关编号！",null);
				}else if(breakers.size()>1||breakers.isEmpty()) {
					return  new RespondMsg("Failed","工作任务中无开关编号！",null);
				}
			}else {
				if("主变本体检修".equals(workType)){
					flag = true;
				}else{
					flag = false;
				}
				
				List<String> equipNum = workTicketService.getEquipNumber(station, expr);
				discnts = workTicketService.getDisconnector(station, expr, equipNum, flag);
				breakers = workTicketService.getBreaker(station, expr, flag);
				isLanding = workTicketService.getIsLanding(station, expr, equipNum, flag);
			}
			if(breakers.size() > 0){
				for (int i = 0; i < breakers.size(); i++) {
					if(brk.length() > 0){
						brk += "、";
					}
					brk += breakers.get(i);
				}
			}else{
				brk = "无";
			}
			
			if(discnts.size() > 0){
				for (int i = 0; i < discnts.size(); i++) {
					if(dis.length() > 0){
						dis += "、";
					}
					dis += discnts.get(i);
				}
			}else{
				dis = "无";
			}
			
			
			if(isLanding.size() > 0){
				for (int i = 0; i < isLanding.size(); i++) {
					if(isl.length() > 0){
						isl += "、";
					}
					isl += isLanding.get(i);
				}
			}else{
				isl = "无";
			}
			
			if("开关投产前试验".equals(workType) && dis.indexOf("DZ") > -1){
				temp = "开关小车";
			}else if("开关投产前试验".equals(workType) && dis.indexOf("DZ") == -1){
				temp = "开关B修";
			}else if("开关检修".equals(workType) || "开关防拒动检查".equals(workType)){
				temp = "开关检修";
			}else if("主变本体检修".equals(workType)){
				temp = "主变本体检修";
			}else if ("保护定检".equals(workType)){
				if(workTask.contains("站用变")){
					temp = "保护定检站用变";
				}else if(workTask.contains("电容器组")){
					temp = "保护定检电容器组";
					workTask = workTask.replace(workType, "");
				}else if(workTask.contains("母线分段")){
					temp = "保护定检母线分段";
				}else if(workTask.contains("开关")){
					temp = "保护定检开关";
					workTask = workTask.replaceAll("\\d+开关", "").replace(workType, "");
				}
				
			}
			WebServiceVo wv = new WebServiceVo();
			List<Map<String,String>> content = workTicketService.getContent(workTask+temp); 
			for (Map<String, String> map : content) {
				wv.setDcpowerLowpCircle(formateStr(map.get("CONTENT_1"),workTask));
				wv.setSwitchEarthwireInsulation(isl + formateStr(map.get("CONTENT_2"),workTask));
				wv.setBillboard(formateStr(map.get("NEED_SET_SIGN"),workTask));
			}
			if(content == null || content.size() == 0){//如果没有模板
				return  new RespondMsg("Failed","工作任务未能正确识别，请检查确认。",null);
			}
			if(brk.equals("无")&&dis.equals("无")&&wv.getSwitchEarthwireInsulation().contains("无")) {
				return new RespondMsg("Failed", "查不到相关安全措施，请检查工作任务填写是否正确。", null);
			}
			wv.setPullBreaker(brk);
			
			String[] disList = dis.split("、");
//			if(disList!=null&& disList[0].indexOf("DZ") > -1) {
//				dis = "将" + brk + "开关小车拉至试验位置";
//				
//			}
			String newdis = "";
			if(disList!=null&& disList.length >0) {
				String _t = "";
				for(int i=0;i<disList.length;i++) {
					if(disList[i].indexOf("DZ")>-1) {
						disList[i] = disList[i].replace("DZ","");
						if(brk.indexOf(disList[i]) > -1){
							_t = _t.length() > 0 ? _t + "、" + disList[i] : _t + disList[i];
						}
						if(i < disList.length -1){
							continue;
						}
					}
					if(i ==  disList.length -1 && _t.length() > 0 ){
						newdis=newdis.length() > 0 ? newdis + ("、将" + _t + "开关小车拉至试验位置") : newdis + ("将" + _t + "开关小车拉至试验位置") ;
					}else{
						newdis=newdis.length() > 0 ? newdis + ("、" + disList[i]) : newdis + disList[i] ;
					}
				}
				
			}
		
			wv.setPullSwitch(newdis);
			wv.setTicketType(ticketType);
			wv.setTicketId(ticketID);
			wv.setSafeotherCare("无");
			wv.setStation(station);
			wv.setWorkTask(recodeTask);
			wv.setHighpDeviceState("");
			wv.setPowerCircleState("");
			wv.setRequiredSafty("");
			wv.setPullBreakerSwitch("");
			wv.setCloseSwitchEarthwire("");
			wv.setSaftyAndCare("");
			wv.setRunningSafty("");
			wv.setRepareSafty("");
			int t = workTicketService.saveInfo(wv);
			if(t>0) {
				LOGGER.info("成功插入"+t+"条");
				wv.setWorkTask("");
				wv.setStation("");
			}
			WebServiceVo mvv = new WebServiceVo();
			mvv.setTicketId(wv.getTicketId());
			mvv.setTicketType(wv.getTicketType());
			mvv.setPullBreaker(wv.getPullBreaker());
			mvv.setPullSwitch(wv.getPullSwitch());
			mvv.setDcpowerLowpCircle(wv.getDcpowerLowpCircle());
			mvv.setSwitchEarthwireInsulation(wv.getSwitchEarthwireInsulation());
			mvv.setBillboard(wv.getBillboard());
			mvv.setSafeotherCare(wv.getSafeotherCare());
			mvv.setHighpDeviceState(wv.getHighpDeviceState());
			mvv.setPowerCircleState(wv.getPowerCircleState());
			mvv.setRequiredSafty(wv.getRequiredSafty());
			mvv.setPullBreakerSwitch(wv.getPullBreakerSwitch());
			mvv.setCloseSwitchEarthwire(wv.getCloseSwitchEarthwire());
			mvv.setSaftyAndCare(wv.getSaftyAndCare());
			mvv.setRunningSafty(wv.getRunningSafty());
			mvv.setRepareSafty(wv.getRepareSafty());
			
			
			return new RespondMsg("OK", "", mvv);
		}catch(Exception e) {
			e.printStackTrace();
			RespondMsg rm = new RespondMsg("Failed",e.getMessage(),null);
			return rm;
		}
		
	}

   
	private List<String> getWorkType(String ticketType){
		return workTicketService.getWorkType(ticketType);
	}
	private String formateStr(String str, String task){
		if(StringUtils.isNotBlank(str)){
			return str.replace("source", task);
		}
		return "";
	}
	private boolean compare(String workType) {
		boolean flag = true;
		if(!workType.equals("开关投产前试验")&&!workType.equals("开关B修")&&!workType.equals("开关检修")&&!workType.equals("开关防拒动检查")) {
			flag = false;
		}
		return flag;
	}
	private String getChinese(String str) {
		Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
        Matcher matcher = pattern.matcher(str);
        String newStr = matcher.replaceAll("");
		return newStr;
	}
//	private String getRealStr(String str) {
//		StringBuffer sb = new StringBuffer();
//        int len = str.length();
//        for (int i = 0; i < len; i++) {
//            char c = str.charAt(i);
//            if (str.indexOf(c) ==str.lastIndexOf(c)) {
//                sb.append(c);
//            } else {
//                int fristposition=str.indexOf(c);
//                if(fristposition==i){
//                    sb.append(c);
//                }
//            }
//        }
//        return sb.toString();
//		
//	}
}
