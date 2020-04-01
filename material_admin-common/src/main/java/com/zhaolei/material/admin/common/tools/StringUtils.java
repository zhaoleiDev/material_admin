package com.zhaolei.material.admin.common.tools;

/**
 * @author ZHAOLEI
 */
public class StringUtils {
    /**
     * 判断字符串是否为null或者""
     * @param str 字符串
     * @return 返回结果
     */
    public static boolean empty(String str){
        return (str == null || "".equals(str.trim()));
    }
}
