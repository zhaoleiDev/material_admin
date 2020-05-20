package com.zhaolei.material.admin.web.interceptor;

import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.domain.exception.NoPermissionRunTimeException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 组织的修改操作只能是特定的人才可以
 * @author ZHAOLEI
 */
public class OrgOperationInterceptor implements HandlerInterceptor {
    private static Set<String> orgOprationSet = new HashSet<>() ;
    static{
        orgOprationSet.add("222016333210145");
    }
    /**
     * 在进入到前端控制器以前执行
     * @param request    请求
     * @param response   响应
     * @param handler   对应的处理器
     * @return      返回值
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String user = LoginContextUtils.getStNum();
        if(orgOprationSet.contains(user)){
            return true;
        }
        throw new NoPermissionRunTimeException(NoPermissionRunTimeException.NO_PERMISSION_OPERATE_ORG);
    }

}
