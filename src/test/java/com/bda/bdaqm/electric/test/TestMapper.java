package com.bda.bdaqm.electric.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.Statistics;

import tk.mybatis.mapper.common.Mapper;

public interface TestMapper extends Mapper<TypeAndDate>{

	List<Map<String,String>> SelectOperation();
	List<Map<String,String>> SelectWorkLast();
	
	String SelectLineWorking(@Param("type")String type,@Param("date")String date);
	String SelectLowVo(@Param("type")String type,@Param("date")String date);
	String SelectUrgent(@Param("type")String type,@Param("date")String date);
	String SelectStationOne(@Param("type")String type,@Param("date")String date);
	String SelectStationTwo(@Param("type")String type,@Param("date")String date);
	String SelectStationThree(@Param("type")String type,@Param("date")String date);
	String SelectLineOne(@Param("type")String type,@Param("date")String date);
	String SelectLineTwo(@Param("type")String type,@Param("date")String date);
	String SelectFireOne(@Param("type")String type,@Param("date")String date);
	String SelectFireTwo(@Param("type")String type,@Param("date")String date);
	int addData(@Param("list")List<Statistics> item);
}
