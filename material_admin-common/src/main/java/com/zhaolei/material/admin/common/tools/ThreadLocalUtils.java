package com.zhaolei.material.admin.common.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHAOLEI
 */
public class ThreadLocalUtils {
    private static ThreadLocal<Map<String,Object>> threadLocal ;
    static{
        threadLocal = ThreadLocal.withInitial(() -> new HashMap<>(2));
    }

    public static Object get(String key){
        Map map = threadLocal.get();
        return map.get(key);
    }

    public static void set(Map<String,Object> map){
        threadLocal.set(map);
    }
    public static void remove(){
        threadLocal.remove();
    }
}
