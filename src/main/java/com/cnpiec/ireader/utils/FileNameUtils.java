package com.cnpiec.ireader.utils;

import org.apache.commons.lang3.StringUtils;

public class FileNameUtils {
    public static String replaceSpecialCharacters(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        String replace = StringUtils.replaceEach(fileName, new String[] { "/", "?" }, new String[] { "-", "？" });
        return replace;
    }

    public static void main(String[] args) {
        String fileName = "第338/339章 是?你！";
        String replace = StringUtils.replaceEach(fileName, new String[] { "/", "?" }, new String[] { "-", "？" });
        System.out.println(replace);
    }
}
