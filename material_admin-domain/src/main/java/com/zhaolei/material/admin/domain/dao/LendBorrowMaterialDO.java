package com.zhaolei.material.admin.domain.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHAOLEI
 */
public class LendBorrowMaterialDO implements Serializable {
    private static final long serialVersionUID = -6771202341572982984L;
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
    private Date operationTime;

    /**
     * 实际归还时间
     */
    private Date revertTime;

    /**
     * 约定归还时间
     */
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

    public LendBorrowMaterialDO() {
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getMaterialId() {
        return materialId;
    }


    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }


    public Integer getNumber() {
        return number;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }


    public String getLendStNum() {
        return lendStNum;
    }


    public void setLendStNum(String lendStNum) {
        this.lendStNum = lendStNum == null ? null : lendStNum.trim();
    }


    public String getLendOrg() {
        return lendOrg;
    }


    public void setLendOrg(String lendOrg) {
        this.lendOrg = lendOrg == null ? null : lendOrg.trim();
    }


    public String getBorrowStNum() {
        return borrowStNum;
    }


    public void setBorrowStNum(String borrowStNum) {
        this.borrowStNum = borrowStNum == null ? null : borrowStNum.trim();
    }

    public String getBorrowOrg() {
        return borrowOrg;
    }


    public void setBorrowOrg(String borrowOrg) {
        this.borrowOrg = borrowOrg == null ? null : borrowOrg.trim();
    }


    public Date getOperationTime() {
        return operationTime;
    }


    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }


    public Date getRevertTime() {
        return revertTime;
    }


    public void setRevertTime(Date revertTime) {
        this.revertTime = revertTime;
    }


    public Date getPromiseTime() {
        return promiseTime;
    }


    public void setPromiseTime(Date promiseTime) {
        this.promiseTime = promiseTime;
    }


    public String getAckRevertStNum() {
        return ackRevertStNum;
    }


    public void setAckRevertStNum(String ackRevertStNum) {
        this.ackRevertStNum = ackRevertStNum == null ? null : ackRevertStNum.trim();
    }


    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(Integer statusInfo) {
        this.statusInfo = statusInfo;
    }

    @Override
    public String toString() {
        return "LendBorrowMaterialDO{" +
                "id=" + id +
                ", materialId=" + materialId +
                ", number=" + number +
                ", lendStNum='" + lendStNum + '\'' +
                ", lendOrg='" + lendOrg + '\'' +
                ", borrowStNum='" + borrowStNum + '\'' +
                ", borrowOrg='" + borrowOrg + '\'' +
                ", operationTime=" + operationTime +
                ", revertTime=" + revertTime +
                ", promiseTime=" + promiseTime +
                ", ackRevertStNum='" + ackRevertStNum + '\'' +
                ", statusInfo=" + statusInfo +
                ", memo='" + memo + '\'' +
                '}';
    }
}