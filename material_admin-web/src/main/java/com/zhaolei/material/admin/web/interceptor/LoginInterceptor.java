package com.zhaolei.material.admin.web.interceptor;

import com.zhaolei.material.admin.common.tools.ConstantUtils;
import com.zhaolei.material.admin.common.tools.CookieUtils;
import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.common.tools.ThreadLocalUtils;
import com.zhaolei.material.admin.domain.exception.NotLoginRuntimeException;
import org.springframework.lang.Nullable;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
        String userBase64 = CookieUtils.getValue(request.getCookies(),"user");
        String loginToken = CookieUtils.getValue(request.getCookies(),"loginToken");
        if(userBase64 == null || loginToken == null || !DigestUtils.md5(userBase64).equals(loginToken)){
            throw new NotLoginRuntimeException();
        }
        String userStr = new String(Base64Utils.decode(userBase64.getBytes()));
        String[] array = userStr.split(ConstantUtils.SPLIT_COOKIE_USER);
        Map<String,Object> map = new HashMap<>(4);
        map.put("stNum",array[0]);
        map.put("id",Integer.parseInt(array[1]));
        ThreadLocalUtils.set(map);
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
