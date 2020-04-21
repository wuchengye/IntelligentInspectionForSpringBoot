package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.ComboxTree;
import com.bda.bdaqm.electric.model.Original;
import com.bda.bdaqm.electric.model.Statistics;
import com.bda.bdaqm.electric.model.StatisticsVo;
import com.bda.bdaqm.util.ComboBoxItem;

import tk.mybatis.mapper.common.Mapper;

public interface StatisticsMapper extends Mapper<Statistics>{
	
	public List<Statistics> getAllData(@Param("type")String type,@Param("timeBegin")String TimeBegin, @Param("timeEnd")String TimeEnd);
	public List<Original> getOriginData(@Param("unit")String unit,@Param("date")String month,@Param("endMonth")String endMonth,@Param("type")String type);
	public List<ComboxTree> getComboxTree();
	public List<String> getComboxTreeZP();
	public List<Map<String,String>> queryUnit();
	public List<ComboBoxItem> getComboxSql();
	public List<ComboBoxItem> getZWComboxSql(@Param("unitType")String unitType);
	public List<StatisticsVo> getPermission(@Param("type")String type,@Param("timeBegin")String TimeBegin, @Param("timeEnd")String TimeEnd);
	public List<StatisticsVo> getSign(@Param("type")String type,@Param("timeBegin")String TimeBegin, @Param("timeEnd")String TimeEnd);
	public List<Statistics> getExterNalUnit(@Param("timeBegin")String TimeBegin, @Param("timeEnd")String TimeEnd);

}
