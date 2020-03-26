package com.zhaolei.material.admin.common.tools;

/**
 * @author ZHAOLEI
 */
public class ThreadLocalUtils {
    private static ThreadLocal threadLocal ;
    static{
        threadLocal = new ThreadLocal(){
            @Override
            protected Object initialValue() {
                return "";
            }
        } ;
    }

    public static Object get(){
        return threadLocal.get();
    }

    public static void set(Object value){
        threadLocal.set(value);
    }
    public static void remove(){
        threadLocal.remove();
    }
}
