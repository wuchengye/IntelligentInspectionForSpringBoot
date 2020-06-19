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

import com.bda.bdaqm.risk.mapper.DubiousMapper;
import com.bda.bdaqm.risk.model.NotDubious;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageHelper;

@Service
public class DubiousService extends AbstractService<NotDubious>{
	
	@Autowired
	public DubiousMapper dubiousMapper;

	public NotDubious getDubiousById(String sessionId){
		return dubiousMapper.getDubiousById(sessionId);
	}

	public int updateFileNameAndFilePath(String sessionId, String fileName, String filePath) {
		return dubiousMapper.updateFileNameAndFilePath(sessionId, fileName, filePath);
	}
	
}
