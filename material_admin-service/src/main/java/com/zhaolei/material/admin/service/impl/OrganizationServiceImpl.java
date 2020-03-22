package com.zhaolei.material.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.RedisCli;
import com.zhaolei.material.admin.dao.graduation.OrganizationMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author ZHAOLEI
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private RedisCli redisCli;

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
        Jedis jedis = redisCli.getJedis();
        String str = jedis.get(id+"");
        if(str == null){
            OrganizationDO organizationDO = organizationMapper.selectByPrimaryKey(id);
            String json = JSON.toJSONString(organizationDO);
            jedis.set(id+"",json);
            return organizationDO;
        }
        return JSON.parseObject(str,OrganizationDO.class);
        /*return organizationMapper.selectByPrimaryKey(id);*/
    }

    public OrganizationDO selectByName(String name) {
        /*Jedis jedis = redisCli.getJedis();
        String str = jedis.get(name);
        if(str == null){
            OrganizationDO organizationDO = organizationMapper.selectByName(name);
            String json = JSON.toJSONString(organizationDO);
            jedis.set(name,json);
            return organizationDO;
        }
        return JSON.parseObject(str,OrganizationDO.class);*/
        return organizationMapper.selectByName(name);
    }
}
