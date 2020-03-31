package com.zhaolei.material.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.common.tools.ThreadLocalUtils;
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
        if(userDO.getOrganization() == null || userDO.getOrganizationToken() == null || userDO.getStudentNum() == null){
            return ServiceResponse.addInfo(ResponseEnum.ERROR_PARAM);
        }
        OrganizationDO organizationDO = organizationMapper.selectByOrgName(userDO.getOrganization());
        if(organizationDO == null){
            return ServiceResponse.addInfo(ResponseEnum.NOT_EXIST_ORG);
        }
        if(!userDO.getOrganizationToken().equals(organizationDO.getToken())){
            return ServiceResponse.addInfo(ResponseEnum.ERROR_ORG_TOKEN);
        }
        //不考虑失败的情况，先写数据库再删缓存比较好
        userMapper.insert(userDO);
        RedisUtils.del(userDO.getStudentNum());
        return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
    }

    @Override
    public boolean updateById(UserDO userDo) {
        Integer id = (Integer)ThreadLocalUtils.get("id");
        userDo.setId(id);
        boolean res = userMapper.updateByPrimaryKeySelective(userDo)>0;
        String stNum = (String)ThreadLocalUtils.get("stNum");
        RedisUtils.del(stNum);
        return res;
    }

    @Override
    public boolean deleteById(Integer id) {
        String stNum = LoginContextUtils.getStNum();
        UserDO userDO = new UserDO();
        userDO.setId(id);
        //将状态设置为注销状态
        userDO.setStatusInfo(0);
        boolean res = userMapper.updateByPrimaryKeySelective(userDO)>0;
        RedisUtils.del(stNum);
        return res;
    }

    @Override
    public UserDO getUerByStNum(String stNum) {
        String jsonStr = RedisUtils.get(stNum);
        if(jsonStr == null){
            UserDO userDO = userMapper.selectByStNum(stNum);
            String value = JSON.toJSONString(userDO);
            //即使数据库值为null也下redis中set值
            RedisUtils.setRandomEx(stNum, value,TimeUtils.TEN_MINUTE_S);
            return userDO;
        }
        return JSON.parseObject(jsonStr,UserDO.class);
    }

}
