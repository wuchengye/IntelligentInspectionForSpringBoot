package com.bda.bdaqm.risk.mapper;

import com.bda.bdaqm.risk.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllMapper {
    List<NotDubious> getNoJugeDetail(RequestModel requestModel);
    List<NotDubious> getNoJugeDetail2(RequestModel requestModel,@Param("allUserIds") List<String> allUserIds);

    List<ComplaintSession> getComJugeDetail(RequestModel requestModel);
    List<ComplaintSession> getComJugeDetail2(RequestModel requestModel,@Param("allUserIds") List<String> allUserIds);

    List<TabooSession> getTabJugeDetail(RequestModel requestModel);
    List<TabooSession> getTabJugeDetail2(RequestModel requestModel,@Param("allUserIds") List<String> allUserIds);

    void updateComplaintCheckResult(RequestModel requestModel);
    void insertCheckHistory(RequestModel requestModel);
    List<SessionDetail> selectQuestionSessionDetail(@Param("sessionId") String sessionId);
    List<SessionDetail> selectNoquestionSessionDetail(@Param("sessionId") String sessionId);
}
