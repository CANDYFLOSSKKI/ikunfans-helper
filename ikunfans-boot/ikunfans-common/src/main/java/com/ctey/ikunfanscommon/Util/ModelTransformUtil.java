package com.ctey.ikunfanscommon.Util;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectTagData;
import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Req.BangumiSubjectReq;
import com.ctey.ikunfanscommon.Resp.DefaultDataResp;
import com.ctey.ikunfanscommon.Resp.DefaultMsgResp;

import static com.ctey.ikunfanscommon.Static.ConnectModuleStatic.BANGUMI_PRESET_FILTER_DATA;
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

    public static BangumiSubjectReq getDefaultBangumiSubjectReq(String keyword) {
        return new BangumiSubjectReq(keyword, "rank", BANGUMI_PRESET_FILTER_DATA);
    }

    public static SubjectInfoData subjectItemDataToInfo(SubjectPostItemData subjectPostItemData) {
        return new SubjectInfoData(
                Long.valueOf(subjectPostItemData.getId()),
                subjectPostItemData.getName(),
                subjectPostItemData.getNameCN(),
                subjectPostItemData.getDate(),
                subjectPostItemData.getImage(),
                subjectPostItemData.getSummary(),
                subjectPostItemData.getScore(),
                subjectPostItemData.getRank(),
                subjectPostItemData.getTags().stream().limit(3).map(SubjectTagData::getName).toList()
        );
    }

}
