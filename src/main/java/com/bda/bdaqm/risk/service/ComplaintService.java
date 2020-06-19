package com.bda.bdaqm.risk.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.risk.mapper.ComplaintMapper;
import com.bda.bdaqm.risk.mapper.UsedTabooMapper;
import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ComplaintService extends AbstractService<TabooSession>{
	
	@Autowired
	public ComplaintMapper complaintMapper;
	
	public ComplaintSession getComplaintSessionById(String sessionId){
		return complaintMapper.getComplaintSessionById(sessionId);
	}

	public int updateFileNameAndFilePath(String sessionId, String fileName, String filePath) {
		return complaintMapper.updateFileNameAndFilePath(sessionId, fileName, filePath);
	}
}
