package com.zhaolei.material.admin.common.tools;


/**
 * 用于获取用户的登录信息
 * @author ZHAOLEI
 */
public class LoginContextUtils {
    public static Integer getId(){
        return (Integer)ThreadLocalUtils.get("id");
    }
    public static String getStNum(){
        return (String)ThreadLocalUtils.get("stNum");
    }
    public static String getOrgName(){
        return (String)ThreadLocalUtils.get("orgName");
    }
}
