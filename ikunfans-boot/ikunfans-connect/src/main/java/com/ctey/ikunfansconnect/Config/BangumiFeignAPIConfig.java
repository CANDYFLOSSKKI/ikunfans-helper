package com.ctey.ikunfansconnect.Config;

import com.ctey.ikunfanscommon.Req.BangumiSubjectReq;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiGetSubjectResp;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiSubjectDetailResp;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiPostSubjectResp;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

@Headers("User-Agent: CANDYFLOSSKKI/ikunfans")
public interface BangumiFeignAPIConfig {
    @RequestLine("POST /v0/search/subjects")
    @Headers("Authorization: Bearer POLVFCJ1hFnE2pxnQVEBWe1kC4gPgkj4cXJMIsx3")
    BangumiPostSubjectResp searchSubjectsV0(BangumiSubjectReq req);

    @RequestLine("GET /search/subject/{keywords}")
    @Headers("Authorization: Bearer POLVFCJ1hFnE2pxnQVEBWe1kC4gPgkj4cXJMIsx3")
    BangumiGetSubjectResp searchSubjects(@Param("keywords") String keywords, @QueryMap Map<String, String> map);

    @RequestLine("GET /v0/subjects/{subject_id}")
    @Headers("Authorization: Bearer POLVFCJ1hFnE2pxnQVEBWe1kC4gPgkj4cXJMIsx3")
    BangumiSubjectDetailResp searchSubjectById(@Param("subject_id") int id);

}
