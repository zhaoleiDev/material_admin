package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.common.tools.CookieUtils;
import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.common.tools.TimeUtils;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.UserVO;
import com.zhaolei.material.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private static final String COOKIE_NAME_USER = "user";
    private static final String COOKIE_NAME_LOGIN_TOKEN = "loginToken";

    @RequestMapping("/login")
    public Response login(@RequestParam("stNum") String stNum, @RequestParam("pwd")String password, HttpServletRequest request, HttpServletResponse response){
        UserDO userDo = userService.getUerByStNum(stNum);
        if(userDo == null){
            return Response.addInfo(ResponseEnum.NOT_REGISTERED);
        }
        if(!userDo.getUserPassword().equals(password)){
            return Response.addInfo(ResponseEnum.ERROR_PASSWORD);
        }
        String token = DigestUtils.md5(stNum);
        Cookie loginToken = CookieUtils.createCookie(COOKIE_NAME_LOGIN_TOKEN,token,TimeUtils.ONE_DAY,"/");
        Cookie user = CookieUtils.createCookie(COOKIE_NAME_USER,stNum,TimeUtils.ONE_DAY,"/");
        response.addCookie(loginToken);
        response.addCookie(user);
        return Response.success();
    }

    @RequestMapping("/logout")
    public Response logout(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        CookieUtils.clearCookie(response,cookies,COOKIE_NAME_LOGIN_TOKEN,COOKIE_NAME_USER);
        return Response.success();
    }

    @PostMapping("/registered")
    public Response registered(@RequestBody UserVO userVO){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO,userDO);
        return Response.parseRespons(userService.registered(userDO));
    }

    @RequestMapping("/getInfo")
    public Response getInfo(String stNum){
        return Response.success(userService.getUerByStNum(stNum));
    }



}
