package com.ctey.ikunfanscommon.Resp.Bangumi;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangumiPostSubjectResp {
    private int total;
    private int limit;
    private int offset;
    private List<SubjectPostItemData> data;
}
