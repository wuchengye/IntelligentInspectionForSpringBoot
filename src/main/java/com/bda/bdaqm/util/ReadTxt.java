package com.bda.bdaqm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadTxt {
	
	public static Logger logger = LoggerFactory.getLogger(ReadTxt.class);
	
	public static List<String> readTxt(String path){
		List<String> result = new ArrayList<String>();
		try {
            Scanner in = new Scanner(new File(path),"UTF-8");
            while (in.hasNextLine()) {
				String str = in.nextLine();
                result.add(str);
            }
            in.close();
        } catch (Exception e) {
        	logger.error(e.toString());
        }
		return result;
	}
	
	public static int readTxt(String path, String fileName, ImportReader reader, int startLine, String batchId) throws IOException{
		FileInputStream inputStream = null;
		Scanner sc = null;
		int count = 0;
		try {
			File file = new File(path, fileName);
			inputStream = new FileInputStream(file);
			sc = new Scanner(inputStream, "UTF-8");
			for (int i = 0; i < startLine && sc.hasNextLine(); i++) {
				sc.nextLine();
			}
            while (sc.hasNextLine()) {
            	List<String> strList = new ArrayList<String>(1001);
            	for (int i = 0; i < 1000 && sc.hasNextLine(); i++) {
            		strList.add(sc.nextLine());
				}
            	count += reader.read(strList, batchId);
            	//logger.info("已导入："+count);
            }
            return count;
        } finally {
        	if(sc != null) {
        		sc.close();
        	}
        	if(inputStream != null) {
        		inputStream.close();
        	}
        }
	}
	
	public static int getFileLineNumber(String path, String fileName) throws IOException{
		File file = new File(path, fileName);
		long fileLength = file.length();
		LineNumberReader rf = null;
		try {
			rf = new LineNumberReader(new FileReader(file));
			int lines = 0;
			rf.skip(fileLength);
			lines = rf.getLineNumber();
			return lines;
		} finally {
			if(rf != null){
				rf.close();
			}
		}
	}
	
	public static String getFileCoding(File file) throws IOException{
		FileInputStream fs = null;
		BufferedInputStream bin = null;
		String code = "";
		try{
			fs = new FileInputStream(file);
			bin = new BufferedInputStream(fs);
			int p = (bin.read() << 8) + bin.read();
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}
			return code;
		} finally {
			bin.close();
			fs.close();
		}
	}
}
