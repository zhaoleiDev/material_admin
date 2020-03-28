package com.zhaolei.material.admin.service.impl;


import com.zhaolei.material.admin.dao.graduation.OrganizationMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
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
        return organizationMapper.selectAllOrgName();
    }


}
