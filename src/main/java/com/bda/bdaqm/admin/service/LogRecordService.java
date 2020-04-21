package com.bda.bdaqm.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.mapper.LogRecordMapper;
import com.bda.bdaqm.admin.model.LogRecord;
import com.bda.common.service.AbstractService;

@Service
public class LogRecordService extends AbstractService<LogRecord>{

	@Autowired
	private LogRecordMapper mapper;
	
}
