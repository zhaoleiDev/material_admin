package com.zhaolei.material.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.common.tools.TimeUtils;
import com.zhaolei.material.admin.dao.graduation.OrganizationMapper;
import com.zhaolei.material.admin.dao.graduation.UserMapper;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOLEI
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public ServiceResponse registered(UserDO userDO) {
        if(userDO.getOrganization() == null || userDO.getOrganizationToken() == null){
            return ServiceResponse.addInfo(ResponseEnum.ERROR_PARAM);
        }
        OrganizationDO organizationDO = organizationMapper.selectByOrgName(userDO.getOrganization());
        if(organizationDO == null){
            return ServiceResponse.addInfo(ResponseEnum.NOT_EXIST_ORG);
        }
        if(!userDO.getOrganizationToken().equals(organizationDO.getToken())){
            return ServiceResponse.addInfo(ResponseEnum.ERROR_ORG_TOKEN);
        }
        userMapper.insert(userDO);
        return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
    }

    @Override
    public boolean updateById(UserDO userDo) {
        return userMapper.updateByPrimaryKey(userDo)>0;
    }

    @Override
    public UserDO getUerByStNum(String stNum) {
        String jsonStr = RedisUtils.get(stNum);
        if(jsonStr == null){
            UserDO userDO = userMapper.selectByStNum(stNum);
            String value = JSON.toJSONString(userDO);
            RedisUtils.set(stNum, value,TimeUtils.FIVE_MINUTE);
            return userDO;
        }
        return JSON.parseObject(jsonStr,UserDO.class);
    }
}
