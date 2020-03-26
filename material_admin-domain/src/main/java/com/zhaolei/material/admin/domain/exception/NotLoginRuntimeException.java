package com.zhaolei.material.admin.domain.exception;

/**
 * @author ZHAOLEI
 */
public class NotLoginRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 2035719956037281090L;
    public NotLoginRuntimeException(){
        super();
    }
    public NotLoginRuntimeException(String str){
        super(str);
    }
    public NotLoginRuntimeException(String str, Throwable e){
        super(str,e);
    }
}
