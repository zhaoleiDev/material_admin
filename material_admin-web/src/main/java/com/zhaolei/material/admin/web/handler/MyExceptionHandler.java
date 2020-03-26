package com.zhaolei.material.admin.web.handler;

import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.exception.NotLoginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author ZHAOLEI
 */
@ControllerAdvice
@ResponseBody
public class MyExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler
    public Response notLoginException(NotLoginRuntimeException e){
        return Response.addInfo(ResponseEnum.NOT_LOGIN);
    }


    @ExceptionHandler
    public Response unknownException(Exception e){
        log.error("发生异常",e);
        return Response.error();

    }
}
