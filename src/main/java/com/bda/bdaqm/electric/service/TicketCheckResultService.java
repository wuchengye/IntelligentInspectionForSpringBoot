package com.bda.bdaqm.electric.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.mapper.TicketCheckResultMapper;
import com.bda.bdaqm.electric.model.QueryParams;
import com.bda.bdaqm.electric.model.TicketCheck;
import com.bda.bdaqm.electric.model.TicketCheckResult;
import com.bda.bdaqm.util.ComboBoxItem;
import com.bda.common.service.AbstractService;

@Service
public class TicketCheckResultService extends AbstractService<TicketCheckResult>{

	@Autowired
	private TicketCheckResultMapper mapper;
	
	public List<TicketCheckResult> getResultData(QueryParams ticket,int flag){
		
		return mapper.getResultData(ticket,flag);
	}
	
	public List<ComboBoxItem> getClassCombobox(){
		return mapper.getClassCombobox();
	}
	
	public List<ComboBoxItem> getTicketType(){
		return mapper.getTicketType();
	}
	
	public List<ComboBoxItem> getPersonName(String type){
		return mapper.getPersonName(type);
	}
	
	public List<Map<String,String>> getChartData(QueryParams ticket){
		return mapper.getChartData(ticket);
	}
	
	public List<Map<String,String>> getExceptionBarData(QueryParams ticket){
		return mapper.getExceptionBarData(ticket);
	}
	
	public List<Map<String,String>> getPieData(QueryParams ticket){
		return mapper.getPieData(ticket);
	}
	
	public List<TicketCheck> selectById(String id) {
		return mapper.selectById(id);
	}
	
	public List<Map<String, String>> selectChildById(String id,String batchTime) {
		return mapper.selectChildById(id,batchTime);
	}
	
	public TicketCheckResult getResultById(String id,String batchTime){
		return mapper.getResultById(id,batchTime);
	}
	
	public List<ComboBoxItem> getAllBatch() {
		return mapper.getAllBatch();
	}
	
	public List<Map<String,String>> getAllUser(){
		return mapper.getAllUser();
	}
	
	public List<Map<String,String>> getBatchsForManage(){
		return mapper.getBatchsForManage();
	}
	
	public void delBatch(String batch){
		mapper.delResBatch(batch);
		mapper.delParentBatch(batch);
		mapper.delChildBatch(batch);
	}
	
	public String handleParam(String param){
		if("hg合格".equals(param)) return "t.check_result = '0'";
		else if ("hg不合格".equals(param)) return "(t.check_result = '1' or t.check_result = '2') ";
		//else if("hg异常".equals(param)) return "t.check_result = '2'";
		else if("gf规范".equals(param)) return "t.standard_result = '0'";
		else if ("gf不规范".equals(param)) return "(t.standard_result = '1' or t.standard_result = '2')";
		//else if("gf异常".equals(param)) return "t.standard_result = '2'";
		
		else if ("hg判据1".equals(param)) return "(t.misuse_ticket = '1' or t.misuse_ticket = '2')";
		else if("hg判据2".equals(param)) return "(t.beyond_plan = '1' or t.beyond_plan = '2' )";
		else if("hg判据3".equals(param)) return "(t.keyword_error = '1' or t.keyword_error = '2' )";
		else if ("hg判据4".equals(param)) return "1=2";
		else if("hg判据5".equals(param)) return "(t.work_member_count = '1' or t.work_member_count = '2')";
		else if ("hg判据6".equals(param)) return "(t.fillin_task_error = '1' or t.fillin_task_error = '2')";
		else if("hg判据7".equals(param)) return "(t.fillin_safe_error = '1' or t.fillin_safe_error = '2')";
		else if("hg判据8".equals(param)) return "(t.double_issue_error = '1' or t.double_issue_error = '2')";
		else if ("hg判据9".equals(param)) return "(t.empty_permiss_time = '1' or t.empty_permiss_time = '2')";
		else if("hg判据10".equals(param)) return "(t.handle_change = '1' or t.handle_change = '2')";
		else if ("hg判据11".equals(param)) return "(t.handle_delay = '1' or t.handle_delay = '2')";
		else if("hg判据12".equals(param)) return "1=2";
		else if("hg判据13".equals(param)) return "(t.final_content_error = '1' or t.final_content_error = '2')";
		else if ("hg判据14".equals(param)) return "(t.licensor_no_sign = '1' or t.licensor_no_sign = '2')";
		else if("hg判据15".equals(param)) return "(t.keep_multiple = '1' or t.keep_multiple = '2')";
		else if ("hg判据16".equals(param)) return "(t.sign_error = '1' or t.sign_error = '2')";
		else if("hg判据17".equals(param)) return "1=2";
		
		else if ("gf判据1".equals(param)) return "1=2";
		else if("gf判据2".equals(param)) return "1=2";
		else if("gf判据3".equals(param)) return "1=2";
		else if ("gf判据4".equals(param)) return "(t.no_save = '1' or t.no_save = '2')";
		else if("gf判据5".equals(param)) return "(t.nonstandard_word = '1' or t.nonstandard_word = '2')";
		else if ("gf判据6".equals(param)) return "1=2";
		else if("gf判据7".equals(param)) return "1=2";
		
		else if ("hgyc判据1".equals(param)) return "t.misuse_ticket = '2'";
		else if("hgyc判据2".equals(param)) return "t.beyond_plan = '2'";
		else if("hgyc判据3".equals(param)) return "t.keyword_error = '2'";
		else if ("hgyc判据4".equals(param)) return "1=2";
		else if("hgyc判据5".equals(param)) return "t.work_member_count = '2'";
		else if ("hgyc判据6".equals(param)) return "t.fillin_task_error = '2'";
		else if("hgyc判据7".equals(param)) return "t.fillin_safe_error = '2'";
		else if("hgyc判据8".equals(param)) return "t.double_issue_error = '2'";
		else if ("hgyc判据9".equals(param)) return "t.empty_permiss_time = '2'";
		else if("hgyc判据10".equals(param)) return "t.handle_change = '2'";
		else if ("hgyc判据11".equals(param)) return "t.handle_delay = '2'";
		else if("hgyc判据12".equals(param)) return "1=2";
		else if("hgyc判据13".equals(param)) return "t.final_content_error = '2'";
		else if ("hgyc判据14".equals(param)) return "t.licensor_no_sign = '2'";
		else if("hgyc判据15".equals(param)) return "t.keep_multiple = '2'";
		else if ("hgyc判据16".equals(param)) return "t.sign_error = '2'";
		else if("hgyc判据17".equals(param)) return "1=2";
		
		else if ("gfyc判据1".equals(param)) return "1=2";
		else if("gfyc判据2".equals(param)) return "1=2";
		else if("gfyc判据3".equals(param)) return "1=2";
		else if ("gfyc判据4".equals(param)) return "t.no_save = '2'";
		else if("gfyc判据5".equals(param)) return "t.nonstandard_word = '2'";
		else if ("gfyc判据6".equals(param)) return "1=2";
		else if("gfyc判据7".equals(param)) return "1=2";
		
		else return "";
	}

}
