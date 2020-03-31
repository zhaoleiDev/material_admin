package com.zhaolei.material.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.common.tools.TimeUtils;
import com.zhaolei.material.admin.dao.graduation.MaterialMapper;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHAOLEI
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;


    @Override
    public boolean entry(MaterialDO materialDO) {
        //先更新数据库再更新缓存保证数据的一致性
        boolean res =  materialMapper.insertSelective(materialDO)>0;
        RedisUtils.del(materialDO.getOrgName());
        return res;
    }

    @Override
    public boolean deleteById(Integer id) {
        String stNum = LoginContextUtils.getStNum();
        MaterialDO materialDO = new MaterialDO();
        materialDO.setUpdateStNum(stNum);
        materialDO.setId(id);
        //将状态设置为删除状态
        materialDO.setStatusInfo(0);
        boolean res = materialMapper.updateByPrimaryKeySelective(materialDO)>0;
        RedisUtils.del(LoginContextUtils.getOrgName());
        return res;
    }

    @Override
    public boolean updateById(MaterialDO materialDO) {
        boolean res = materialMapper.updateByPrimaryKeySelective(materialDO)>0;
        RedisUtils.del(LoginContextUtils.getOrgName());
        return res;
    }

    @Override
    public List<MaterialDO> getMaterialByOrg(String orgName) {
        String jsonStr = RedisUtils.get(orgName);
        if(jsonStr == null){
            List<MaterialDO> list = materialMapper.getMaterialByOrg(orgName);
            RedisUtils.setRandomEx(orgName,JSON.toJSONString(list), TimeUtils.TEN_MINUTE_S);
            return list;
        }
        //return JSON.parseArray(jsonStr,MaterialDO.class);两种方式选其中一种
        return JSON.parseObject(jsonStr,new TypeReference<List<MaterialDO>>(){});
    }
}
