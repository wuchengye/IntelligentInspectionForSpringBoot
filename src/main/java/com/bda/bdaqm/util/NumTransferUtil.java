package com.bda.bdaqm.util;

public class NumTransferUtil {
	
	public static final String GZF = "零〇一壹二贰三叁弎四肆五伍六陆七柒八捌九玖";
	public static final String SingleNum = "001122333445566778899";
	public static final String Digit = "十拾百佰千仟";
	
	/**
	 * 中文数字转化成阿拉伯数字
	 * 
	 * @param before
	 * @return
	 */
	public static String GZF2Num(String before){
		String result = "";
		int index=-1;  
		for(int i=0;i<before.length();i++){ 
			char tmp=before.charAt(i);
			if((index=GZF.indexOf(tmp))!=-1){
				//数字直接转换
				result += SingleNum.charAt(index);
			}else if((index=Digit.indexOf(tmp))!=-1){
				//如果是"十拾百佰千仟"这些数位，就进行判断
				if(i < before.length()-1){
					if(i>0 && GZF.indexOf(before.charAt(i-1))!=-1){
						if(GZF.indexOf(before.charAt(i+1))!=-1){
							//数位前后都有数字，无需处理
						}else{
							//数位前有数字，后无数字
							if(tmp == '十' || tmp == '拾'){
								result += "0";
							}else if(tmp == '百' || tmp == '佰'){
								result += "00";
							}else if(tmp == '千' || tmp == '仟'){
								result += "000";
							}
						}
					}else{
						if(GZF.indexOf(before.charAt(i+1))!=-1){
							//数位前无数字，后有数字
							result += "1";
						}else{
							//数位前无数字，后无数字
							if(tmp == '十' || tmp == '拾'){
								result += "10";
							}
						}
					}
				}else{
					if(i>0 && GZF.indexOf(before.charAt(i-1))!=-1){
						//数位为最后一位，前有数字
						if(tmp == '十' || tmp == '拾'){
							result += "0";
						}else if(tmp == '百' || tmp == '佰'){
							result += "00";
						}else if(tmp == '千' || tmp == '仟'){
							result += "000";
						}
					}else{
						//数位为最后一位，前无数字
						if(tmp == '十' || tmp == '拾'){
							result += "10";
						}else if(tmp == '百' || tmp == '佰'){
							result += "100";
						}else if(tmp == '千' || tmp == '仟'){
							result += "1000";
						}
					}
				}
			}else{
				result += tmp;
			}
		}
		return result;
	}

	/*public static void main(String[] args) {
		String str = "香港话说";
		String encode = "Big5";//GB2312
		//話說  
		try {
			System.out.println("話說".getBytes(encode));
	        if (!str.equals(new String(str.getBytes(encode), encode))) {
	        	System.out.println("是繁体");             
	        }else{
	        	System.out.println("是简体");
	        }
        } catch (Exception exception3) {
 
        }
	}*/
	
}
