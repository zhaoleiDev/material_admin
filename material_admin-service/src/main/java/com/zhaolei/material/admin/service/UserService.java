package com.zhaolei.material.admin.service;


import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.UserDO;

/**
 * @author ZHAOLEI
 */
public interface UserService {
    /**
     * 注册接口
     * @param userDo 用户信息
     * @return 成功true
     */
    ServiceResponse registered(UserDO userDo);

    /**
     * 更新用户信息
     * @param userDo 用户信息
     * @return 成功true
     */
    boolean updateById(UserDO userDo);

    /**
     * 根据学号查询用户信息
     * @param stNum 学号
     * @return 用户信息
     */
    UserDO getUerByStNum(String stNum);

    /**
     * 根据id逻辑删除用户，stNum用于删除缓存
     * @param id 主键
     * @param stNum 学号
     * @return 更新条数
     */
    boolean deleteById(Integer id,String stNum);

}
