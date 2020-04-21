package com.bda.bdaqm.risk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.risk.model.NotDubious;
import com.bda.bdaqm.risk.model.SessionDetail;

import tk.mybatis.mapper.common.Mapper;

public interface DubiousMapper extends Mapper<NotDubious> {
	List<NotDubious> getDubiousData(NotDubious params);
	List<SessionDetail> getSessionDetail(@Param("sessionId")String sessionId);
	NotDubious getDubiousById(@Param("sessionId")String sessionId);
}
