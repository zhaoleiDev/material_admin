package com.zhaolei.material.admin.web.interceptor;

import com.zhaolei.material.admin.common.tools.CookieUtils;
import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.common.tools.ThreadLocalUtils;
import com.zhaolei.material.admin.domain.exception.NotLoginRuntimeException;
import com.zhaolei.material.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZHAOLEI
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    /**
     * 在进入到前端控制器以前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user = CookieUtils.getValue(request.getCookies(),"user");
        String loginToken = CookieUtils.getValue(request.getCookies(),"loginToken");
        if(user == null || loginToken == null || !DigestUtils.md5(user).equals(loginToken)){
            throw new NotLoginRuntimeException();
        }
        ThreadLocalUtils.set(user);
        return true;
    }

    /**
     * postHandle()在生成视图之前
     * 在控制器处理完所有操作后，主要用于资源的释放
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalUtils.remove();
    }

}
