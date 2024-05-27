package com.ctey.ikunfanscommon.Entity.Bangumi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarItemData {
    private WeekDayData weekday;
    private List<SubjectCalendarData> items;
}
