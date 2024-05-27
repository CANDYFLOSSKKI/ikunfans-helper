package com.ctey.ikunfanscommon.Static;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectFilterData;

import java.util.List;
import java.util.Map;

public class ConnModuleStatic {
    public static final String ALIYUN_MODEL = "XXXXX";
    public static final String ALIYUN_APPKEY = "XXXXX";
    public static final String ALIYUN_CONTENT_PREFIX = "XXXXX";
    public static final String BANGUMI_BASE_API = "https://api.bgm.tv";
    public static final Integer BANGUMI_ITEM_LIMIT = 5;
    public static final Integer ALIYUN_ITEM_LIMIT = 5;
    public static final SubjectFilterData BANGUMI_PRESET_FILTER_DATA = new SubjectFilterData(
            List.of(2),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            false
    );

    public static final Map<String, String> BANGUMI_PRESET_SEARCH_QUERY = Map.ofEntries(
            Map.entry("type", "2"),
            Map.entry("responseGroup", "large")
    );

}
