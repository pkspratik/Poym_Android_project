package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Patrols {

    @SerializedName("projectid")
    @DatabaseField(columnName = "projectid")
    private Integer projectid;

    @SerializedName("linkid")
    @DatabaseField(columnName = "linkid")
    private Integer linkid;

    @SerializedName("userid")
    @DatabaseField(columnName = "userid")
    private Integer userid;

    @SerializedName("datetime")
    @DatabaseField(columnName = "datetime")
    private String datetime;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("modifiedflag")
    @DatabaseField(columnName = "modifiedflag")
    private Boolean modifiedflag;

    @SerializedName("actuallatitude")
    @DatabaseField(columnName = "actuallatitude")
    private String actuallatitude;

    @SerializedName("actuallongitude")
    @DatabaseField(columnName = "actuallongitude")
    private String actuallongitude;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Long id;

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getModifiedflag() {
        return modifiedflag;
    }

    public void setModifiedflag(Boolean modifiedflag) {
        this.modifiedflag = modifiedflag;
    }

    public String getActuallatitude() {
        return actuallatitude;
    }

    public void setActuallatitude(String actuallatitude) {
        this.actuallatitude = actuallatitude;
    }

    public String getActuallongitude() {
        return actuallongitude;
    }

    public void setActuallongitude(String actuallongitude) {
        this.actuallongitude = actuallongitude;
    }

    public boolean getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(boolean insertFlag) {
        this.insertFlag = insertFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}