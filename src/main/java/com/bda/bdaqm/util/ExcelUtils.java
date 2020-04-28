package com.bda.bdaqm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	/**
	 * 读xlsx文件，返回2维数组
	 * 
	 * @param fileName 文件名
	 * @param startRow 开始行
	 * @param columnSize 列数
	 * @return
	 * @throws IOException
	 * @throws EncryptedDocumentException 
	 * @throws InvalidFormatException 
	 */
	public static List<List<String>> readXlsx(String filePath, String fileName, int startRow, int columnSize) throws IOException, InvalidFormatException {
		List<List<String>> result = new ArrayList<List<String>>();
		Workbook rWB = null;
		InputStream in = null;
		try {
			File file = new File(filePath, fileName);
//			rWB = new XSSFWorkbook(fileName);
			in = new FileInputStream(file);
//			Workbook create = WorkbookFactory.create(in);
			long start = (new Date()).getTime();
			rWB = WorkbookFactory.create(in);
			System.out.println((new Date()).getTime() - start);
//			if (create instanceof XSSFWorkbook) {
//				rWB = (XSSFWorkbook) create;
//			}
			Sheet rSheet = rWB.getSheetAt(0);
			int lastRowNum = rSheet.getLastRowNum();
			System.out.println(lastRowNum);
			System.out.println();
			for (int i = startRow; i <= lastRowNum; i++) {
				List<String> rowValues = new ArrayList<String>();
				for(int j = 0; j < columnSize; j++){
					Cell cell = rSheet.getRow(i).getCell(j, Row.CREATE_NULL_AS_BLANK);
					if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							//如果是日期类型
							SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());//获取成DATE类型     
							rowValues.add(dateformat.format(dt));   
						}else{  
				               DecimalFormat df = new DecimalFormat("0");    
				               String cellValue = df.format(cell.getNumericCellValue());  
				               rowValues.add(cellValue);
				           }  
					}else{
						cell.setCellType(Cell.CELL_TYPE_STRING);
						rowValues.add(cell.getStringCellValue());
					}
				}
//				result.add(rowValues);
				// xuying.chen 判断空白行---begin
				boolean isBreakFlag = true;//true:这一行为空,需要退出大循环,false:这行不为空,不需要退出大循环
				for (String string : rowValues) {
					if(string!=null && !"".equals(string.trim())){
						//如果这行里有一列是不为空,说明这行有数据,不需要退出大循环,直接退出判断小循环
						isBreakFlag = false;
						break;
					}
				}
				if(isBreakFlag){
					break;
				}else{
					result.add(rowValues);
				}
				// xuying.chen 判断空白行---end
			}
		} finally {
			if (rWB != null) {
				rWB.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return result;
	}
	
	/**
	 * 写xlsx文件
	 * 
	 * @param fileName 文件名
	 * @param contents 内容，一个二维数组
	 * @return 返回是否成功
	 * @throws IOException
	 */
	public static boolean writeXlsx(String path, String fileName, List<List<String>> contents) throws IOException{
		XSSFWorkbook wWB = null;
		FileOutputStream fileOut = null;
		try {
			File file = new File(path, fileName);
			fileOut = new FileOutputStream(file);
			wWB = new XSSFWorkbook();
			XSSFSheet wSheet = wWB.createSheet("Sheet");
			for(int i = 0; i < contents.size(); i++){
				List<String> rowContents = contents.get(i);
				XSSFRow wRow = wSheet.createRow(i);
				for(int j = 0; j < rowContents.size(); j++){
					wRow.createCell(j).setCellValue(rowContents.get(j));
				}
			}
			wWB.write(fileOut);
		} finally {
			if (wWB != null) {
				wWB.close();
			}
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return true;
	}
	
//	public static List<List<String>> readBigXlsx(String fileName, int startRow, int columnSize) throws Exception {
//		List<List<String>> result = new ArrayList<List<String>>();
//		IRowReader reader = new RowReader();
//		Excel2007Reader excel07 = new Excel2007Reader();  
//        excel07.setRowReader(reader);  
//        excel07.processOneSheet(fileName, 1, result);
//        return result;
//	}
}
