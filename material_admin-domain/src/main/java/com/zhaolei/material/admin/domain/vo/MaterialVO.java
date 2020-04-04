package com.zhaolei.material.admin.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHAOLEI
 */
@Data
public class MaterialVO implements Serializable {
    private static final long serialVersionUID = -1154847968338895656L;

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
     * 负责人学号
     */
    private String principalStNum;


}
