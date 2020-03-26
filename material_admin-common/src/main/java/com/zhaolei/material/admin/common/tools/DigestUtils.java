package com.zhaolei.material.admin.common.tools;

import java.security.MessageDigest;

public class DigestUtils {
    private static final String SLAT = "zl_material";
    private static final String MD5 = "MD5";
    private static final String SHA = "SHA";
    private static final char[] HEX_ARRY = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};

    public static String md5(String str){
        return digest(str,MD5);
    }
    public static String sha(String str){
        return digest(str,SHA);
    }

    private static String digest(String str,String algorithm) {
        try{
            MessageDigest md = MessageDigest.getInstance(algorithm);
            str = str + SLAT;
            md.update(str.getBytes("utf-8"));
            byte[] result = md.digest();
            md.reset();
            return convertString(result).toString();
        }catch(Exception e){
            throw new RuntimeException("DigestUtils出错");
        }
    }

    /**
     * 如果直接用字节数组到字符串会出现不常见的符号
     * @return
     */
    private static StringBuilder convertString(byte[] array){
        StringBuilder bl = new StringBuilder();
        for(byte b : array){
            //第四位的值
            int low = b & 15;
            //高四位的值
            int hig = (b >> 4) & 15;
            bl.append(HEX_ARRY[low]).append(HEX_ARRY[hig]);
        }
        return bl;
    }

}
