package com.bda.bdaqm.risk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.model.TabooSession;
import tk.mybatis.mapper.common.Mapper;

public interface UsedTabooMapper extends Mapper<TabooSession> {
	TabooSession getTabooSessionById(@Param("sessionId")String sessionId);
	int updateFileNameAndFilePath(@Param("sessionId") String sessionId, @Param("fileName") String fileName, @Param("filePath") String filePath);
}
