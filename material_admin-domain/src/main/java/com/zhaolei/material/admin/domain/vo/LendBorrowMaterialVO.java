package com.zhaolei.material.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHAOLEI
 */
@Data
public class LendBorrowMaterialVO implements Serializable {
    private static final long serialVersionUID = 5543736724781187811L;
    /**
     * 物资id
     */
    private Integer materialId;

    /**
     * 数量
     */
    private Integer number;
    /**
     * 借入者学号
     */
    private String borrowStNum;
    /**
     * 约定归还时间
     * 只适用json传数据的时候，并且这个注解还有时区问题，所以+8
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date promiseTime;

    /**
     * 备注
     */
    private String memo;

}
