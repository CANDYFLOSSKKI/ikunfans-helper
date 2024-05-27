package com.ctey.ikunfanscommon.Util;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectGetItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.ALIYUN_ITEM_LIMIT;
import static com.ctey.ikunfanscommon.Static.WebModuleStatic.TOKEN_PREFIX;

public class DataParamParseUtil {
    public static <T> Predicate<T> filterDistinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static Boolean filterSubjectPostItem(SubjectPostItemData data) {
        return data.getRank() != 0
                && !CollectionEmptyUtil.forString(data.getSummary())
                && !data.getName().contains("OVA")
                && !data.getName().contains("OAD");
    }

    public static Boolean filterSubjectGetItem(SubjectGetItemData data) {
        return data.getRank() != 0
                && !CollectionEmptyUtil.forString(data.getSummary())
                && !data.getName().contains("OVA")
                && !data.getName().contains("OAD");
    }

    public static String parseAuthToToken(String auth) {
        return auth.replace(TOKEN_PREFIX, "");
    }

    public static List<String> parseCallBackKeywords(String msg) {
        return Pattern.compile("《([^》]+)》").matcher(msg).results()
                .map(result -> result.group(1))
                .limit(ALIYUN_ITEM_LIMIT)
                .toList();
    }

    public static String filterUrlEncode(String str) {
        return str.replace("%7E", "%EF%BD%9E")
                .replace("%3A", "%EF%BC%9A");
    }

    public static String parseSimpleContent(String content) {
        return content.trim();
    }

}
