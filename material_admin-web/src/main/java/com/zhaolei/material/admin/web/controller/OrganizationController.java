package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.domain.base.Response;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value="/getById")
    public  Response getById(int id)  {
        Long time = System.currentTimeMillis();
        System.out.println(time);
        Response res = Response.success(organizationService.selectById(id));
        System.out.println(System.currentTimeMillis()-time);
        return res;
    }
    @RequestMapping(value="/getByName")
    public  Response getByName(String name)  {
        Long time = System.currentTimeMillis();
        System.out.println(time);
        Response res = Response.success(organizationService.selectByName(name));
        System.out.println(System.currentTimeMillis()-time);
        return res;
    }

}
