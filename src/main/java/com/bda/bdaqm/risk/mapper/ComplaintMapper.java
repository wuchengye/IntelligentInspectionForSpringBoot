package com.bda.bdaqm.risk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.TabooSession;
import tk.mybatis.mapper.common.Mapper;

public interface ComplaintMapper extends Mapper<ComplaintSession> {
	List<ComplaintSession> getComplaintJudgeDetail(ComplaintSession params);
	ComplaintSession getComplaintSessionById(@Param("sessionId")String sessionId);
	int updateComplaintCheckResult(ComplaintSession params);
	int insertCheckHistory(ComplaintSession params);
}
