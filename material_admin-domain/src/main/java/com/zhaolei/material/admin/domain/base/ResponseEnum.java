package com.zhaolei.material.admin.domain.base;

/**
 * @author ZHAOLEI
 */

public enum ResponseEnum {
    /**
     *
     */
    SUCCESS(200,"成功"),
    ERROR(500,"服务端异常"),
    NOT_REGISTERED(410,"用户未注册"),
    NOT_LOGIN(411,"用户未登录"),
    ERROR_PASSWORD(412,"密码错误"),
    ERROR_PARAM(413,"参数错误"),
    ERROR_ORG_TOKEN(414,"组织令牌错误"),
    NOT_EXIST_ORG(415,"组织未备案"),
    NOT_PERMISSION_OPERATE_ORG(416,"无组织操作权限"),
    NOT_PERMISSION(417,"无权限"),
    FAIL_OPERATION(418,"操作失败"),
    BORROWER_NOT_REGISTERED(419,"对方未注册,借出失败"),
    STOCK_LOW(420,"库存不够"),
    MATERIAL_NOT_EXIST(421,"物资不存在"),
    PRINCIPAL_NOT_REGISTERED(422,"负责人未注册"),
    FAIL_SEARCH(423,"查询失败"),
    PRINCIPAL_EXIT(424,"该学号已是负责人")
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
