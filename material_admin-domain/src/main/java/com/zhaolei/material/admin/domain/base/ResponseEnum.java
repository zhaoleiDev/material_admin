package com.zhaolei.material.admin.domain.base;

/**
 * @author ZHAOLEI
 */

public enum ResponseEnum {
    /**
     *
     */
    SUCCESS(200,"成功"),
    ERROR(500,"服务端异常")
    ;
    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
