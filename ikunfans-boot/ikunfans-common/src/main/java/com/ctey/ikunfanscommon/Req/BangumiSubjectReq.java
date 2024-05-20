package com.ctey.ikunfanscommon.Req;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectFilterData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangumiSubjectReq {
    private String keyword;
    private String sort;
    private SubjectFilterData filter;
}
