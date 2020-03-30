package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.common.tools.*;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.UserVO;
import com.zhaolei.material.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    private static final String COOKIE_NAME_USER = "user";
    private static final String COOKIE_NAME_LOGIN_TOKEN = "loginToken";

    @RequestMapping("/login")
    public Response login(@RequestParam("stNum") String stNum, @RequestParam("pwd")String password, HttpServletRequest request, HttpServletResponse response){
        long time1 = System.currentTimeMillis();
        UserDO userDo = userService.getUerByStNum(stNum);
        log.info("查询数据库,耗时:{}",System.currentTimeMillis()-time1);
        if(userDo == null){
            return Response.addInfo(ResponseEnum.NOT_REGISTERED);
        }
        if(!userDo.getUserPassword().equals(password)){
            return Response.addInfo(ResponseEnum.ERROR_PASSWORD);
        }
        //将学号与数据库id拼接在一起
        String userStr = stNum+ ConstantUtils.SPLIT_COOKIE_USER+userDo.getId();
        log.info("原始字符串为:{}",userStr);
        String userBase64 = Base64Utils.encodeToString(userStr.getBytes());
        log.info("加密后字符串为:{}",userBase64);
        String token = DigestUtils.md5(userBase64);
        Cookie loginToken = CookieUtils.createCookie(COOKIE_NAME_LOGIN_TOKEN,token,TimeUtils.ONE_DAY,ConstantUtils.DEFAULT_COOKIE_PATH);
        Cookie user = CookieUtils.createCookie(COOKIE_NAME_USER,userBase64,TimeUtils.ONE_DAY,ConstantUtils.DEFAULT_COOKIE_PATH);
        response.addCookie(loginToken);
        response.addCookie(user);
        log.info("login方法返回,耗时:{}",System.currentTimeMillis()-time1);
        return Response.success();
    }

    @RequestMapping("/signOut")
    public Response signOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        CookieUtils.clearCookie(response,cookies,COOKIE_NAME_LOGIN_TOKEN,COOKIE_NAME_USER);
        return Response.success();
    }

    @RequestMapping("/logout")
    public Response logout(HttpServletRequest request,HttpServletResponse response){
        //清除cookie信息
        Cookie[] cookies = request.getCookies();
        CookieUtils.clearCookie(response,cookies,COOKIE_NAME_LOGIN_TOKEN,COOKIE_NAME_USER);
        //修改数据库中的数据
        Integer  id = (Integer)ThreadLocalUtils.get("id");
        String stNum = (String)ThreadLocalUtils.get("stNum");
        userService.deleteById(id,stNum);
        return Response.success();
    }

    @PostMapping("/registered")
    public Response registered(@RequestBody UserVO userVO){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO,userDO);
        return Response.parseRespons(userService.registered(userDO));
    }

    @RequestMapping("/getInfo")
    public Response getInfo(){
        String stNum = (String)ThreadLocalUtils.get("stNum");
        log.info("登录的用户:{}",stNum);
        UserDO userDO = userService.getUerByStNum(stNum);
        //组织令牌不对外展示
        userDO.setOrganizationToken("******");
        userDO.setUserPassword(ConstantUtils.OUT_PASSWORD);
        return Response.success(userDO);
    }

    @RequestMapping("update")
    public Response update(UserVO userVO){
        if(ConstantUtils.OUT_PASSWORD.equals(userVO.getUserPassword())){
            userVO.setUserPassword(null);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO,userDO);
        userService.updateById(userDO);
        return Response.success();
    }



}
