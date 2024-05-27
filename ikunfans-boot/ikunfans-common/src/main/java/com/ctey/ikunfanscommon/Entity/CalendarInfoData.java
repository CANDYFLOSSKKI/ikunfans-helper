package com.ctey.ikunfanscommon.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarInfoData {
    private Integer index;
    private String cn;
    private String jp;
    private List<SubjectMiniInfoData> subjects;
}
