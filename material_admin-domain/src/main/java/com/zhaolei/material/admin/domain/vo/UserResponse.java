package com.zhaolei.material.admin.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHAOLEI
 */
@Data
public class UserResponse implements Serializable {
    private static final long serialVersionUID = -8168303550391636469L;
    /**
     * 学号
     */
    private String studentNum;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String phoneNum;
}
