package com.zhaolei.material.admin.domain.dao;

import java.util.Date;

/**
 * @author ZHAOLEI
 */
public class OrganizationDO {
    /**
     * 主键id
     */
    private Integer id;
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
    /**
     * 组织令牌，用于用户注册时的校验
     */
    private String token;
    /**
     * 接入时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态  0：已注销  1：在用
     */
    private Integer statusInfo;

    public OrganizationDO(Integer id, String orgName, String principalName, String principalStNum, String principalPhoneNum, String principalCollege, String token, Date createTime, Date updateTime, Integer statusInfo) {
        this.id = id;
        this.orgName = orgName;
        this.principalName = principalName;
        this.principalStNum = principalStNum;
        this.principalPhoneNum = principalPhoneNum;
        this.principalCollege = principalCollege;
        this.token = token;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.statusInfo = statusInfo;
    }

    public OrganizationDO() {

        super();
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getOrgName() {

        return orgName;
    }

    public void setOrgName(String orgName) {

        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPrincipalName() {

        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public String getPrincipalStNum() {

        return principalStNum;
    }

    public void setPrincipalStNum(String principalStNum) {
        this.principalStNum = principalStNum == null ? null : principalStNum.trim();
    }

    public String getPrincipalPhoneNum() {

        return principalPhoneNum;
    }

    public void setPrincipalPhoneNum(String principalPhoneNum) {
        this.principalPhoneNum = principalPhoneNum == null ? null : principalPhoneNum.trim();
    }

    public String getPrincipalCollege()
    {
        return principalCollege;
    }

    public void setPrincipalCollege(String principalCollege) {
        this.principalCollege = principalCollege == null ? null : principalCollege.trim();
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token)
    {
        this.token = token == null ? null : token.trim();
    }

    public Date getCreateTime() {

        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(Integer statusInfo) {
        this.statusInfo = statusInfo;
    }

    @Override
    public String toString() {
        return "OrganizationDO{" +
                "id=" + id +
                ", orgName='" + orgName + '\'' +
                ", principalName='" + principalName + '\'' +
                ", principalStNum='" + principalStNum + '\'' +
                ", principalPhoneNum='" + principalPhoneNum + '\'' +
                ", principalCollege='" + principalCollege + '\'' +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", statusInfo=" + statusInfo +
                '}';
    }
}