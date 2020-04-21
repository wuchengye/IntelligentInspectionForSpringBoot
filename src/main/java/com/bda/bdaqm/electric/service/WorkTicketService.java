package com.bda.bdaqm.electric.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.WorkTicketMapper;
import com.bda.bdaqm.electric.model.WebServiceVo;
import com.bda.bdaqm.electric.model.WorkTicket;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.service.AbstractService;

@Service
public class WorkTicketService extends AbstractService<WorkTicket>{

	@Autowired
	private WorkTicketMapper workTicketMapper;
	
	private static String LIST_BREAKER = "站用|启备|发电机|曲折|厂变|起动|启动|厂用|STATCOM|高厂|机组|升压|变低虚开关";
	private static String LIST_DISCONNECTOR = "站用|启备|发电机|曲折|厂变|起动|启动|厂用|STATCOM|高厂|机组|升压|电容器|电抗器|公用|PT";
	private static String LIST_GDISCONNECTOR = "站用|启备|发电机|曲折|厂变|起动|启动|厂用|STATCOM|高厂|机组|升压|电容器|电抗器|公用";
	
	public static String STATION_FIRST_TYPE = "厂站第一种工作票";
	
	public List<ComboBoxItem> getTitles() {
		return workTicketMapper.getTitles();
	}
	
	public List<ComboBoxItem> getTaskByTitle(String title) {
		return workTicketMapper.getTaskByTitle(title);
	}
	
	public List<String> getTaskStrByTitle(String title) {
		return workTicketMapper.getTaskStrByTitle(title);
	}

	public List<String> getEquipNumber(String title,String task){
		return workTicketMapper.getEquipNumber(title, task);
	}
	
    public List<String> getBreaker(String title,String task, boolean flag){	
    	String listBreaker = "";
    	if(flag){
    		listBreaker = LIST_BREAKER;
    	}
    	return workTicketMapper.getBreaker(title, task, listBreaker);
    	
    }
	
    public List<String> getDisconnector(String title,String task, List<String> equipNum, boolean flag){
    	String listDisconnector = "";
    	if(flag){
    		listDisconnector = LIST_DISCONNECTOR;
    	}
    	return workTicketMapper.getDisconnector(title, task, equipNum, listDisconnector);
    }
    
    public List<String> getIsLanding(String title,String task, List<String> equipNum, boolean flag){
    	String listGdisconnector = "";
    	if(flag){
    		listGdisconnector = LIST_GDISCONNECTOR;
    	}
    	return workTicketMapper.getIsLanding(title, task, equipNum, listGdisconnector);
    }
    //以下三个只为开关服务
    public List<String> getBreakerOpen(String title,String task){	
    	return workTicketMapper.getBreakerOpen(title, task);
    	
    }
	
    public List<String> getDisconnectorOpen(String title,String task){
    	return workTicketMapper.getDisconnectorOpen(title, task);
    }
    
    public List<String> getIsLandingOpen(String title,String task){
    	return workTicketMapper.getIsLandingOpen(title, task);
    }
    //end
    
    public int saveInfo(WebServiceVo wv) {
    	return workTicketMapper.saveInfo(wv);
    }
    
    public WebServiceVo selectInfoById(String ticketId,String ticketType,String workTask,String station) {
    	return workTicketMapper.selectInfoById(ticketId,ticketType,workTask,station);
    }

    public List<Map<String,String>> getContent(String type){
    	if(type.indexOf("电容器")>-1 && type.indexOf("保护定检") == -1){
    		type = "电容器";
    	}else if(type.indexOf("PT")>-1 || type.indexOf("CT")>-1){
    		type = "PT";
    	}else if(type.indexOf("开关小车")>-1){
    		type = "开关小车";
    	}else if (type.indexOf("开关B修")>-1){
    		type = "开关B修";
    	}else if (type.indexOf("开关检修")>-1){
    		type = "开关检修";
    	}else if (type.indexOf("主变本体检修")>-1){
    		type = "主变本体检修";
    	}else if(type.indexOf("保护定检站用变")>-1){
    		type = "保护定检站用变";
		}else if(type.indexOf("保护定检电容器组")>-1){
			type = "保护定检电容器组";
		}else if(type.indexOf("保护定检母线分段")>-1){
			type = "保护定检母线分段";
		}else if(type.indexOf("保护定检开关")>-1){
			type = "保护定检开关";
		}
    	return workTicketMapper.getContent(type);
    }
	
	public List<String> getWorkType(String ticketType){
		return workTicketMapper.getWorkType(ticketType);
	}
	public String toUpper(String str) {
		str = str.toUpperCase();
		String newStr = str.replace("KV","kV");
		return newStr;
	}
	public String getSameChar(String s1,String s2){
		String str="";
		char c;
		char[] same=s1.toCharArray();
		int count=0,j;
		int i,k;
		for(i=0;i<s1.length();i++){
			c=s1.charAt(i);
			out:for(k=0;k<s2.length();k++){
				if(c==(s2.charAt(k))){
					for(j=0;j<count;j++){
						if(c==same[j])
							break out;
					}
					same[count]=c;
					count++;
					str+=c;
					break;
				}
			}
		}
		System.out.println(str);
		return str;
	}
	public String Trim(String str) {
		String newStr = str.replaceAll(" ", "");
		return newStr;
	}
	public String[] getList(String[] strList,String str,String workTask) {
		String newStr = "";
		if(strList==null&&!str.equals("）")&&!str.equals("（")) {
			if(str.equals("+")||str.equals("|")||str.equals("*")||str.equals("\\")) {
				newStr="\\"+str;
				strList = workTask.split(newStr);
			}else {
				strList = workTask.split(str);
			}
		}
		return strList;
	}
	
	public String swtchNumber(String workTask) {
		String str = "";
		if(workTask.contains("母联")) {
			str = workTask.substring(workTask.indexOf("母联"), workTask.indexOf("开关"));
		}else {
			String newstr = workTask.substring(0,workTask.indexOf("开关"));
			String[] array = newstr.split("[\\D]+");
			str = array[array.length-1];
		}
		return str;
		
	}

}
