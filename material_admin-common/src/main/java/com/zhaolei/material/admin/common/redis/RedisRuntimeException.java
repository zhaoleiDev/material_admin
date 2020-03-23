package com.zhaolei.material.admin.common.redis;

/**
 * @author ZHAOLEI
 */
public class RedisRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8955600306844280281L;
    RedisRuntimeException(){
        super();
    }
    RedisRuntimeException(String str){
        super(str);
    }
    RedisRuntimeException(String str,Throwable e){
        super(str,e);
    }


}
