package com.zhaolei.material.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.common.tools.StringUtils;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.LendBorrowMaterialVO;
import com.zhaolei.material.admin.service.LendBorrowMaterialService;
import com.zhaolei.material.admin.service.MaterialService;
import com.zhaolei.material.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping("/lendBorrow")
@Slf4j
public class LendBorrowMaterialController {
    @Autowired
    private LendBorrowMaterialService lendBorrowMaterialService;
    @Autowired
    private UserService userService;
    @Autowired
    private MaterialService materialService;

    @PostMapping("/lend")
    public Response lend(LendBorrowMaterialVO lendBorrowMaterialVO){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为:{}借出物资,信息:{}",stNum, JSON.toJSONString(lendBorrowMaterialVO));
        LendBorrowMaterialDO lendBorrowMaterialDO = new LendBorrowMaterialDO();
        BeanUtils.copyProperties(lendBorrowMaterialVO,lendBorrowMaterialDO);
        //重要参数检查
        String borrowStNum = lendBorrowMaterialDO.getBorrowStNum();
        Integer materialId = lendBorrowMaterialDO.getMaterialId();
        Integer num = lendBorrowMaterialDO.getNumber();
        if(borrowStNum == null || materialId == null || num <= 0){
            return Response.addInfo(ResponseEnum.ERROR_PARAM);
        }
        //设置借出者信息
        UserDO lender = new UserDO();
        lender.setStudentNum(stNum);
        lender.setOrganization(LoginContextUtils.getOrgName());
        //获取借入者信息
        UserDO borrower = userService.getUerByStNum(borrowStNum);
        if(borrower == null){
            return Response.addInfo(ResponseEnum.BORROWER_NOT_REGISTERED);
        }
        ServiceResponse serviceResponse = lendBorrowMaterialService.lend(lendBorrowMaterialDO,lender,borrower);
        return Response.parseResponse(serviceResponse);
    }


    @RequestMapping("/ackRevert")
    public Response ackRevert(LendBorrowMaterialVO lendBorrowMaterialVO){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为{}确认归还物资,信息:{}",stNum,JSON.toJSONString(lendBorrowMaterialVO));
        //重要参数检查
        String lendStNum = lendBorrowMaterialVO.getLendStNum();
        String borrowStNum = lendBorrowMaterialVO.getBorrowStNum();
        if(lendBorrowMaterialVO.getId() == null || lendBorrowMaterialVO.getMaterialId() == null || StringUtils.empty(lendStNum) || StringUtils.empty(borrowStNum)){
            return Response.addInfo(ResponseEnum.ERROR_PARAM);
        }
        //类型转换
        LendBorrowMaterialDO lendBorrowMaterialDO = new LendBorrowMaterialDO();
        BeanUtils.copyProperties(lendBorrowMaterialVO,lendBorrowMaterialDO);
        //设置确认人信息
        lendBorrowMaterialDO.setAckRevertStNum(LoginContextUtils.getStNum());
        ServiceResponse serviceResponse = lendBorrowMaterialService.ackRevert(lendBorrowMaterialDO);
        return Response.parseResponse(serviceResponse);
    }

    @RequestMapping("/getLendMaterial")
    public Response getLendMaterial(){
        return null;
    }
    @RequestMapping("/getBorrowMaterial")
    public Response getBorrowMaterial(){
        return null;
    }
}
