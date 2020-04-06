package com.zhaolei.material.admin.dao.graduation;

import com.zhaolei.material.admin.dao.base.ISqlMapper;
import com.zhaolei.material.admin.domain.dao.UserDO;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface UserMapper extends ISqlMapper {
    /**
     * 物理删除用户信息
     * @param id 主键
     * @return 删除条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入用户
     * @param record 用户信息
     * @return 添加条数
     */
    int insert(UserDO record);

    /**
     * 插入用户
     * @param record 插入信息
     * @return 插入条数
     */
    int insertSelective(UserDO record);

    /**
     * 根据主键查询用户信息
     * @param id 主键
     * @return 条数
     */
    UserDO selectByPrimaryKey(Integer id);

    /**
     * 有选择性更新用户信息
     * @param record 更新信息
     * @return 更新条数
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * 根据id 更新用户信息
     * @param record 更新信息
     * @return 更新条数
     */
    int updateByPrimaryKey(UserDO record);

    /**
     * 根据学号查询用户信息
     * @param stNum 学号
     * @return 用户信息
     */
    UserDO selectByStNum(String stNum);

    /**
     * 根据组织名称获取成员
     * @param orgName 组织名称
     * @return 结果集
     */
    List<UserDO> getUserByOrg(String orgName);

}