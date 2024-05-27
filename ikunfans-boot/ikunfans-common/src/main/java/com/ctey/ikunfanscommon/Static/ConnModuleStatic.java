package com.ctey.ikunfanscommon.Static;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectFilterData;

import java.util.List;
import java.util.Map;

public class ConnModuleStatic {
    public static final String ALIYUN_MODEL = "qwen1.5-110b-chat";
    public static final String ALIYUN_APPKEY = "sk-90c5041a8cb549ff9847819e36d14174";
    public static final String ALIYUN_CONTENT_PREFIX = "现在请你根据下面的要求，推荐若干部不同的动画(只包含中国和日本的TV连载动画),每部动画名只出现一次且用书名号《》括起来,其他的地方不允许出现《》,推荐的要求是:";
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
