package com.management.rd.utils;

/**
 * 检查验证工具类 非空、格式等
 * 
 * @author Wang Hanqing
 * 
 */
public class CheckUtil {
    
    /**
     * 非空校验
     * @param str
     * @return
     */
    public static boolean emptyCheck(String str) {
        if (str == null || "".equals(str)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 大小写不敏感校验
     * @param str
     * @return
     */
    public static boolean noSensitiveCheck(String str1, String str2) {
        if (str1.toLowerCase().equals(str2.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
