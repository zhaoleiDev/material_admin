package com.zhaolei.material.admin.web.interceptor;

import com.zhaolei.material.admin.common.tools.CookieUtils;
import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.common.tools.ThreadLocalUtils;
import com.zhaolei.material.admin.domain.exception.NotLoginRuntimeException;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author ZHAOLEI
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在进入到前端控制器以前执行
     * @param request    请求
     * @param response   响应
     * @param handler   对应的处理器
     * @return      返回值
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String user = CookieUtils.getValue(request.getCookies(),"user");
        String loginToken = CookieUtils.getValue(request.getCookies(),"loginToken");
        if(user == null || loginToken == null || !DigestUtils.md5(user).equals(loginToken)){
            throw new NotLoginRuntimeException();
        }
        ThreadLocalUtils.set(user);
        return true;
    }

    /**
     * postHandle()在执行完controller之后生成视图之前
     * 在视图渲染之后
     * @param request  请求
     * @param response  响应
     * @param handler   处理器
     * @param ex    异常
     */
    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        ThreadLocalUtils.remove();
    }

}
