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
public class OrganizationVO {
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
