package com.zhaolei.material.admin.dao.graduation;


import com.zhaolei.material.admin.dao.base.ISqlMapper;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface LendBorrowMaterialMapper extends ISqlMapper {
    /**
     * 物理删除记录
     * @param id 主键id
     * @return  删除条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段插入记录
     * @param record 物资信息
     * @return 插入条数
     */
    int insert(LendBorrowMaterialDO record);

    /**
     * 有选择性插入记录
     * @param record 物资信息
     * @return 插入条数
     */
    int insertSelective(LendBorrowMaterialDO record);

    /**
     * 根据id查询记录
     * @param id id
     * @return 返回信息
     */
    LendBorrowMaterialDO selectByPrimaryKey(Integer id);

    /**
     * 有选择性更新信息
     * @param record 更新记录信息
     * @return 更新条数
     */
    int updateByPrimaryKeySelective(LendBorrowMaterialDO record);

    /**
     * 全量字段更新记录
     * @param record 更新信息
     * @return 更新条数
     */
    int updateByPrimaryKey(LendBorrowMaterialDO record);

    /**
     * 查询借入借出情况 根据传入信息 xml文件需要考虑索引问题
     * @param record 筛选条件
     * @return 结果集
     */
    List<LendBorrowMaterialDO> selectBySelective(LendBorrowMaterialDO record);
}