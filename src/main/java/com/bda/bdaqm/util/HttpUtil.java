package com.bda.bdaqm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	private String sessionId = "";
	/**s
	 * @Description:使用HttpURLConnection发送post请求
	 * 
	 */
	public String sendPost(String urlParam, 
			Map<String,String> params, 
			String charset) {
		StringBuffer resultBuffer = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String,String> e : params.entrySet()) {
				sbParams.append(e.getKey());
				sbParams.append("=");
				sbParams.append(e.getValue());
				sbParams.append("&");
			}
		}
		HttpURLConnection con = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		// 发送请求
		try {
			URL url = new URL(urlParam);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if(null!=sessionId && sessionId.length()>0){
				con.setRequestProperty("Cookie", sessionId);
			}
			if (sbParams != null && sbParams.length() > 0) {
				osw = new OutputStreamWriter(con.getOutputStream(), charset);
				osw.write(sbParams.substring(0, sbParams.length() - 1));
				osw.flush();
			}
			// 读取返回内容
			resultBuffer = new StringBuffer();
			int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
			if (contentLength > 0) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
				String temp;
				while ((temp = br.readLine()) != null) {
					resultBuffer.append(temp);
				}
	            String cookieValue=con.getHeaderField("Set-Cookie");
	            //System.out.println("cookie value:"+cookieValue);
	            sessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
	            //System.out.println(sessionId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * @Description:使用HttpURLConnection发送get请求
	 * 
	 */
	public String sendGet(String urlParam, 
			Map<String,String> params, 
			String charset) {
		StringBuffer resultBuffer = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String,String> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		HttpURLConnection con = null;
		BufferedReader br = null;
		try {
			URL url = null;
			if (sbParams != null && sbParams.length() > 0) {
				url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
			} else {
				url = new URL(urlParam);
			}
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if(null!=sessionId && sessionId.length()>0){
				con.setRequestProperty("Cookie", sessionId);
			}
			con.connect();
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * @Description:使用HttpURLConnection发送带有Json字符串参数的Post请求
	 * @param urlPath
	 * @param Json
	 * @param charset
	 * @return
	 */
	public String doJsonPost(String urlPath, String Json,String charset) {
        
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", charset);
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null ) {
            //if (Json != null && !StringUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                //System.out.println("doJsonPost: conn"+conn.getResponseCode());
            }
            int respondCode = conn.getResponseCode();
            System.out.println("doJsonPost: conn"+respondCode);
            if (respondCode == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
	
	
	public static void main(String[] args) {
		HttpUtil httpUtil = new HttpUtil();
		String testUrl="http://10.253.34.122:8080/banking-bp-fe-web/esApiZnzjProcess.json";
		String jsonParams="{\"pDateMon\":\"201808\",\"sessionId\":\"18081409523667401116046\"}";
		//Map<String,String> mapParams = new HashMap<>();
		//mapParams.put("pDateMon", "201808");
		//mapParams.put("sessionId", "18081409523667401116046");
		//18061710402934101440811     --201806
		//18081409523667401116046
		//18081512354053001245526
		//1808160000905564
		//18081612443777801271718
		Date a = new Date();
		String testRes = httpUtil.doJsonPost(testUrl,jsonParams,"UTF-8");
		System.out.println(testRes);
		Date b = new Date();
		System.out.println("耗时："+(b.getTime()-a.getTime()));
		
		JSONObject respJSONObject = (JSONObject)JSON.parse(testRes);
		JSONObject object = (JSONObject) respJSONObject.get("object");
		//System.out.println(object);
		String resultRows = (String) object.get("resultRows");
		//System.out.println(resultRows);
		JSONArray resultArray = (JSONArray)object.get("result");
		//System.out.println(resultArray);
		
		ListIterator<Object> it = resultArray.listIterator();
		while(it.hasNext()){
			JSONObject result = (JSONObject)it.next();
			System.out.println(result);
		}
	}
}
