package com.zhaolei.material.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZHAOLEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationVO implements Serializable {
    private static final long serialVersionUID = 6672050943088742308L;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 负责人姓名
     */
    private String principalName;
    /**
     * 负责人学号
     */
    private String principalStNum;
    /**
     * 负责人电话号码
     */
    private String principalPhoneNum;
    /**
     * 负责人所在学院
     */
    private String principalCollege;
}
