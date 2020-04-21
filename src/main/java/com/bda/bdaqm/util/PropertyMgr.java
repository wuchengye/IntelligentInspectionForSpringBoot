package com.bda.bdaqm.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties文件管理类
 * @author jay.huang
 *
 */
public class PropertyMgr {
	/**
	 * 数据库配置文件
	 */
	public static final String DB_CONFIG_PROP = "dbconfig.properties";
	
	public static final String FTP_CONFIG_PROP = "ftpconfig.properties";
	public static final String FTP_IP_KEY = "ftpIp";
	public static final String FTP_ACCOUNT_KEY = "ftpAccount";
	public static final String FTP_PW_KEY = "ftpPW";
	public static final String FTP_PATH_KEY = "ftpPath";
	public static final String FTP_DEBUG_MODEL_KEY = "debugmodel";
	public static final String SFTP_IP = "sftpIp";
	
	public static final String SFTP_NAME = "sftpName";
	
	public static final String SFTP_PWD = "sftpPwd";
	
	public static final String SFTP_PORT = "sftpPort";
	
	public static final String SFTP_PATH = "sftpPath";
	
	private static Properties prop = null;
	
	private PropertyMgr() {
		
	}
	
	/**
	 * 根据配置文件、key值获取value
	 * @param propFileName
	 * @param key
	 * @return
	 */
	public static synchronized  String getPropertyByKey(String propFileName, String key) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(propFileName)) {
			System.out.println("key值或配置文件名不能为空！");
			return null;
		}
		prop = new Properties();
		try {
			prop.load(PropertyMgr.class.getClassLoader().getResourceAsStream(propFileName));
		} catch (IOException e) {//如果找不到那个文件
			System.out.println("找不到properties文件：" + propFileName);
			e.printStackTrace();
			return null;
		}
		String value = prop.getProperty(key);
		return value;
	}
	
}
