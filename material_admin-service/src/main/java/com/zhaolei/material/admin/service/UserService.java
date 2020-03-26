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

}
