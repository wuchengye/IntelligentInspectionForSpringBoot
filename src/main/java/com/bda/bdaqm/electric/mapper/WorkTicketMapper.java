package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.WebServiceVo;
import com.bda.bdaqm.electric.model.WorkTicket;
import com.bda.bdaqm.util.ComboBoxItem;

import tk.mybatis.mapper.common.Mapper;

public interface WorkTicketMapper extends Mapper<WorkTicket>{

	List<ComboBoxItem> getTitles();
	
	List<ComboBoxItem> getTaskByTitle(@Param("title")String title);
	
	List<String> getTaskStrByTitle(@Param("title")String title);
	
	List<String> getEquipNumber(@Param("title")String title,@Param("task")String task);
	
	List<String> getBreaker(@Param("title")String title,@Param("task")String task, @Param("listBreaker")String listBreaker);
	
	List<String> getDisconnector(@Param("title")String title,@Param("task")String task, @Param("equipNum")List<String> equipNum, @Param("listDisconnector")String listDisconnector);
	
	List<String> getIsLanding(@Param("title")String title,@Param("task")String task, @Param("equipNum")List<String> equipNum, @Param("listGdisconnector")String listGdisconnector);
	
	List<Map<String,String>> getContent(@Param("type")String type);
	
	WorkTicket getTicket(@Param("workTask")String workTask,@Param("station")String station,@Param("ticketType")String ticketType);
	
	//获取作业类型
	List<String> getWorkType(@Param("ticketType")String type);
	List<String> getAllTitle();
	
	//以下三个只为开关服务
	List<String> getBreakerOpen(@Param("title")String title,@Param("task")String task);
	
	List<String> getDisconnectorOpen(@Param("title")String title,@Param("task")String task);
	
	List<String> getIsLandingOpen(@Param("title")String title,@Param("task")String task);
	
	int saveInfo(@Param("ticket")WebServiceVo wv);
	
	WebServiceVo selectInfoById(@Param("id")String ticketId,@Param("ticketType")String ticketType,@Param("workTask")String workTask,@Param("station")String station);
	
}
