package com.bda.bdaqm.risk.mapper;

import java.util.List;

import com.bda.bdaqm.risk.model.DataManage;

import tk.mybatis.mapper.common.Mapper;

public interface DataManageMapper extends Mapper<DataManage> {
	
	List<DataManage> getDataManage();
}
