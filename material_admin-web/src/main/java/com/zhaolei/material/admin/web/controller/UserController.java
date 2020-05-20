package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.common.tools.*;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.UserResponse;
import com.zhaolei.material.admin.domain.vo.UserVO;
import com.zhaolei.material.admin.service.OrganizationService;
import com.zhaolei.material.admin.service.UserService;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/login")
    public Response login(@RequestParam("stNum") String stNum, @RequestParam("pwd")String password, HttpServletRequest request, HttpServletResponse response){
        //查库
        UserDO userDo = userService.getUerByStNum(stNum);
      if(userDo == null || userDo.getStatusInfo().equals(0)){
            return Response.addInfo(ResponseEnum.NOT_REGISTERED);
        }
        if(!userDo.getUserPassword().equals(password)){
            return Response.addInfo(ResponseEnum.ERROR_PASSWORD);
        }
        //将学号与数据库id、组织、当前时间拼接在一起  学号===数据库id===组织===当前时间   当前时间用于保证loginToken是一个变化的值
        String userStr = stNum+ ConstantUtils.SPLIT_COOKIE_USER+userDo.getId()+ ConstantUtils.SPLIT_COOKIE_USER+userDo.getOrganization()+ ConstantUtils.SPLIT_COOKIE_USER+Math.random();
        String userBase64 = Base64Utils.encodeToString(userStr.getBytes());
        String token = DigestUtils.md5(userBase64);
        //往cookie中添加loginToken
        Cookie loginToken = CookieUtils.createCookie(ConstantUtils.COOKIE_LOGIN_TOKEN,token, TimeUtils.ONE_HOURS_M,ConstantUtils.DEFAULT_COOKIE_PATH);
        //往cookie中添加user
        Cookie user = CookieUtils.createCookie(ConstantUtils.COOKIE_NAME,userBase64, TimeUtils.ONE_HOURS_M,ConstantUtils.DEFAULT_COOKIE_PATH);
        response.addCookie(loginToken);
        response.addCookie(user);
        return Response.success();
    }

    /**
     * 退出登录
     * @param request 请求
     * @param response 响应
     * @return 返回结果
     */
    @RequestMapping("/signOut")
    public Response signOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        CookieUtils.clearCookie(response,cookies,ConstantUtils.COOKIE_LOGIN_TOKEN,ConstantUtils.COOKIE_NAME);
        return Response.success();
    }

    /**
     * 注销用户
     * @param request 请求
     * @param response 响应
     * @return 返回
     */
    @RequestMapping("/logout")
    public Response logout(HttpServletRequest request,HttpServletResponse response){
        if(organizationService.isPrincipalByStNum(LoginContextUtils.getStNum())){
            return Response.addInfo(ResponseEnum.NOT_HANDOVER);
        }
        //清除cookie信息
        Cookie[] cookies = request.getCookies();
        CookieUtils.clearCookie(response,cookies,ConstantUtils.COOKIE_LOGIN_TOKEN,ConstantUtils.COOKIE_NAME);
        //修改数据库中的数据
        Integer  id = LoginContextUtils.getId();
        if(userService.deleteById(id)){
            return Response.success();
        }
        return Response.fail();
    }

    @PostMapping("/registered")
    public Response registered(@RequestBody UserVO userVO){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO,userDO);
        return Response.parseResponse(userService.registered(userDO));
    }

    @RequestMapping("/getInfo")
    public Response getInfo(){
        String stNum = (String)ThreadLocalUtils.get("stNum");
        log.info("登录的用户:{}",stNum);
        long time = System.currentTimeMillis();
        UserDO userDO = userService.getUerByStNum(stNum);
        log.info("通过学号查询用户信息耗时{}",System.currentTimeMillis()-time);
        //组织令牌不对外展示
        userDO.setOrganizationToken("******");
        userDO.setUserPassword(ConstantUtils.OUT_PASSWORD);
        return Response.success(userDO);
    }

    @RequestMapping("update")
    public Response update(UserVO userVO){
        //若传进来的密码为默认返回前端的密码，则没有改变密码
        if(ConstantUtils.OUT_PASSWORD.equals(userVO.getUserPassword())){
            userVO.setUserPassword(null);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO,userDO);
        if(userService.updateById(userDO)){
            return Response.success();
        }
        return Response.fail();
    }

    @RequestMapping("/getOrgMember")
    public Response getOrgMember(){
        String org = LoginContextUtils.getOrgName();
        List<UserDO> userDOList = userService.getOrgMember(org);
        List<UserResponse> userResponseList = new ArrayList<>();
        for(UserDO userDO:userDOList){
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(userDO,userResponse);
            userResponseList.add(userResponse);
        }
        return Response.success(userResponseList);
    }

    @RequestMapping("/deleteMember")
    public Response deleteMember(Integer id){
        //检查是否是组织负责人，只有组织负责人才有删除成员的权限
        String stNum = LoginContextUtils.getStNum();
        if(!organizationService.isPrincipalByStNum(stNum)){
            return Response.addInfo(ResponseEnum.NOT_PERMISSION);
        }
        log.info("用户学号:{} 删除用户主键id:{}",stNum,id);
        if(userService.deleteById(id)){
            return Response.success();
        }
        return Response.fail();
    }



}
