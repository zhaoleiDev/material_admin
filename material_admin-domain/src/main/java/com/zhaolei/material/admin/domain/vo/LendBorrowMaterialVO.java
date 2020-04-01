package com.zhaolei.material.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHAOLEI
 */
@Data
public class LendBorrowMaterialVO implements Serializable {
    private static final long serialVersionUID = 5543736724781187811L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 物资id
     */
    private Integer materialId;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 借出者学号
     */
    private String lendStNum;

    /**
     * 借出者组织
     */
    private String lendOrg;

    /**
     * 借入者学号
     */
    private String borrowStNum;

    /**
     * 借入者组织
     */
    private String borrowOrg;

    /**
     * 借入 借出 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operationTime;

    /**
     * 实际归还时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date revertTime;

    /**
     * 约定归还时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date promiseTime;

    /**
     * 确认归还者学号
     */
    private String ackRevertStNum;

    /**
     * 备注
     */
    private String memo;

}
