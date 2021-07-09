package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 5/9/2018.
 */

public class BOQHeaders {

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesareaId")
    @DatabaseField(columnName = "salesareaId")
    private Integer salesareaId;

    @SerializedName("distributionareaId")
    @DatabaseField(columnName = "distributionareaId")
    private Integer distributionareaId;

    @SerializedName("distributionsubareaId")
    @DatabaseField(columnName = "distributionsubareaId")
    private Integer distributionsubareaId;

    @SerializedName("boqNo")
    @DatabaseField(columnName = "boqNo")
    private String boqNo;

    @SerializedName("boqDate")
    @DatabaseField(columnName = "boqDate")
    private String boqDate;

    @SerializedName("payterm")
    @DatabaseField(columnName = "payterm")
    private Integer payterm;

    @SerializedName("totalamount")
    @DatabaseField(columnName = "totalamount")
    private String totalamount;

    @SerializedName("netamount")
    @DatabaseField(columnName = "netamount")
    private String netamount;

    @SerializedName("flag")
    @DatabaseField(columnName = "flag")
    private Boolean flag;

    @SerializedName("deleteFlag")
    @DatabaseField(columnName = "deleteFlag")
    private Boolean deleteFlag;

    @SerializedName("completedFlag")
    @DatabaseField(columnName = "completedFlag")
    private Boolean completedFlag;

    @SerializedName("surveyid")
    @DatabaseField(columnName = "surveyid")
    private long surveyid;

    @SerializedName("surveyFlag")
    @DatabaseField(columnName = "surveyFlag")
    private Boolean surveyFlag;

    @SerializedName("boqstatus")
    @DatabaseField(columnName = "boqstatus")
    private String boqstatus;

    @SerializedName("lastModifiedDate")
    @DatabaseField(columnName = "lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("subtasktypeid")
    @DatabaseField(columnName = "subtasktypeid")
    private Integer subtasktypeid;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getSalesareaId() {
        return salesareaId;
    }

    public void setSalesareaId(Integer salesareaId) {
        this.salesareaId = salesareaId;
    }

    public Integer getDistributionareaId() {
        return distributionareaId;
    }

    public void setDistributionareaId(Integer distributionareaId) {
        this.distributionareaId = distributionareaId;
    }

    public Integer getDistributionsubareaId() {
        return distributionsubareaId;
    }

    public void setDistributionsubareaId(Integer distributionsubareaId) {
        this.distributionsubareaId = distributionsubareaId;
    }

    public String getBoqNo() {
        return boqNo;
    }

    public void setBoqNo(String boqNo) {
        this.boqNo = boqNo;
    }

    public String getBoqDate() {
        return boqDate;
    }

    public void setBoqDate(String boqDate) {
        this.boqDate = boqDate;
    }

    public Integer getPayterm() {
        return payterm;
    }

    public void setPayterm(Integer payterm) {
        this.payterm = payterm;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getNetamount() {
        return netamount;
    }

    public void setNetamount(String netamount) {
        this.netamount = netamount;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(Boolean completedFlag) {
        this.completedFlag = completedFlag;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public long  getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(long surveyid) {
        this.surveyid = surveyid;
    }

    public Boolean getSurveyFlag() {
        return surveyFlag;
    }

    public void setSurveyFlag(Boolean surveyFlag) {
        this.surveyFlag = surveyFlag;
    }

    public String getBoqstatus() {
        return boqstatus;
    }

    public void setBoqstatus(String boqstatus) {
        this.boqstatus = boqstatus;
    }

    public Integer getSubtasktypeid() {
        return subtasktypeid;
    }

    public void setSubtasktypeid(Integer subtasktypeid) {
        this.subtasktypeid = subtasktypeid;
    }

    public boolean getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(boolean updateflag) {
        this.updateflag = updateflag;
    }

    public boolean getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(boolean insertFlag) {
        this.insertFlag = insertFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}