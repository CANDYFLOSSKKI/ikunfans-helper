package com.ctey.ikunfanscommon.Util;

import java.util.Arrays;
import java.util.List;

public class MessagePrintUtil {
    public static void printCallBackKeyWords(List<String> keywords) {
        System.out.println("RECEIVED FROM QWEN: " + Arrays.toString(keywords.toArray()));
    }

}
