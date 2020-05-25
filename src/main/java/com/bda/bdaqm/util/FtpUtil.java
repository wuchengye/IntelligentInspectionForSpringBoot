package com.bda.bdaqm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil {
	private String ip = "";
	private String username = "";
	private String password = "";
	private int port = 21;
	private FTPClient ftpClient = null;

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	//private String reply = "";
	private static String encoding = System.getProperty("file.encoding");
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public FtpUtil(String serverIP, int port, String username, String password) {
		this.ip = serverIP;
		this.username = username;
		this.password = password;
		this.port = port;
		this.ftpClient = new FTPClient();
	}

	/**
	 * 连接服务
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		if (this.ftpClient == null) {
			throw new Exception("haven't call the Constructor!");
		}
		// 设置超时时间
		ftpClient.setConnectTimeout(30000);
		if (ftpClient.isConnected()) {
			return true;
		}

		ftpClient.connect(ip);
		ftpClient.login(username, password);
		int replycode = ftpClient.getReplyCode();
		// 验证是否登陆成功
		if (!FTPReply.isPositiveCompletion(replycode)) {
			// logger.info("未连接到FTP，用户名或密码错误。");
			throw new RuntimeException("未连接到FTP，用户名或密码错误。");
		} else {
			// logger.info("FTP连接成功。IP:"+host +"PORT:" +port);
		}
		// 设置连接属性
		ftpClient.setControlEncoding(encoding);
		// 设置以二进制方式传输
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		return true;
	}

	/**
	 * 关闭连接
	 * @throws Exception 
	 */
	public void disconnect() throws Exception {
		if (this.ftpClient == null) {
			throw new Exception("haven't call the Constructor!");
		}
		//判断是否已经连接，如果已连接则断开
		if(ftpClient.isConnected()){
			ftpClient.disconnect();
		}else{
			return;
		}
		int replycode = ftpClient.getReplyCode();
		String reply = this.getReplyString();
		// 验证是否关闭成功
		if (!FTPReply.isPositiveCompletion(replycode)) {
			logger.error("disconnect reply code:"+replycode);
			logger.error("disconnect reply:"+reply);
			throw new RuntimeException("关闭FTP连接失败!!! ");
		} else {
			logger.info("成功关闭FTP连接!!! ");
		}

	}

	/**
	 * FTP下载文件
	 * @param localPath
	 * @param ftpPath
	 * @param filename
	 * @throws Exception
	 */
	public void downloadFile(String localPath, String ftpPath,String filename ) throws Exception{
		InputStream is = null;
		FileOutputStream fos = null;
		try{
			//ftpPath=new String(ftpPath.getBytes("UTF-8"),"iso-8859-1");// 转换后的目录名或文件名。
			logger.info("Download ftpPath:"+ftpPath);
			logger.info("Download localPath:"+localPath);
			ftpClient.enterLocalPassiveMode();
			logger.info("enterLocalPassiveMode reply:"+this.getReplyString());
			
			
			
			//切换当前工作目录
			ftpClient.changeWorkingDirectory(ftpPath);
			logger.info("changeWorkingDirectory ftpPath:"+ftpPath+" reply:"+this.getReplyString());
			
			FTPFile[] files = ftpClient.listFiles();
			logger.info("listFiles reply:"+this.getReplyString());
			/*for(FTPFile file:files){
				logger.info(file.getRawListing());
			}*/
			
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE); 
			logger.info("setFileTransferMode reply:"+this.getReplyString());
			
			//此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉
			/*boolean flag0 = ftpClient.completePendingCommand();
			reply = ftpClient.getReplyString();
			logger.info("completePendingCommand reply:"+reply);
			if(!flag0) {
				logger.error("下载失败，completePendingCommand失败!!  "+reply);
				throw new RuntimeException("下载失败，completePendingCommand失败!!  "+reply);
			}*/
			
			//检测文件夹是否存在
			File directory = new File(localPath);
			if (!directory.exists()) {
	            directory.mkdirs();
	        }
			
			File localFile = new File(localPath+filename); 
			fos = new FileOutputStream(localFile);
			
			// 下载文件 不要用retrieveFile方法。
			/*boolean flag1 = ftpClient.retrieveFile(filename, fos);
			logger.info("retrieveFile reply:"+this.getReplyString());
			if(!flag1) {
				logger.error("下载失败，retrieveFile失败!!  "+this.getReplyString());
				throw new RuntimeException("下载失败，retrieveFile失败!!  "+this.getReplyString());
			}*/
			//开始下载文件 
			logger.info("retrieveFileStream 开始");
			is=ftpClient.retrieveFileStream(filename); 
			logger.info("retrieveFileStream reply:"+this.getReplyString());
			byte[]byteArray=new byte[4096];
			int read=0;
			while((read=is.read(byteArray))!=-1) {
				fos.write(byteArray,0,read);
			}
			logger.info("retrieveFileStream 结束");
			//这句很重要  要多次操作这个ftp的流的通道,要等他的每次命令完成
			ftpClient.completePendingCommand();
			logger.info("completePendingCommand reply:"+this.getReplyString());
			
		} finally{
			try {
				if(is!=null) {
					is.close();
					logger.info("FTP输入流 is close");
				}
				if(fos!=null){
					fos.flush();
					fos.close();
					logger.info("文件输出流 fos close");
				}
			} catch (IOException e) {
				logger.error("IO流关闭异常"+e.getMessage());
			}
		}
	         
    }
	
	/**
	 * FTP上传文件
	 * @param localPath
	 * @param ftpPath
	 * @param filename
	 */
	public void uploadFile(String localPath, String ftpPath,String filename )throws Exception{
		InputStream is = null;
		logger.info("Upload ftpPath:"+ftpPath);
		logger.info("Upload localPath:"+localPath);
		
		try {
			is = new FileInputStream(new File(localPath+filename));
			//进入被动模式
			ftpClient.enterLocalPassiveMode();
			logger.info("enterLocalPassiveMode reply:"+this.getReplyString());
			
			//设置文件传输类型
			ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			logger.info("setFileTransferMode reply:"+this.getReplyString());
			
			//切换当前工作目录
			ftpClient.changeWorkingDirectory(ftpPath);
			logger.info("changeWorkingDirectory ftpPath:"+ftpPath+" reply:"+this.getReplyString());
			
			/*上传文件不要使用storeFile方法，很慢，使用下面的输出流方式来上传文件*/
			/*boolean storeFlag = ftpClient.storeFile(filename, is);
			logger.info("storeFile reply:"+this.getReplyString());
			
			if(!storeFlag) {
				logger.error("上传失败，storeFile失败!!  "+this.getReplyString());
				throw new RuntimeException("下载失败，storeFile失败!!  "+this.getReplyString());
			}*/
			
			//开始上传文件
			logger.info("storeFileStream 开始");
			OutputStream out = ftpClient.storeFileStream(filename);
			logger.info("storeFileStream reply:"+this.getReplyString());
			byte[]byteArray=new byte[4096];
			int read=0;
			while((read=is.read(byteArray)) != -1) {
			    out.write(byteArray,0,read);
			}
			out.close();
			logger.info("storeFileStream 结束");
			//这句很重要  要多次操作这个ftp的流的通道,要等他的每次命令完成
			ftpClient.completePendingCommand();
			logger.info("completePendingCommand reply:"+this.getReplyString());
		} finally{
			try {
				if(is!=null) {
					is.close();
					logger.info("FTP上传输入流 is close");
				}
				
			} catch (IOException e) {
				logger.error("IO流关闭异常"+e.getMessage());
			}
		}
		
		
	}
	
	private String getReplyString(){
		String reply = ftpClient.getReplyString();
		return reply;
	}

	/**
	 * @author wcy
	 * @date 2020-05-22 11:37
	 * ftp下载文件返回成功与否
	 */
	public boolean downloadFiles(String localPath, String ftpPath,String localFileName ,String ftpFileName) throws Exception{
		boolean flag = false;
		InputStream is = null;
		FileOutputStream fos = null;
		try{
			//ftpPath=new String(ftpPath.getBytes("UTF-8"),"iso-8859-1");// 转换后的目录名或文件名。
			logger.info("Download ftpPath:"+ftpPath);
			logger.info("Download localPath:"+localPath);
			ftpClient.enterLocalPassiveMode();
			logger.info("enterLocalPassiveMode reply:"+this.getReplyString());



			//切换当前工作目录
			ftpClient.changeWorkingDirectory(ftpPath);
			logger.info("changeWorkingDirectory ftpPath:"+ftpPath+" reply:"+this.getReplyString());

			FTPFile[] files = ftpClient.listFiles();
			logger.info("listFiles reply:"+this.getReplyString());
			/*for(FTPFile file:files){
				logger.info(file.getRawListing());
			}*/

			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			logger.info("setFileTransferMode reply:"+this.getReplyString());

			//此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉
			/*boolean flag0 = ftpClient.completePendingCommand();
			reply = ftpClient.getReplyString();
			logger.info("completePendingCommand reply:"+reply);
			if(!flag0) {
				logger.error("下载失败，completePendingCommand失败!!  "+reply);
				throw new RuntimeException("下载失败，completePendingCommand失败!!  "+reply);
			}*/

			//检测文件夹是否存在
			File directory = new File(localPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File localFile = new File(localPath+localFileName);
			fos = new FileOutputStream(localFile);

			// 下载文件 不要用retrieveFile方法。
			/*boolean flag1 = ftpClient.retrieveFile(filename, fos);
			logger.info("retrieveFile reply:"+this.getReplyString());
			if(!flag1) {
				logger.error("下载失败，retrieveFile失败!!  "+this.getReplyString());
				throw new RuntimeException("下载失败，retrieveFile失败!!  "+this.getReplyString());
			}*/
			//开始下载文件
			logger.info("retrieveFileStream 开始");
			is=ftpClient.retrieveFileStream(ftpFileName);
			logger.info("retrieveFileStream reply:"+this.getReplyString());
			byte[]byteArray=new byte[4096];
			int read=0;
			while((read=is.read(byteArray))!=-1) {
				fos.write(byteArray,0,read);
			}
			logger.info("retrieveFileStream 结束");
			//这句很重要  要多次操作这个ftp的流的通道,要等他的每次命令完成
			ftpClient.completePendingCommand();
			logger.info("completePendingCommand reply:"+this.getReplyString());
			flag = true;
		} finally{
			try {
				if(is!=null) {
					is.close();
					logger.info("FTP输入流 is close");
				}
				if(fos!=null){
					fos.flush();
					fos.close();
					logger.info("文件输出流 fos close");
				}
			} catch (IOException e) {
				logger.error("IO流关闭异常"+e.getMessage());
			}
		}
		return flag;
	}
}
