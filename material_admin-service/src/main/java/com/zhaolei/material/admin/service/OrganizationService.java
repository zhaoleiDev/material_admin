package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.dao.OrganizationDO;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface OrganizationService {
    /**
     * 注册接口
     * @param organizationDO  入参
     * @return  注册成功返回true,不成功false
     */
    boolean registered(OrganizationDO organizationDO);

    /**
     * 更新信息接口
     * @param organizationDO 入参
     * @return 更新成功返回true,不成功false
     */
    boolean updateByOrgName(OrganizationDO organizationDO);

    /**
     * 删除,逻辑删除
     * @param orgName 组织名称
     * @return 更新成功返回true,不成功false
     */
    boolean deleteByOrgName(String orgName);

    /**
     * 获取所有组织的名称
     * @return 组织名称
     */
    List<String> getAllOrgName();

    /**
     * 根据负责人学号查询组织信息
     * @param stNum 负责人学号
     * @return 返回组织信息
     */
    OrganizationDO getOrgByPrincipalStNum(String stNum);


}
