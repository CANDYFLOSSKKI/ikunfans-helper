package com.ctey.ikunfanscommon.Util;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateParamParseUtil {
    public static String getTodayParseStr() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.getYear() + "年" + dateTime.getMonthValue() + "月" + dateTime.getDayOfMonth() + "日";
    }

    public static String getTodayWeekCN() {
        return LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
    }

    public static String getTodayWeekJP() {
        return LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPAN);
    }

}
