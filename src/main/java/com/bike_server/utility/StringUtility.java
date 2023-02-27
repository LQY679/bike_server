package com.bike_server.utility;

public class StringUtility {

    public static String replaceFirst(String str, String oldStr, String newStr) {
        int index = str.indexOf(oldStr);
        if (index == -1) {
            return str; // 如果找不到要替换的子字符串，则直接返回原始字符串
        }
        String before = str.substring(0, index);
        String after = str.substring(index + oldStr.length());
        return before + newStr + after;
    }
}
