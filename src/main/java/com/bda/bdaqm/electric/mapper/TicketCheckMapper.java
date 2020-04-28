package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.Criterion15Vo;
import com.bda.bdaqm.electric.model.CriterionChildrenResult;
import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheck;
import com.bda.bdaqm.electric.model.TicketCheckResult;

import tk.mybatis.mapper.common.Mapper;

public interface TicketCheckMapper extends Mapper<TicketCheck>{

	List<Map<String,Object>> getTicketsData(@Param("ticket")QueryParams ticket);
	
	List<Map<String,Object>> getTicketsDataIsNull(@Param("ticket")QueryParams ticket);
	
	List<String> getQuartzTicketsData(@Param("beginTime")String beginTime,@Param("endTime")String endTime);

	public List<Map<String,Object>> getTicketInfo(@Param("ids")List<String> ids);
	
	public List<String> getUser();
	
	public List<Map<String,String>> getUserType();
	
	public void deleteResult(@Param("resultList")List<TicketCheckResult> resultList);
	
	public void insertResult(@Param("resultList")List<TicketCheckResult> resultList);
	
	public void deleteResultChildren(@Param("resultList")List<CriterionChildrenResult> resultList);
	
	public void insertResultChildren(@Param("resultList")List<CriterionChildrenResult> resultList);
	
	public List<String> getLocation();
	
	public List<Criterion15Vo> selectBySql(@Param("list")List<String> list);
	
	public int saveBatch(@Param("time") String batchTime, @Param("userId") String userId, @Param("count") int count);
	
	public String queryMax();
	
	@MapKey("id")
	public Map<String,Map<String,Object>> getIsConnect(@Param("oids")List<String> oids);
}
