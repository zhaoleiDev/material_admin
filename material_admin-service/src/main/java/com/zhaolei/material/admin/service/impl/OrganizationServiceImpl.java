package com.zhaolei.material.admin.service.impl;


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
        return organizationMapper.selectByPrimaryKey(id);
    }
}
