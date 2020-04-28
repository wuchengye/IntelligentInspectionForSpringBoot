package com.bda.bdaqm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustQualityCheckCalculator {
	
	private List<String> operator;

	private String[] suffix;
	
	public CustQualityCheckCalculator(String[] suffix) {
		operator = new ArrayList<>();
		operator.add("!");
		operator.add("&");
		operator.add("|");
		
		this.suffix = suffix;
	}
	
	/**
	 * 执行判断
	 * @param text
	 * @return
	 */
	public Boolean execute(String text) {
		if(null!=text && !"".equals(text)){
			return this.suffixToValue(text);
		}else{
			return false;
		}
		
	}
	
	private Boolean suffixToValue(String text) {
		Stack<Boolean> stack = new Stack<>();
		
		for(int i = 0; i < this.suffix.length; i++) {
			String element = suffix[i];
			if(!operator.contains(element)) {
				stack.push(this.match(text, element));
			}else {
				if("!".equals(element)) {
					stack.push(!stack.pop());
				}else {
					if("|".equals(element)) {
						stack.push(stack.pop() | stack.pop());
					}else if("&".equals(element)) {
						stack.push(stack.pop() & stack.pop());
					}
				}
			}
		}
		
		return stack.pop();
	}
	
	private Boolean match(String paramStr, String expression) {
		StringBuilder str = new StringBuilder(paramStr);
		String[] items = expression.split("#");
		
		Boolean result = true;
		int distance = 10; //默认一个#代表10个字符
		
		Pattern pattern = Pattern.compile("^[1-9]\\d*$");
		Matcher matcher = pattern.matcher(items[items.length-1]);
		if(matcher.find() && items.length > 1) {
			//距离修改
			distance = Integer.valueOf(items[items.length-1]);
			//数组缩容
			items = Arrays.copyOf(items, items.length-1);
		}
		
		for(int i = 0; i < items.length; i++) {
			String item = items[i];
			int matchIndex = str.indexOf(item);
			if(matchIndex < 0 || (i > 0 && matchIndex > distance)) {
				result = false;
				break;
			}else {
				matchIndex += item.length();
				str = new StringBuilder(str.substring(matchIndex));
			}
		}
		
		return result;
	}
}
