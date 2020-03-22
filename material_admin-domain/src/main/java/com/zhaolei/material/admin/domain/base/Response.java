package com.zhaolei.material.admin.domain.base;

import java.io.Serializable;

/**
 * 与前端交互实体
 * @author ZHAOLEI
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 5612140254005941933L;
    private int code;
    private String msg;
    private T data;

    public  static Response success(Object data){
        Response res = setResponseEnum(ResponseEnum.SUCCESS);
        res.setData(data);
        return res;
    }
    public static Response success(){
        return setResponseEnum(ResponseEnum.SUCCESS);
    }
    public static Response error(){
        return setResponseEnum(ResponseEnum.ERROR);
    }

    public static Response addInfo(ResponseEnum responseEnum,Object data){
        Response res = setResponseEnum(responseEnum);
        res.setData(data);
        return res;
    }

    private static Response setResponseEnum(ResponseEnum responseEnum){
        Response res = new Response();
        res.setCode(responseEnum.getCode());
        res.setMsg(responseEnum.getMsg());
        return res;
    }


    public Response() {
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }
}
