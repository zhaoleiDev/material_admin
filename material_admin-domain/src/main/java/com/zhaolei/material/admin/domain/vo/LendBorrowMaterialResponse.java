package com.zhaolei.material.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHAOLEI
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LendBorrowMaterialResponse implements Serializable {
    private static final long serialVersionUID = -7608853176405606149L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 物资id
     */
    private Integer materialId;
    /**
     * 物资名称
     */
    private String materialName;
    /**
     * 物资组织
     */
    private String materialOrg;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 借出者学号
     */
    private String lendStNum;
    /**
     * 借出者姓名
     */
    private String lendName;
    /**
     * 借出者联系方式
     */
    private String lendPhoneNum;

    /**
     * 借出者组织
     */
    private String lendOrg;

    /**
     * 借入者学号
     */
    private String borrowStNum;

    /**
     * 借入者姓名
     */
    private String borrowName;
    /**
     * 借入者联系电话
     */
    private String borrowPhoneNum;

    /**
     * 借入者组织
     */
    private String borrowOrg;

    /**
     * 借入 借出 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operationTime;

    /**
     * 约定归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date promiseTime;

    /**
     * 确认归还者学号
     */
    private String ackRevertStNum;

    /**
     * 归还状态  0:未归还  1：已归还
     */
    private Integer statusInfo;

    /**
     * 备注
     */
    private String memo;
}
