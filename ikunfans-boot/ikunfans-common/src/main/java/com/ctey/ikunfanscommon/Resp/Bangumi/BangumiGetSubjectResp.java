package com.ctey.ikunfanscommon.Resp.Bangumi;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectGetItemData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangumiGetSubjectResp {
    private int results;
    private List<SubjectGetItemData> list;
}
