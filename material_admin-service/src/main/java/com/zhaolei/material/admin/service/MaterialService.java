package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.dao.MaterialDO;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface MaterialService {
    /**
     * 录入物资信息
     * @param materialDO 物资信息
     * @return 录入是否成功
     */
    boolean entry(MaterialDO materialDO);

    /**
     * 根据id逻辑删除物资记录
     * @param id id
     * @return 删除条数
     */
    boolean deleteById(Integer id);

    /**
     * 更新物资信息
     * @param materialDO 更新信息
     * @return 更新条数
     */
    boolean updateById(MaterialDO materialDO);

    /**
     * 根据组织名称获取物资信息
     * @param orgName 组织名称
     * @return 结果集
     */
    List<MaterialDO> getMaterialByOrg(String orgName);
}
