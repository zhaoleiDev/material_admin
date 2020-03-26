package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.common.tools.DigestUtils;
import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.domain.vo.OrganizationVO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



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
        OrganizationDO organizationDO = new OrganizationDO();
        BeanUtils.copyProperties(organizationVO,organizationDO);
        String token = DigestUtils.md5(organizationDO.getOrgName());
        organizationDO.setToken(token);
        organizationService.registered(organizationDO);
        return Response.success(token);
    }



}
