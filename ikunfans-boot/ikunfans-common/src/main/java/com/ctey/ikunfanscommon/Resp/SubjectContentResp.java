package com.ctey.ikunfanscommon.Resp;

import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectContentResp {
    private String propose;
    private List<String> keywords;
    private List<SubjectInfoData> subjects;
}
