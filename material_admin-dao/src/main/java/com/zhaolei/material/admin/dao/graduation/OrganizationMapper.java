package com.zhaolei.material.admin.dao.graduation;

import com.zhaolei.material.admin.dao.base.ISqlMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface OrganizationMapper extends ISqlMapper {
    /**
     * 更加id物理删除记录
     * @param id 删除id
     * @return 删除条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入组织所有信息，xml中舍弃了一些字段
     * @param record 插入信息
     * @return 插入条数
     */
    int insert(OrganizationDO record);

    /**
     * 有选择性插入记录，
     * @param record 插入信息
     * @return 插入条数
     */
    int insertSelective(OrganizationDO record);

    /**
     * 根据id查询组织信息
     * @param id 查询id
     * @return 组织信息
     */
    OrganizationDO selectByPrimaryKey(Integer id);

    /**
     * 根据id有选择性地更新组织的信息
     * @param record 组织信息
     * @return 更新条数
     */
    int updateByOrgNameSelective(OrganizationDO record);

    /**
     * 根据id更新组织所有信息
     * @param record 组织信息
     * @return 更新条数
     */
    int updateByPrimaryKey(OrganizationDO record);

    /**
     * 根据组织名称查询组织信息
     * @param organizationName 组织名称
     * @return 组织信息（组织名称是唯一索引）
     */

    OrganizationDO selectByOrgName(@Param("orgName") String organizationName);

    /**
     * 查询所有组织的名称
     * @return 名称列表
     */
    List<String> selectAllOrgName();
}