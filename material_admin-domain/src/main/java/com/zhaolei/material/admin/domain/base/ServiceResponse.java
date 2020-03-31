package com.zhaolei.material.admin.domain.base;

/**
 * @author ZHAOLEI
 */
public class ServiceResponse<T> {
    private int code;
    private String msg;
    private T data;


    public static ServiceResponse addInfo(ResponseEnum responseEnum,Object data){
        ServiceResponse serviceResponse = setResponseEnum(responseEnum);
        serviceResponse.setData(data);
        return serviceResponse;
    }

    public static ServiceResponse addInfo(ResponseEnum responseEnum){
        return setResponseEnum(responseEnum);
    }

    private static ServiceResponse setResponseEnum(ResponseEnum responseEnum){
        ServiceResponse res = new ServiceResponse();
        res.setCode(responseEnum.getCode());
        res.setMsg(responseEnum.getMsg());
        return res;
    }

    private void setCode(int code) {
        this.code = code;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    private void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
