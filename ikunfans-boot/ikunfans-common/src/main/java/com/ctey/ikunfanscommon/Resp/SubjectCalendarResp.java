package com.ctey.ikunfanscommon.Resp;

import com.ctey.ikunfanscommon.Entity.CalendarInfoData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCalendarResp {
    private String date;
    private String cn;
    private String jp;
    @JsonProperty("infoList")
    private List<CalendarInfoData> infoList;
}
