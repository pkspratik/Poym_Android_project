package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kanthi on 5/15/2018.
 */

public class RouteAssignmentSummaries {

    @SerializedName("fromdate")
    @DatabaseField(columnName = "fromdate")
    private String fromdate;

    @SerializedName("todate")
    @DatabaseField(columnName = "todate")
    private String todate;

    @SerializedName("actualstartdate")
    @DatabaseField(columnName = "actualstartdate")
    private String actualstartdate;

    @SerializedName("actualenddate")
    @DatabaseField(columnName = "actualenddate")
    private String actualenddate;

    @SerializedName("partnerId")
    @DatabaseField(columnName = "partnerId")
    private Integer partnerId;

    @SerializedName("salesManagerId")
    @DatabaseField(columnName = "salesManagerId")
    private Integer salesManagerId;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @DatabaseField(columnName = "distributionAreaId")
    private Integer distributionAreaId;

    @SerializedName("distributionSubAreaId")
    @DatabaseField(columnName = "distributionSubAreaId")
    private Integer distributionSubAreaId;

    @SerializedName("tourtype")
    @DatabaseField(columnName = "tourtype")
    private Integer tourtype;

    @SerializedName("worktypeid")
    @DatabaseField(columnName = "worktypeid")
    private Integer worktypeid;

    @SerializedName("duration")
    @DatabaseField(columnName = "duration")
    private Float duration;

    @SerializedName("unitmeasurementid")
    @DatabaseField(columnName = "unitmeasurementid")
    private Integer unitmeasurementid;

    @SerializedName("submitflag")
    @DatabaseField(columnName = "submitflag")
    private Boolean submitflag;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private Float totaltarget;

    @SerializedName("totalactual")
    @DatabaseField(columnName = "totalactual")
    private String totalactual;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "is_inserted")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("isupdateflag")
    @DatabaseField(columnName = "isupdateflag")
    private boolean isupdateflag;

    @SerializedName("consultUserId")
    @DatabaseField(columnName = "consultUserId")
    private Integer consultUserId;

    @SerializedName("informedUserId")
    @DatabaseField(columnName = "informedUserId")
    private Integer informedUserId;

    @SerializedName("supportUserId")
    @DatabaseField(columnName = "supportUserId")
    private Integer supportUserId;

    @SerializedName("dependentTaskId")
    @DatabaseField(columnName = "dependentTaskId")
    private String dependentTaskId;

    @SerializedName("startFlag")
    @DatabaseField(columnName = "startFlag")
    private Boolean startFlag;

    @SerializedName("completedFlag")
    @DatabaseField(columnName = "completedFlag")
    private Boolean completedFlag;

    @SerializedName("phaseId")
    @DatabaseField(columnName = "phaseId")
    private Integer phaseId;

    @SerializedName("processId")
    @DatabaseField(columnName = "processId")
    private Integer processId;

    @SerializedName("subProcessId")
    @DatabaseField(columnName = "subProcessId")
    private Integer subProcessId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getActualstartdate() {
        return actualstartdate;
    }

    public void setActualstartdate(String actualstartdate) {
        this.actualstartdate = actualstartdate;
    }

    public String getActualenddate() {
        return actualenddate;
    }

    public void setActualenddate(String actualenddate) {
        this.actualenddate = actualenddate;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(Integer salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(Integer salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    public Integer getDistributionAreaId() {
        return distributionAreaId;
    }

    public void setDistributionAreaId(Integer distributionAreaId) {
        this.distributionAreaId = distributionAreaId;
    }


    public Integer getDistributionSubAreaId() {
        return distributionSubAreaId;
    }

    public void setDistributionSubAreaId(Integer distributionSubAreaId) {
        this.distributionSubAreaId = distributionSubAreaId;
    }

    public Integer getTourtype() {
        return tourtype;
    }

    public void setTourtype(Integer tourtype) {
        this.tourtype = tourtype;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Integer getUnitmeasurementid() {
        return unitmeasurementid;
    }

    public void setUnitmeasurementid(Integer unitmeasurementid) {
        this.unitmeasurementid = unitmeasurementid;
    }

    public Boolean getSubmitflag() {
        return submitflag;
    }

    public void setSubmitflag(Boolean submitflag) {
        this.submitflag = submitflag;
    }

    public Float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(Float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public String getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(String totalactual) {
        this.totalactual = totalactual;
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

    public boolean isIsupdateflag() {
        return isupdateflag;
    }

    public void setIsupdateflag(boolean isupdateflag) {
        this.isupdateflag = isupdateflag;
    }

    public Integer getConsultUserId() {
        return consultUserId;
    }

    public void setConsultUserId(Integer consultUserId) {
        this.consultUserId = consultUserId;
    }

    public Integer getInformedUserId() {
        return informedUserId;
    }

    public void setInformedUserId(Integer informedUserId) {
        this.informedUserId = informedUserId;
    }

    public Integer getSupportUserId() {
        return supportUserId;
    }

    public void setSupportUserId(Integer supportUserId) {
        this.supportUserId = supportUserId;
    }

    public String getDependentTaskId() {
        return dependentTaskId;
    }

    public void setDependentTaskId(String dependentTaskId) {
        this.dependentTaskId = dependentTaskId;
    }

    public Boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public Boolean getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(Boolean completedFlag) {
        this.completedFlag = completedFlag;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(Integer subProcessId) {
        this.subProcessId = subProcessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}