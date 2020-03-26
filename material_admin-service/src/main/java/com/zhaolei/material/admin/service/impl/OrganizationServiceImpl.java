package com.zhaolei.material.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.dao.graduation.OrganizationMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOLEI
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    public boolean registered(OrganizationDO organizationDO) {
        return organizationMapper.insert(organizationDO)>0;
    }

    public boolean update(OrganizationDO organizationDO) {
        return organizationMapper.updateByPrimaryKeySelective(organizationDO)>0;
    }

    public boolean delete(int id) {
        OrganizationDO organizationDO = new OrganizationDO();
        organizationDO.setId(id);
        //将状态设置为已删除状态
        organizationDO.setStatusInfo(0);
        return organizationMapper.updateByPrimaryKeySelective(organizationDO)>0;
    }



}
