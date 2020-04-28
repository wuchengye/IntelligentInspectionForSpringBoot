package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.ThpsDialogDetail;
import com.bda.bdaqm.electric.model.ThreePerson;

import tk.mybatis.mapper.common.Mapper;

public interface ThreePersonMapper extends Mapper<ThreePerson>{

	List<ThreePerson> getThreePersonData(Map<String,String>params);
	
	List<ThreePerson> getKeepTicketAmount(Map<String,String>params);
	
	List<ThpsDialogDetail> getOriginalData(@Param("ticketNum")String ticketNum,@Param("personName")String personName);
	
	List<Map<String,Object>> getAmountOfTypeFromKeep(Map<String,String> params);
}
