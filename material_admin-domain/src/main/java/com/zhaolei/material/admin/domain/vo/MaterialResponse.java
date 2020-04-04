package com.zhaolei.material.admin.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHAOLEI
 */
@Data
public class MaterialResponse implements Serializable {
    private static final long serialVersionUID = 5676645547310415340L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 物资名称
     */
    private String materialName;

    /**
     * 总量
     */
    private Integer totalNum;

    /**
     * 可借出数量
     */
    private Integer lendNum;

    /**
     * 所属组织
     */
    private String orgName;

    /**
     * 最近一次更新人的学号
     */
    private String updateStNum;

    /**
     * 负责人学号
     */
    private String principalStNum;
    /**
     * 负责人姓名
     */
    private String principalName;
    /**
     * 负责人联系方式
     */
    private String principalPhoneNum;

    /**
     * 图片地址
     */
    private String photoPath;


}
