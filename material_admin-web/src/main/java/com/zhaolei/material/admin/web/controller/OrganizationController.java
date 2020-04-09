package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.domain.vo.OrganizationVO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping(value="/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/registered")
    public Response registered(@RequestBody OrganizationVO organizationVO){
        //一个用户只能一个组织的负责人
        if(organizationService.getOrgByPrincipalStNum(organizationVO.getPrincipalStNum()) != null){
            return Response.addInfo(ResponseEnum.PRINCIPAL_EXIT);
        }
        OrganizationDO organizationDO = new OrganizationDO();
        BeanUtils.copyProperties(organizationVO,organizationDO);
        String token = DigestUtils.md5(organizationDO.getOrgName()+System.currentTimeMillis());
        organizationDO.setToken(token);
        organizationService.registered(organizationDO);
        return Response.success(token);
    }

    @PostMapping("/updateByOrgName")
    public Response updateByOrgName(@RequestBody OrganizationVO organizationVO){
        if(organizationVO.getOrgName() == null){
            return Response.addInfo(ResponseEnum.ERROR_PARAM);
        }
        OrganizationDO organizationDO = new OrganizationDO();
        BeanUtils.copyProperties(organizationVO,organizationDO);
        if(organizationService.updateByOrgName(organizationDO)){
            return Response.success();
        }
        return Response.fail();
    }

    @RequestMapping("/deleteByOrgName")
    public Response deleteByOrgName(@RequestParam("orgName") String orgName){
        if(organizationService.deleteByOrgName(orgName)){
            return Response.success();
        }
        return Response.fail();
    }

    @RequestMapping("/getAllOrg")
    public Response getAllOrg(){
        return Response.success(organizationService.getAllOrgName());
    }



}
