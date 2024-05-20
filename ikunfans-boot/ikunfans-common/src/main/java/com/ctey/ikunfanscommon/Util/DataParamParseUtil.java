package com.ctey.ikunfanscommon.Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.ctey.ikunfanscommon.Static.WebModuleStatic.TOKEN_PREFIX;

public class DataParamParseUtil {
    public static <T> Predicate<T> modelDistinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static String parseAuthToToken(String auth) {
        return auth.replace(TOKEN_PREFIX, "");
    }

}
