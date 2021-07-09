package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 2/22/2018.
 */

public class User {

    @DatabaseField(columnName = "name")
    @SerializedName("name")
    private String name;

    @DatabaseField(columnName = "typeOfDoctor")
    @SerializedName("typeOfDoctor")
    private Integer typeOfDoctor;

    @DatabaseField(columnName = "countryId")
    @SerializedName("countryId")
    private String countryId;

    @DatabaseField(columnName = "stateId")
    @SerializedName("stateId")
    private String stateId;

    @DatabaseField(columnName = "districtId")
    @SerializedName("districtId")
    private String districtId;

    @DatabaseField(columnName = "siteId")
    @SerializedName("siteId")
    private String siteId;

    @DatabaseField(columnName = "username")
    @SerializedName("username")
    private String username;

    @DatabaseField(columnName = "languageId")
    @SerializedName("languageId")
    private Integer languageId;

    @DatabaseField(columnName = "status")
    @SerializedName("status")
    private String status;

    @DatabaseField(columnName = "mobile")
    @SerializedName("mobile")
    private String mobile;

    @DatabaseField(columnName = "emailId")
    @SerializedName("emailId")
    private String emailId;

    @DatabaseField(columnName = "createdBy")
    @SerializedName("createdBy")
    private Integer createdBy;

    @DatabaseField(columnName = "createdDate")
    @SerializedName("createdDate")
    private String createdDate;

    @DatabaseField(columnName = "createdByRole")
    @SerializedName("createdByRole")
    private Integer createdByRole;

    @DatabaseField(columnName = "lastModifiedBy")
    @SerializedName("lastModifiedBy")
    private Integer lastModifiedBy;

    @DatabaseField(columnName = "lastModifiedDate")
    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;

    @DatabaseField(columnName = "lastModifiedByRole")
    @SerializedName("lastModifiedByRole")
    private Integer lastModifiedByRole;

    @DatabaseField(columnName = "realm")
    @SerializedName("realm")
    private String realm;

    @DatabaseField(columnName = "email")
    @SerializedName("email")
    private String email;

    @DatabaseField(columnName = "emailVerified")
    @SerializedName("emailVerified")
    private Boolean emailVerified;

    @DatabaseField(columnName = "verificationToken")
    @SerializedName("verificationToken")
    private String verificationToken;

    @DatabaseField(columnName = "created")
    @SerializedName("created")
    private String created;

    @DatabaseField(columnName = "lastUpdated")
    @SerializedName("lastUpdated")
    private String lastUpdated;

    @DatabaseField(columnName = "password")
    @SerializedName("password")
    private String password;

    @DatabaseField(columnName = "id")
    @SerializedName("id")
    private Integer id;

    @DatabaseField(columnName = "salesAreaId")
    @SerializedName("salesAreaId")
    private Integer salesAreaId;

    @DatabaseField(columnName = "distributionAreaId")
    @SerializedName("distributionAreaId")
    private Integer distributionAreaId;

    @DatabaseField(columnName = "zoneId")
    @SerializedName("zoneId")
    private Integer zoneId;

    @DatabaseField(columnName = "usertype")
    @SerializedName("usertype")
    private String usertype;

    @DatabaseField(columnName = "projecttype")
    @SerializedName("projecttype")
    private String projecttype;

    @DatabaseField(columnName = "groupId")
    @SerializedName("groupId")
    private Integer groupId;

    @DatabaseField(columnName = "roleId")
    @SerializedName("roleId")
    private Integer roleId;

    @DatabaseField(columnName = "language")
    @SerializedName("language")
    private Integer language;

    @DatabaseField(columnName = "sync_in_time")
    @SerializedName("syncInTime")
    private String syncInTime;

    @DatabaseField(columnName = "sync_out_time")
    @SerializedName("syncOutTime")
    private String syncOutTime;

    @DatabaseField(columnName = "update_flag")
    @SerializedName("updateFlag")
    private Boolean updateFlag;

    @DatabaseField(columnName = "login_date")
    @SerializedName("loginDate")
    private String loginDate;

    @DatabaseField(columnName = "score")
    @SerializedName("score")
    private Integer score;

    @DatabaseField(columnName = "trophy_date")
    @SerializedName("trophyDate")
    private String trophyDate;

    @DatabaseField(columnName = "employeeid")
    @SerializedName("employeeid")
    private int employeeid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeOfDoctor() {
        return typeOfDoctor;
    }

    public void setTypeOfDoctor(Integer typeOfDoctor) {
        this.typeOfDoctor = typeOfDoctor;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedByRole() {
        return createdByRole;
    }

    public void setCreatedByRole(Integer createdByRole) {
        this.createdByRole = createdByRole;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getLastModifiedByRole() {
        return lastModifiedByRole;
    }

    public void setLastModifiedByRole(Integer lastModifiedByRole) {
        this.lastModifiedByRole = lastModifiedByRole;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getSyncInTime() {
        return syncInTime;
    }

    public void setSyncInTime(String syncInTime) {
        this.syncInTime = syncInTime;
    }

    public String getSyncOutTime() {
        return syncOutTime;
    }

    public void setSyncOutTime(String syncOutTime) {
        this.syncOutTime = syncOutTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTrophyDate() {
        return trophyDate;
    }

    public void setTrophyDate(String trophyDate) {
        this.trophyDate = trophyDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(Integer salesAreaId) {
        this.salesAreaId = salesAreaId;
    }


    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getDistributionAreaId() {
        return distributionAreaId;
    }

    public void setDistributionAreaId(Integer distributionAreaId) {
        this.distributionAreaId = distributionAreaId;
    }
}