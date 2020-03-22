package com.zhaolei.material.admin.web.handler;

import com.zhaolei.material.admin.domain.base.Response;
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
    public Response arithmeticException(ArithmeticException e){
        log.error("发生异常1",e);
        return Response.success();

    }

    @ExceptionHandler
    public Response unknownException(Exception e){
        log.error("发生异常",e);
        return Response.error();

    }
}
