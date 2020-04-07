package com.zhaolei.material.admin.dao.graduation;


import com.zhaolei.material.admin.dao.base.ISqlMapper;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface MaterialMapper extends ISqlMapper {
    /**
     * 物理删除
     * @param id id
     * @return 删除条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段插入
     * @param record 插入信息
     * @return 插入条数
     */
    int insert(MaterialDO record);

    /**
     * 有选择性插入
     * @param record 插入信息
     * @return 插入条数
     */
    int insertSelective(MaterialDO record);

    /**
     *根据id查询记录
     * @param id id
     * @return 返回记录
     */
    MaterialDO selectByPrimaryKey(Integer id);

    /**
     * 有选择性更新记录
     * @param record 更新信息
     * @return 更新条数
     */
    int updateByPrimaryKeySelective(MaterialDO record);

    /**
     * 全量更新记录
     * @param record 更新信息
     * @return 更新条数
     */
    int updateByPrimaryKey(MaterialDO record);

    /**
     * 根据组织名称查询物资信息
     * @param orgName 组织名称
     * @return 结果集
     */
    List<MaterialDO> getMaterialByOrg(@Param("orgName") String orgName);

    /**
     * 根据id查询物资信息，使用当前读的方式，防止在事务期间有记录的修改操作
     * @param id id
     * @return 返回
     */
    MaterialDO getMaterialLendNumById(@Param("id")Integer id);

    /**
     * 根据id增加物资库存数量
     * @param id 主键id
     * @param addNum  需要增加的数量
     * @return 返回
     */
    int updateLendNumById(@Param("id")Integer id, @Param("addNum")Integer addNum);

    /**
     * 根据查询条件返回对应的结果集
     * @param materialDO 查询条件
     * @return 结果集
     */
    List<MaterialDO> search(MaterialDO materialDO);
}