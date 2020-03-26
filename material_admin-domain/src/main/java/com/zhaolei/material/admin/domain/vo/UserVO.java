package com.zhaolei.material.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZHAOLEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    /**
     * 组织
     */
    private String organization;

    /**
     * 组织令牌
     */
    private String organizationToken;

    /**
     * 学号
     */
    private String studentNum;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String phoneNum;
}
