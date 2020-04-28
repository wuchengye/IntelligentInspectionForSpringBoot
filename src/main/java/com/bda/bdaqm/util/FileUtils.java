package com.bda.bdaqm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author jay.huang
 *
 */
public class FileUtils {
	/**
	 * 上传文件到指定路径
	 * @param file(MultipartFile类型)
	 * @param path
	 * @param fileName
	 */
	public static void uploadToLoc(MultipartFile file, String path, String fileName) {
		File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	/**
	 * 下载指定路径下的文件
	 * @param path
	 * @param fileName
	 * @param response
	 * @throws IOException 
	 */
	public static void downloadFile(String path, String fileName, HttpServletResponse response) throws IOException {
        // 得到要下载的文件  
        File file = new File(path, fileName);  
        
        // 设置响应头，控制浏览器下载该文件  
        response.setHeader("content-disposition", "attachment;filename="  
                + URLEncoder.encode(fileName, "UTF-8"));  
        // 读取要下载的文件，保存到文件输入流  
        FileInputStream in = new FileInputStream(file);  
        // 创建输出流  
        OutputStream out = response.getOutputStream();  
        // 创建缓冲区  
        byte buffer[] = new byte[1024];  
        int len = 0;  
        // 循环将输入流中的内容读取到缓冲区当中  
        while ((len = in.read(buffer)) > 0) {  
            // 输出缓冲区的内容到浏览器，实现文件下载  
            out.write(buffer, 0, len);  
        }  
        // 关闭文件输入流  
        in.close();  
        // 关闭输出流  
        out.close();  
	}
	
	/**
	 * 根据指定XSSFWorkbook对象和文件名以excel形式进行下载(XLSX
	 * @param wb
	 * @param fileName
	 * @throws IOException 
	 */
	public void downLoadToExcel(XSSFWorkbook wb, String fileName, HttpServletResponse response) throws IOException {
		//创建文件名
		//设置头信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		//使用URLEncoder.encode方法防止出现中文文件名乱码
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//客户端下载
		OutputStream out = response.getOutputStream();
		wb.write(out);
		//关闭流
		wb.close();
		out.close();
	}
	
	/**
	 * 根据指定HSSFWorkbook对象和文件名以excel形式进行下载(XLSX
	 * @param wb
	 * @param fileName
	 * @throws IOException 
	 */
	public void downLoadToExcel(HSSFWorkbook wb, String fileName, HttpServletResponse response) throws IOException {
		//创建文件名
		//设置头信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		//使用URLEncoder.encode方法防止出现中文文件名乱码
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//客户端下载
		OutputStream out = response.getOutputStream();
		wb.write(out);
		//关闭流
		wb.close();
		out.close();
	}

	/**
	 * 复制本地文件
	 * @param srcPath 		原文件路径，如：c:/fqf.txt 
	 * @param targetPath 	目标路径，如：f:/
	 * @param tagetFileName 目标文件名，如：fqf.txt 
	 * @throws IOException
	 */
	public static void copyFile(String srcPath,String targetPath,String tagetFileName) throws IOException{
		//int bytesum = 0; 
		int byteread = 0; 
		File srcfile = new File(srcPath);
		if(srcfile.exists()){
			if(!targetPath.endsWith("/")){
				targetPath += "/";//判断路径最后是否含有“/”，如果没有则补充。
			}
			if(!new File(targetPath).exists()){
				new File(targetPath).mkdirs();
			}
			InputStream inStream = new FileInputStream(srcPath); //读入原文件 
			FileOutputStream fs = new FileOutputStream(targetPath+tagetFileName);
			byte[] buffer = new byte[1024]; 
			while ( (byteread = inStream.read(buffer)) != -1) { 
				//bytesum += byteread; //字节数 文件大小 
				fs.write(buffer, 0, byteread); 
			}
			inStream.close(); 
			fs.close();
		}
	}
	
	/**
	 * 删除文件夹，以及文件夹内部的全部文件、子文件夹
	 * @param path
	 */
	public static void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
        	if(!path.endsWith("/")){
        		path += "/";//判断路径最后是否含有“/”，如果没有则补充。
			}
            File[] tmp=dir.listFiles();
            //如果该目录下还有文件，则递归删除文件，最后删除文件夹
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+tmp[i].getName());
                }
                else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }
	
	public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	}
	
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	
	/**
	 * 删除指定文件夹的指定文件
	 * @param path
	 * @param filename
	 * @return
	 */
	public static boolean delFile(String path,String filename){
		if(!path.endsWith("/")){
    		path += "/";//判断路径最后是否含有“/”，如果没有则补充。
		}
		File file = new File(path+filename);
		boolean result = file.delete();
		return result;
	}
}
