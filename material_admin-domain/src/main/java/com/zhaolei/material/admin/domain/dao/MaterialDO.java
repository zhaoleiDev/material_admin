package com.zhaolei.material.admin.domain.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHAOLEI
 */
public class MaterialDO implements Serializable {
    private static final long serialVersionUID = -4155975651364825419L;
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
     * 图片地址
     */
    private String photoPath;

    /**
     * 状态 0：删除  1：在用
     */
    private Integer statusInfo;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public MaterialDO() {
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getMaterialName() {
        return materialName;
    }


    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }


    public Integer getTotalNum() {
        return totalNum;
    }


    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }


    public Integer getLendNum() {
        return lendNum;
    }


    public void setLendNum(Integer lendNum) {
        this.lendNum = lendNum;
    }


    public String getOrgName() {
        return orgName;
    }


    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }


    public String getUpdateStNum() {
        return updateStNum;
    }


    public void setUpdateStNum(String updateStNum) {
        this.updateStNum = updateStNum == null ? null : updateStNum.trim();
    }


    public String getPrincipalStNum() {
        return principalStNum;
    }


    public void setPrincipalStNum(String principalStNum) {
        this.principalStNum = principalStNum == null ? null : principalStNum.trim();
    }


    public String getPhotoPath() {
        return photoPath;
    }


    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath == null ? null : photoPath.trim();
    }


    public Integer getStatusInfo() {
        return statusInfo;
    }


    public void setStatusInfo(Integer statusInfo) {
        this.statusInfo = statusInfo;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MaterialDO{" +
                "id=" + id +
                ", materialName='" + materialName + '\'' +
                ", totalNum=" + totalNum +
                ", lendNum=" + lendNum +
                ", orgName='" + orgName + '\'' +
                ", updateStNum='" + updateStNum + '\'' +
                ", principalStNum='" + principalStNum + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", statusInfo=" + statusInfo +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}