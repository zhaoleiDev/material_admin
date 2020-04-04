package com.zhaolei.material.admin.domain.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 与前端交互实体
 * @JsonInclude(JsonInclude.Include.NON_NULL)：null字段不参与序列化
 * @author ZHAOLEI
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 5612140254005941933L;
    private int code;
    private String msg;
    private Page page;
    private T data;

    public static Response parseResponse(ServiceResponse serviceResponse){
        Response res = new Response();
        res.setMsg(serviceResponse.getMsg());
        res.setCode(serviceResponse.getCode());
        res.setData(serviceResponse.getData());
        res.setPage(serviceResponse.getPage());
        return res;
    }

    public  static Response success(Object data){
        Response res = setResponseEnum(ResponseEnum.SUCCESS);
        res.setData(data);
        return res;
    }
    public static Response success(){
        return setResponseEnum(ResponseEnum.SUCCESS);
    }

    /**
     * 操作失败，出现操作数据库中不存在记录的情况
     * @return response
     */
    public static Response fail(){
        return setResponseEnum(ResponseEnum.FAIL_OPERATION);
    }

    /**
     * 服务端发生了异常
     * @return response
     */
    public static Response error(){
        return setResponseEnum(ResponseEnum.ERROR);
    }

    public static Response addInfo(ResponseEnum responseEnum,Object data){
        Response res = setResponseEnum(responseEnum);
        res.setData(data);
        return res;
    }
    public static Response addInfo(ResponseEnum responseEnum){
        return setResponseEnum(responseEnum);
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
