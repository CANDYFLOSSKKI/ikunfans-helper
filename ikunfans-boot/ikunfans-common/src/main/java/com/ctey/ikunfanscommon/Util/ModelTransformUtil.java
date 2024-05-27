package com.ctey.ikunfanscommon.Util;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectCalendarData;
import com.ctey.ikunfanscommon.Entity.SubjectMiniInfoData;
import com.ctey.ikunfanscommon.Req.BangumiPostSubjectReq;
import com.ctey.ikunfanscommon.Resp.DefaultDataResp;
import com.ctey.ikunfanscommon.Resp.DefaultMsgResp;
import com.ctey.ikunfanscommon.Resp.SubjectCalendarResp;

import java.util.ArrayList;

import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.BANGUMI_PRESET_FILTER_DATA;
import static com.ctey.ikunfanscommon.Static.WebModuleStatic.WEB_RESP_CODE_FAILURE;
import static com.ctey.ikunfanscommon.Static.WebModuleStatic.WEB_RESP_CODE_SUCCESS;

public class ModelTransformUtil {
    public static DefaultDataResp getDefaultDataInstance(String info, Object data) {
        boolean flag = data != null;
        int code = flag ? WEB_RESP_CODE_SUCCESS : WEB_RESP_CODE_FAILURE;
        return new DefaultDataResp(code, flag, info, data);
    }

    public static DefaultMsgResp getDefaultMsgInstance(boolean flag, String info, String msg) {
        int code = flag ? WEB_RESP_CODE_SUCCESS : WEB_RESP_CODE_FAILURE;
        return new DefaultMsgResp(code, flag, info, msg);
    }

    public static BangumiPostSubjectReq getDefaultBangumiSubjectReq(String keyword) {
        return new BangumiPostSubjectReq(keyword, "rank", BANGUMI_PRESET_FILTER_DATA);
    }

    public static SubjectCalendarResp getDefaultSubjectCalendarResp() {
        return new SubjectCalendarResp(
                DateParamParseUtil.getTodayParseStr(),
                DateParamParseUtil.getTodayWeekCN(),
                DateParamParseUtil.getTodayWeekJP(),
                new ArrayList<>()
        );
    }

    public static SubjectMiniInfoData subjectCalendarToMiniInfo(SubjectCalendarData data) {
        return new SubjectMiniInfoData(
                Long.valueOf(data.getId()),
                data.getName(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getAirDate(),
                data.getAirWeekday(),
                data.getImages() == null ? "" : data.getImages().getGrid(),
                data.getRating() == null ? 0.0 : data.getRating().getScore(),
                data.getRank()
        );
    }

}
