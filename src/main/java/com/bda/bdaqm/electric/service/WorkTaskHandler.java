package com.bda.bdaqm.electric.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;


public class WorkTaskHandler {
	
	@Autowired
	protected HttpServletRequest request;
	
	public static String ERROR = "error";

	/**
	 * 返回作业类型
	 * @param list
	 * @param task
	 * @return
	 */
	public static String checkWorkType(List<String> list,String task){
		for (String string : list) {
			if(task.contains(string)){
				return string;
			}
		}
		return ERROR;
	}
	
	public static String generateCondition(String task, String type){
		
		System.out.println("生成查询条件=>传入的参数为：" + task + ", " + type);
		String expr = task.replace(type, "");
		
		if("电容器组检修".equals(type) ){
			if(task.contains("变")){
				Pattern pattern1 = Pattern.compile("#\\d+");//  #1234
				Matcher matcher1 = pattern1.matcher(expr);
				expr = "";
				while(matcher1.find()){
					expr += matcher1.group() + "%";  // #4%#3%
				}
				expr = "%" + expr.substring(1);      // %4%#3%
			}else{
				expr = "%" + expr + "%";
			}
			
		}else if("开关投产前试验".equals(type) || "开关B修".equals(type)||"开关防拒动检查".equals(type)||"开关检修".equals(type)){
//			if(expr.indexOf('(') > -1){
//				expr = expr.substring(0, expr.indexOf('(') );
//			}
//			expr = "%" + expr + "%";
			
//		}else if( "开关检修".equals(type) ){
//			if(expr.indexOf("(") > -1){
//				expr = "%" + expr.replaceAll("(?:\\()\\w+(?:\\))", "") + "%";
//			}else{
//				expr = "%" + expr.replaceAll("\\d+$", "") + "%";
//			}
			String[] taskList = task.split("开关");
			String str = getUseStr(taskList[0]);
			if(!str.equals("none")) {
				expr = str;
			}else {
				String tempStr = getTempList(taskList[0]);
				if(tempStr.equals("none")) {
					expr = "none";
				}else {
					expr = tempStr;
				}
			}
		}else if( "PT检修".equals(type) ){
			//expr = "%" + expr.replaceAll("\\dM母线", "") + "%";
			int one = task.indexOf('#');
			int two = task.indexOf("PT");
			String newtask01 = task.substring(0,task.indexOf("kV"));
			String newtask = task.substring(one+1, two);
			expr = "%"+newtask01+"kV%"+newtask+"PT%";
		}else if( "CT检修".equals(type)){
			expr = "%" + expr + "%";
			
//		}else if( "开关防拒动检查".equals(type) ){
//			expr = "%" + expr.replaceAll("\\d+$", "") + "%";
		}else if("主变本体检修".equals(type)){
			expr = expr + "%";
		}else if("保护定检".equals(type) ){
			if(task.contains("站用变")){
				expr = "%" + expr + "%";
			}else if(task.contains("电容器组") || task.contains("母线分段")){
				expr = expr.replace("组", "") + "%";
			}else if(task.contains("开关")){
				expr = expr.replace("开关", "");
				int last = expr.lastIndexOf('#');
				Pattern pattern = Pattern.compile("\\d+$");//  311
				Matcher matcher = pattern.matcher(expr);
				String newExpr = "";
				if(matcher.find()){
					newExpr = matcher.group(0);
					newExpr = "%" + expr.substring(last, expr.indexOf(newExpr)) + "%" + newExpr + "%";
				}
				expr = newExpr;
			}
		}
		
		System.out.println("生成查询条件=>返回的结果为：" + expr);
		return expr;
		
	}
	
	/**
	 * 读取json文件并且转换成字符串
	 * @param filePath文件的路径
	 * @return
	 * @throws IOException 
	 */
	public static String readJsonData(String pactFile) throws Exception {
		
		// 读取文件数据
		//System.out.println("读取文件数据util");
		
		StringBuffer strbuffer = new StringBuffer();
		File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
		if (!myFile.exists()) {
			System.err.println("Can't Find " + pactFile);
		}
		try {
			FileInputStream fis = new FileInputStream(pactFile);
			InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
			BufferedReader in  = new BufferedReader(inputStreamReader);
			
			String str;
			while ((str = in.readLine()) != null) {
				strbuffer.append(str);  //new String(str,"UTF-8")
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		//System.out.println("读取文件结束util");
		
		return strbuffer.toString();
	}
	public static String getTempList(String workTask) {
		Pattern p = Pattern.compile("(\\d+)");
	    Matcher m = p.matcher(workTask);
	    String result = "";
	    if (m.find()) {
	        Map<Integer, String> map = new TreeMap<Integer, String>();
	        Pattern p3 = Pattern.compile("(\\d+)");
	        m = p3.matcher(workTask);
	        while (m.find()) {
	            result = m.group(1) == null ? "" : m.group(1);
	            int i = workTask.indexOf(result);
	            if(i!=0) {
		            if (String.valueOf(workTask.charAt(i - 1)).equals(".")) {
		            	workTask = workTask.substring(0, i - 1) + workTask.substring(i + result.length());
		                continue;
		            }
		            String s = workTask.substring(i, i + result.length());
		            map.put(i, s);
		            workTask = workTask.substring(0, i) + workTask.substring(i + result.length());
	            }
	        }
	        result = "";
	        for (Map.Entry<Integer, String> e : map.entrySet()) {
	            result += e.getValue() + ",";
	        }
	        if(result.length()==0) {
	        	return "none";
	        }
	        result = result.substring(0, result.length()-1);
	    } else {
	        result = "";
	    }
	    String[] reList = result.split(",");
	    if(reList.length==0||(reList.length==1&&reList[0].equals(""))) {
	    	return "none";
	    }
		return reList[reList.length-1];
	}
	public static String getUseStr(String taskList) {
		int one = taskList.indexOf('(');
		int two = taskList.indexOf(')');
		if(one==-1&&two==-1) {
			return "none";
		}
		String newtask = taskList.substring(one+1, two);
		if(newtask.equals("")) {
			return "nothing";
		}
		return newtask;
	}
	
}
