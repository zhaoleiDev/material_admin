package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.dao.OrganizationDO;

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
    boolean update(OrganizationDO organizationDO);

    /**
     * 删除,逻辑删除
     * @param id 主键id
     * @return 更新成功返回true,不成功false
     */
    boolean delete(int id);

}
