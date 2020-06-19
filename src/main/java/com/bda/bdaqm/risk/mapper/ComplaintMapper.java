package com.bda.bdaqm.risk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.TabooSession;
import tk.mybatis.mapper.common.Mapper;

public interface ComplaintMapper extends Mapper<ComplaintSession> {
	ComplaintSession getComplaintSessionById(@Param("sessionId")String sessionId);
	int updateFileNameAndFilePath(@Param("sessionId") String sessionId, @Param("fileName") String fileName, @Param("filePath") String filePath);
}
