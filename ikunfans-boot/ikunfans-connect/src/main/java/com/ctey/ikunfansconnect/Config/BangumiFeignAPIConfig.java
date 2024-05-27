package com.ctey.ikunfansconnect.Config;

import com.ctey.ikunfanscommon.Entity.Bangumi.CalendarItemData;
import com.ctey.ikunfanscommon.Req.BangumiPostSubjectReq;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiGetSubjectResp;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiPostSubjectResp;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiSubjectDetailResp;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;
@Headers("User-Agent: Apifox/1.0.0 (https://apifox.com)")
public interface BangumiFeignAPIConfig {
    @RequestLine("POST /v0/search/subjects")
    BangumiPostSubjectResp searchSubjectsV0(BangumiPostSubjectReq req);

    @RequestLine("GET /search/subject/{keywords}")
    BangumiGetSubjectResp searchSubjects(@Param("keywords") String keywords, @QueryMap Map<String, String> map);

    @RequestLine("GET /v0/subjects/{subject_id}")
    BangumiSubjectDetailResp searchSubjectById(@Param("subject_id") int id);

    @RequestLine("GET /calendar")
    List<CalendarItemData> getCalendar();
}
