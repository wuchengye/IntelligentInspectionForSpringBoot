package com.bda.bdaqm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CustQualityCheckUtils {

	//运算符
	private static List<String> operator = new ArrayList<>();
	//运算符优先级
	private static Map<String, Integer> priority = new HashMap<>();
	//程序使用的字符
	private static List<String> specialChar = new ArrayList<>();
	//初始化
	static {
		operator.add("!");
		operator.add("&");
		operator.add("|");
			
		priority.put("!", 2);
		priority.put("&", 1);
		priority.put("|", 1);
		priority.put("", 0);
			
		specialChar.add("!");
		specialChar.add("&");
		specialChar.add("|");
		specialChar.add("(");
		specialChar.add(")");
	}
	
	/**
	 * 格式化表达式
	 * 1、全角转半角；2、消除空格
	 * @param expression
	 * @return
	 * @throws Exception 
	 */
	public static String format(String expression) throws Exception {
		int spaceCBD = 32; //半角空格的ASCII码
		int spaceDBC = 12288; //全角空格的ASCII码
		int dValue = 65248; //一般全角字符和半角字符的ASCII码差值
		char[] charArray = {'(', ')', '#', '|', '&', '!'};//需要进行全-半角转换的字符
		
		//全角转半角
		if(StringUtils.isBlank(expression)){
			throw new Exception("表达式内容为空");
		}
		StringBuilder sb = new StringBuilder(expression);
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < sb.length(); i++) {
			int charCode = (int) sb.charAt(i);
			if(charCode == spaceDBC) {
				temp.append((char) spaceCBD);
			}else {
				int flag = 0;
				for(char c : charArray) {
					if(charCode - ((int) c) == dValue) {
						temp.append(c);
						break;
					}
					flag++;
				}
				
				if(flag >= charArray.length) {
					temp.append((char) charCode);
				}
			}
		}
		
		//消除空格
		String result = temp.toString();
		Pattern p = Pattern.compile("\\s+|\t");
		Matcher m = p.matcher(result);
		result = m.replaceAll("");
		
		return result;
	}
	
	/**
	 * 校验表达式是否符合规范
	 * @param expression 经过格式化的表达式
	 * @return
	 */
	public static Map<String, Object> validate(String expression){
		Map<String, Object> result = new HashMap<>();
		
		//校验()是否对应
		int leftBracketsCount = appearNumber("[(]", expression);
		int rightBracketsCount = appearNumber("[)]", expression);
		if(leftBracketsCount != rightBracketsCount) {
			if(leftBracketsCount > rightBracketsCount) {
				result.put("message", "')'缺少对应的'('");
			}else {
				result.put("message", "'('缺少对应的')'");
			}
			result.put("isValid", false);
				
			return result;
		}
			
		//校验运算符使用
		StringBuilder sb = new StringBuilder(expression);
		String error = null;
		int point = -1;
		for(int i = 1; i < sb.length(); i++) {
			char temp = sb.charAt(i), prev = sb.charAt(i-1);
			if(operator.contains(String.valueOf(prev))) {
				if(temp == '|' || temp == '&' || temp == ')') {
					error = "'" + prev + "'缺少必要的参数";
					point = i-1;
				}else if(temp == '#') {
					error = "参与运算的字符串不能以'#'开头";
					point = i;
				}
			}else if(prev == '(') {
				if(temp == '|' || temp == '&') {
					error = "'" + temp + "'缺少必要的参数";
					point = i;
				}else if(temp == '#') {
					error = "参与运算的字符串不能以'#'开头";
					point = i;
				}else if(temp == ')') {
					error = "'('和')'之间缺少表达式";
					point = i-1;
				}
			}else if(prev == ')') {
				if(temp != '|' && temp != '&') {
					error = "')'后缺少运算符";
					point = i-1;
				}
			}else if(prev == '#') {
				if(specialChar.contains(String.valueOf(temp))) {
					error = "参与运算的字符串不能以'#'结尾";
					point = i-1;
				}
			}else if(i == sb.length()-1){
				if(operator.contains(String.valueOf(temp))) {
					error = "'" + temp + "'缺少必要的参数";
					point = i;
				}else if(temp == '(') {
					error = "'('缺少对应的')'";
					point = i;
				}else if(temp == '#') {
					error = "参与运算的字符串不能以'#'结尾";
					point = i;
				}
			}else {
				if(temp == '!' || temp == '(') {
					error = "'" + temp + "'前缺少必要的运算符";
					point = i;
				}
			}
		}
			
		if(point != -1) {
			result.put("isValid", false);
			result.put("message", error);
			result.put("point", point);
			return result;
		}
			
		result.put("isValid", true);
		result.put("message", "表达式合法");
		return result;
	}
	
	/**
	 * 生成计算器
	 * @param expression 经过校验的表达式
	 * @return 计算器
	 */
	public static CustQualityCheckCalculator calculator(String expression) {
		return new CustQualityCheckCalculator(inffixToSuffix(expression));
	}
	
	/**
	 * 统计字符串出现的次数
	 * @param regx 匹配字符串的正则表达式
	 * @param text 待统计的文本
	 * @return
	 */
	private static int appearNumber(String regx, String text) {
		int count = 0;
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(text);
		while (m.find()) {
			count++;
		}
		return count;
	}
	
	/**
	 * 中缀表达式转后缀表达式
	 * @param expression
	 * @return
	 */
	private static String[] inffixToSuffix(String expression) {
		StringBuilder inffix = new StringBuilder(expression);
		StringBuilder suffix = new StringBuilder();
		Stack<String> stack = new Stack<>();
		String element = "", temp = "";
		
		while(inffix.length() > 0) {
			element = popNextElement(inffix);
			//判断字符在后缀表达式中的位置
			if("(".equals(element)
					|| (priority.containsKey(element)
							&& priority.get(element) > priority.get(getTopOperate(stack)))) {
				stack.push(element);
			}else if(")".equals(element)) {
				temp = stack.pop();
				while(!"(".equals(temp)) {
					suffix.append(temp).append(" ");
					temp = stack.pop();
				}
			}else if(!specialChar.contains(element)) {
				suffix.append(element).append(" ");
			}else {
				temp = stack.pop();
				suffix.append(temp).append(" ");
				stack.push(element);
			}
		}
		
		//把栈中剩余运算符弹出
		while(stack.size() > 0) {
			suffix.append(stack.pop()).append(" ");
		}
		
		return suffix.toString().split(" ");
	}
	
	/**
	 * 下一个字符串
	 * @param param
	 * @return
	 */
	private static String popNextElement(StringBuilder param) {
		StringBuilder result = new StringBuilder();
		char c = param.charAt(0);
		param.deleteCharAt(0);
		result.append(c);
		//取到的字符非系统字符，继续
		if(!specialChar.contains(c+"")) {
			while(param.length() > 0 && !specialChar.contains((c = param.charAt(0))+"")) {
				param.deleteCharAt(0);
				result.append(c);
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 获取运算符
	 * @param stack
	 * @return
	 */
	private static String getTopOperate(Stack<String> stack) {
		String temp = "";
		for(int i = stack.size() - 1; i >= 0; i--) {
			temp = stack.get(i);
			if("(".equals(temp)) {
				break;
			}else if(operator.contains(temp)) {
				return temp;
			}
		}
		
		return "";
	}
}
