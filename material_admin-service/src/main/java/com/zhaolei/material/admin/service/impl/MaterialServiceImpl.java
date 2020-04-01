package com.zhaolei.material.admin.service.impl;

import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.dao.graduation.MaterialMapper;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与物资表相关统一不走缓存，因为从业务上有根据组织查询物资的需求
 * 只有有任何一个组员有物资的借入借出、归还确认依据物资的更新都会使组织物资信息发生变化
 * @author ZHAOLEI
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;


    @Override
    public boolean entry(MaterialDO materialDO) {
       return  materialMapper.insertSelective(materialDO)>0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String stNum = LoginContextUtils.getStNum();
        MaterialDO materialDO = new MaterialDO();
        materialDO.setUpdateStNum(stNum);
        materialDO.setId(id);
        //将状态设置为删除状态
        materialDO.setStatusInfo(0);
        return materialMapper.updateByPrimaryKeySelective(materialDO)>0;
    }

    @Override
    public boolean updateById(MaterialDO materialDO) {
       return materialMapper.updateByPrimaryKeySelective(materialDO)>0;
    }

    @Override
    public List<MaterialDO> getMaterialByOrg(String orgName) {
        return materialMapper.getMaterialByOrg(orgName);
    }

    @Override
    public MaterialDO getMaterialById(Integer id) {
        return materialMapper.selectByPrimaryKey(id);
    }

    @Override
    public MaterialDO getMaterialLendNumById(Integer id) {
        return materialMapper.getMaterialLendNumById(id);
    }

    @Override
    public boolean addLendNumById(Integer id , Integer addNum) {
        return materialMapper.updateLendNumById(id,addNum)>0;
    }
}
