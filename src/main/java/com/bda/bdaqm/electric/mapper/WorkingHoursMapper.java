package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.HourModulus;

public interface WorkingHoursMapper {

	List<Map<String, Object>> getCountOfTicket(@Param("param")Map<String, String> params);
	
	List<Map<String, Object>> getDetailOfAll(@Param("param")Map<String, String> params);
	
	//获取系数配置数据
	List<HourModulus> getModulusByType(@Param("personType")String personType);
	
	//修改系数
    void updateModulus(@Param("modulus")HourModulus modulus);
	
}
