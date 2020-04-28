package com.bda.bdaqm.electric.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.electric.model.Statistics;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	public List<Map<String,String>> SelectOperation(){
		List<Map<String,String>> result =testMapper.SelectOperation();
		return result;
	}
	public List<Map<String,String>> SelectWorkLast(){
		List<Map<String,String>> result =testMapper.SelectWorkLast();
		return result;
	}
	public String SelectLineWorking(String type,String date){
		String result =testMapper.SelectLineWorking(type, date);
		return result;
	}
	public String SelectLowVo(String type,String date){
		String result =testMapper.SelectLowVo(type, date);
		return result;
	}
	public String SelectUrgent(String type,String date){
		String result =testMapper.SelectUrgent(type, date);
		return result;
	}
	public String SelectStationOne(String type,String date){
		String result =testMapper.SelectStationOne(type, date);
		return result;
	}
	public String SelectStationTwo(String type,String date){
		String result =testMapper.SelectStationTwo(type, date);
		return result;
	}
	public String SelectStationThree(String type,String date){
		String result =testMapper.SelectStationThree(type, date);
		return result;
	}
	public String SelectLineOne(String type,String date){
		String result =testMapper.SelectLineOne(type, date);
		return result;
	}
	public String SelectLineTwo(String type,String date){
		String result =testMapper.SelectLineTwo(type, date);
		return result;
	}
	public String SelectFireOne(String type,String date){
		String result =testMapper.SelectFireOne(type, date);
		return result;
	}
	public String SelectFireTwo(String type,String date){
		String result =testMapper.SelectFireTwo(type, date);
		return result;
	}
	public void addData(List<Statistics> result){
		testMapper.addData(result);
	}

}
