package com.zhaolei.material.admin.domain.exception;

/**
 * @author ZHAOLEI
 */
public class NoPermissionRunTimeException extends RuntimeException {
    private static final long serialVersionUID = 6515867632687948146L;
    public static final String NO_PERMISSION_OPERATE_ORG = "无组织操作权限";
    public NoPermissionRunTimeException(){
        super();
    }
    public NoPermissionRunTimeException(String str){
        super(str);
    }
    public NoPermissionRunTimeException(String str, Throwable e){
        super(str,e);
    }
}
