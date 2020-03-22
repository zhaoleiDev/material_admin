package com.zhaolei.material.admin.domain.dao;

/**
 * @author ZHAOLEI
 */
public class UserDO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 组织
     */
    private String organization;

    /**
     * 组织令牌
     */
    private String organizationToken;

    /**
     * 学号
     */
    private String studentNum;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 身份证明信息
     */
    private String photoInfo;
    /**
     * 状态 0：注销  1：在用
     */
    private Integer statusInfo;


    public UserDO(Integer id, String organization, String organizationToken, String studentNum, String userPassword, String userName, String phoneNum, String photoInfo, Integer statusInfo) {
        this.id = id;
        this.organization = organization;
        this.organizationToken = organizationToken;
        this.studentNum = studentNum;
        this.userPassword = userPassword;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.photoInfo = photoInfo;
        this.statusInfo = statusInfo;
    }

    public Integer getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(Integer statusInfo) {
        this.statusInfo = statusInfo;
    }


    public UserDO() {
        super();
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {

        this.id = id;
    }


    public String getOrganization() {

        return organization;
    }


    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }


    public String getOrganizationToken() {
        return organizationToken;
    }


    public void setOrganizationToken(String organizationToken) {
        this.organizationToken = organizationToken == null ? null : organizationToken.trim();
    }


    public String getStudentNum() {

        return studentNum;
    }


    public void setStudentNum(String studentNum) {

        this.studentNum = studentNum == null ? null : studentNum.trim();
    }


    public String getUserPassword() {

        return userPassword;
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }


    public String getUserName() {

        return userName;
    }


    public void setUserName(String userName) {

        this.userName = userName == null ? null : userName.trim();
    }


    public String getPhoneNum() {
        return phoneNum;
    }


    public void setPhoneNum(String phoneNum) {

        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }


    public String getPhotoInfo() {

        return photoInfo;
    }


    public void setPhotoInfo(String photoInfo) {

        this.photoInfo = photoInfo == null ? null : photoInfo.trim();
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", organization='" + organization + '\'' +
                ", organizationToken='" + organizationToken + '\'' +
                ", studentNum='" + studentNum + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", photoInfo='" + photoInfo + '\'' +
                ", statusInfo=" + statusInfo +
                '}';
    }
}