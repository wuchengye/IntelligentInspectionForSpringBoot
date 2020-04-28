package com.bda.bdaqm.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * @author jay.huang
 *
 */
public class StringUtils {
	/**
	 * 第一个字母转化为大写
	 * @param str
	 * @return
	 */
	public static String firstCharToUpperCase(String str) {
		String result = str.substring(0,1).toUpperCase() + str.substring(1);
		return result;
	}
	
	/**
	 * Clob对象转换为字符串
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String ClobToStr(Clob clob) throws Exception{
		
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	
	/**
	 * 统计指定字符串在另一个字符串中出现的次数
	 */
	public static int occurTimesInStr(String source, String target) {
		StringBuilder tsb = new StringBuilder(target);
		Pattern pattern = Pattern.compile(source);
		Matcher matcher = pattern.matcher(tsb.toString());
		int cnt = 0;
		
		while(matcher.find()) {
			cnt++;
		}
		return cnt;
	}
}
