package com.zhaolei.material.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.domain.base.Page;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.eum.LendOrBorrowEnum;
import com.zhaolei.material.admin.domain.vo.LendBorrowMaterialResponse;
import com.zhaolei.material.admin.domain.vo.LendBorrowMaterialVO;
import com.zhaolei.material.admin.service.LendBorrowMaterialService;
import com.zhaolei.material.admin.service.MaterialService;
import com.zhaolei.material.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        if(borrower == null || borrower.getStatusInfo().equals(0)){
            return Response.addInfo(ResponseEnum.BORROWER_NOT_REGISTERED);
        }
        ServiceResponse serviceResponse = lendBorrowMaterialService.lend(lendBorrowMaterialDO,lender,borrower);
        return Response.parseResponse(serviceResponse);
    }


    @RequestMapping("/ackRevert")
    public Response ackRevert(LendBorrowMaterialVO lendBorrowMaterialVO){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为{}确认归还物资,信息:{}",stNum,JSON.toJSONString(lendBorrowMaterialVO));
        if(lendBorrowMaterialVO.getId() == null || lendBorrowMaterialVO.getMaterialId() == null || lendBorrowMaterialVO.getNumber() == 0){
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
    public Response getLendMaterial(@RequestParam(required = false ,defaultValue = "1") int pageNum,@RequestParam(required = false ,defaultValue = "13")int pageSize){
        Page page = new Page(pageNum,pageSize);
        return getLendOrBorrowInfo(LendOrBorrowEnum.PERSONAL_LEND,page);
    }

    @RequestMapping("/getBorrowMaterial")
    public Response getBorrowMaterial(@RequestParam(required = false ,defaultValue = "1") int pageNum,@RequestParam(required = false ,defaultValue = "13")int pageSize){
        Page page = new Page(pageNum,pageSize);
        return getLendOrBorrowInfo(LendOrBorrowEnum.PERSONAL_BORROW,page);
    }

    @RequestMapping("/getOrgLendMaterial")
    public Response getOrgLendMaterial(@RequestParam(required = false ,defaultValue = "1") int pageNum,@RequestParam(required = false ,defaultValue = "13")int pageSize){
        Page page = new Page(pageNum,pageSize);
        return getLendOrBorrowInfo(LendOrBorrowEnum.ORG_LEND,page);
    }

    @RequestMapping("/getOrgBorrowMaterial")
    public Response getOrgBorrowMaterial(@RequestParam(required = false ,defaultValue = "1") int pageNum,@RequestParam(required = false ,defaultValue = "13")int pageSize){
        Page page = new Page(pageNum,pageSize);
        return getLendOrBorrowInfo(LendOrBorrowEnum.ORG_BORROW,page);
    }

    /**
     * 获取个人的借入 借出信息
     * @param flag 标识借入、借出
     * @param page 分页信息
     * @return 返回结果
     */
    private Response getLendOrBorrowInfo(LendOrBorrowEnum flag, Page page){
        ServiceResponse serviceResponse = null;
        if(LendOrBorrowEnum.PERSONAL_LEND.equals(flag)){
            //个人借出
            serviceResponse = lendBorrowMaterialService.getLendMaterialByStNum(LoginContextUtils.getStNum(),page);
        }else if(LendOrBorrowEnum.PERSONAL_BORROW.equals(flag)){
            //个人借入
            serviceResponse = lendBorrowMaterialService.getBorrowMaterialByStNum(LoginContextUtils.getStNum(),page);
        }else if(LendOrBorrowEnum.ORG_BORROW.equals(flag)){
            //组织借入
            serviceResponse = lendBorrowMaterialService.getOrgBorrowMaterial(LoginContextUtils.getOrgName(),page);
        }else if(LendOrBorrowEnum.ORG_LEND.equals(flag)){
            //组织借出
            serviceResponse = lendBorrowMaterialService.getOrgLendMaterial(LoginContextUtils.getOrgName(),page);
        }
        if(serviceResponse == null){
            return Response.error();
        }
        if(serviceResponse.getCode() != ResponseEnum.SUCCESS.getCode()){
            return Response.parseResponse(serviceResponse);
        }
        List<LendBorrowMaterialResponse> lendBorrowMaterialResponseList = new ArrayList<>();
        List<LendBorrowMaterialDO> lendBorrowMaterialDOList = (List<LendBorrowMaterialDO>)serviceResponse.getData();
        for(LendBorrowMaterialDO lendBorrowMaterialDO : lendBorrowMaterialDOList){
            //类型转换
            LendBorrowMaterialResponse lendBorrowMaterialResponse = new LendBorrowMaterialResponse();
            BeanUtils.copyProperties(lendBorrowMaterialDO,lendBorrowMaterialResponse);
            //获取物资信息
            Integer materialId = lendBorrowMaterialDO.getMaterialId();
            MaterialDO materialDO = materialService.getMaterialById(materialId);
            lendBorrowMaterialResponse.setMaterialName(materialDO.getMaterialName());
            lendBorrowMaterialResponse.setMaterialOrg(materialDO.getOrgName());
            //查询组织借入借出时借入者和借出者都需要查询，查询个人的借入借出时只需要查借入或者借出人的信息
            if(LendOrBorrowEnum.PERSONAL_LEND.equals(flag) || LendOrBorrowEnum.ORG_LEND.equals(flag) || LendOrBorrowEnum.ORG_BORROW.equals(flag)){
                log.info("获取借出者信息");
                String borrowStNum = lendBorrowMaterialDO.getBorrowStNum();
                UserDO borrow = userService.getUerByStNum(borrowStNum);
                lendBorrowMaterialResponse.setBorrowPhoneNum(borrow.getPhoneNum());
                lendBorrowMaterialResponse.setBorrowName(borrow.getUserName());
            }
            if(LendOrBorrowEnum.PERSONAL_BORROW.equals(flag) || LendOrBorrowEnum.ORG_LEND.equals(flag) || LendOrBorrowEnum.ORG_BORROW.equals(flag)){
                log.info("获取借入者信息");
                String lendStNum = lendBorrowMaterialDO.getLendStNum();
                UserDO lend = userService.getUerByStNum(lendStNum);
                lendBorrowMaterialResponse.setLendName(lend.getUserName());
                lendBorrowMaterialResponse.setLendPhoneNum(lend.getPhoneNum());
            }
            lendBorrowMaterialResponseList.add(lendBorrowMaterialResponse);
        }
        return Response.success(lendBorrowMaterialResponseList,serviceResponse.getPage());
    }
}
