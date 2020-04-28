package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheck;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.bdaqm.util.ComboBoxItem;

import tk.mybatis.mapper.common.Mapper;

public interface TicketCheckResultMapper extends Mapper<TicketCheckResult>{

	List<TicketCheckResult> getResultData(@Param("params")QueryParams params,@Param("flag")int flag);
	
	//获取班组信息
    List<ComboBoxItem> getClassCombobox();
    
    //获取工作票种类
    List<ComboBoxItem> getTicketType();
    
    //获取三种人姓名
    List<ComboBoxItem> getPersonName(@Param("type")String type);
    
    //获取柱状图 数据
    List<Map<String,String>> getChartData(@Param("params")QueryParams params);
    List<Map<String,String>> getExceptionBarData(@Param("params")QueryParams params);
    
    //获取饼状图 数据
    List<Map<String,String>> getPieData(@Param("params")QueryParams params);
	
    //根据ID查找工作票信息
    public List<TicketCheck> selectById(@Param("id") String id);
    
    
    //根据ID查找校验结果
    TicketCheckResult getResultById(@Param("id") String id,@Param("batchTime")String batchTime);
    
    List<Map<String,String>> selectChildById(@Param("id") String id,@Param("batchTime")String batchTime);
    
    //获取校验批次
    List<ComboBoxItem> getAllBatch();
    
    //获取所有人员信息
    List<Map<String,String>> getAllUser();
    
    //获取批次管理
    List<Map<String,String>> getBatchsForManage();   
    
    //删除批次
    int delResBatch(@Param("batch")String batch);
    int delParentBatch(@Param("batch")String batch);
    int delChildBatch(@Param("batch")String batch);
}
