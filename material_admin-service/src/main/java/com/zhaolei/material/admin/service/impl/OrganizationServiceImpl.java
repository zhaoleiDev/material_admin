package com.zhaolei.material.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.common.tools.TimeUtils;
import com.zhaolei.material.admin.dao.graduation.OrganizationMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取所有组织会有五分钟延迟，不保证数据的完全一致
 * @author ZHAOLEI
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public boolean registered(OrganizationDO organizationDO) {
        return organizationMapper.insert(organizationDO)>0;
    }

    @Override
    public boolean updateByOrgName(OrganizationDO organizationDO) {
        return organizationMapper.updateByOrgNameSelective(organizationDO)>0;
    }

    @Override
    public boolean deleteByOrgName(String orgName) {
        OrganizationDO organizationDO = new OrganizationDO();
        organizationDO.setOrgName(orgName);
        //将状态设置为已删除状态
        organizationDO.setStatusInfo(0);
        return organizationMapper.updateByOrgNameSelective(organizationDO)>0;
    }

    @Override
    public List<String> getAllOrgName() {
        String key = "allOrgName";
        String jsonStr = RedisUtils.get(key);
        if(jsonStr == null){
            List<String> list = organizationMapper.selectAllOrgName();
            String redisJson = JSON.toJSONString(list);
            //会有五分钟延迟,不保证强一致性
            RedisUtils.setRandomEx(key,redisJson, TimeUtils.FIVE_MINUTE_S);
            return list;
        }
        return JSON.parseArray(jsonStr,String.class);
    }

    @Override
    public OrganizationDO getOrgByPrincipalStNum(String stNum) {
        return organizationMapper.selectByPrincipalStNum(stNum);
    }


}
