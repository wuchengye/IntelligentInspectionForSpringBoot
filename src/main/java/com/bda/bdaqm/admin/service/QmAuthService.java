package com.bda.bdaqm.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.mapper.QmAuthMapper;

@Service
public class QmAuthService {
	@Autowired
	private QmAuthMapper qmAuthMapper;
	
	@Autowired
	private PermisService permisService;
	
	
}
