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
        return organizationMapper.insertSelective(organizationDO)>0;
    }

    public boolean update(OrganizationDO organizationDO) {
        return false;
    }

    public boolean delete(int id) {
        return organizationMapper.deleteByPrimaryKey(id)>0;
    }

    public OrganizationDO selectById(int id) {
        String str = RedisUtils.get(id+"");
        if(str == null){
            OrganizationDO organizationDO = organizationMapper.selectByPrimaryKey(id);
            String json = JSON.toJSONString(organizationDO);
            RedisUtils.set(id+"",json);
            return organizationDO;
        }
        return JSON.parseObject(str,OrganizationDO.class);
        /*return organizationMapper.selectByPrimaryKey(id);*/
    }

    public OrganizationDO selectByName(String name) {
        String str = RedisUtils.get(name);
        if(str == null){
            OrganizationDO organizationDO = organizationMapper.selectByName(name);
            String json = JSON.toJSONString(organizationDO);
            RedisUtils.set(name,json);
            return organizationDO;
        }
        return JSON.parseObject(str,OrganizationDO.class);
        /*return organizationMapper.selectByName(name);*/
    }
}
