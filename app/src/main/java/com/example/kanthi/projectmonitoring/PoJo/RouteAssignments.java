package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;

/**
 * Created by Kanthi on 3/28/2018.
 */

public class RouteAssignments {

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("actualdate")
    @DatabaseField(columnName = "actualdate")
    private String actualdate;

    @SerializedName("day")
    @DatabaseField(columnName = "day")
    private String day;

    @SerializedName("tourtype")
    @DatabaseField(columnName = "tourtype")
    private Integer tourtype;

    @SerializedName("subtaskid")
    @DatabaseField(columnName = "subtaskid")
    private Integer subtaskid;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("timestamp")
    @DatabaseField(columnName = "timestamp")
    private String timestamp;

    @SerializedName("flag")
    @DatabaseField(columnName = "flag")
    private Boolean flag;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

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
    private String submitflag;

    @SerializedName("startseq")
    @DatabaseField(columnName = "startseq")
    private String startseq;

    @SerializedName("endseq")
    @DatabaseField(columnName = "endseq")
    private String endseq;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private Float totaltarget;

    @SerializedName("totalactual")
    @DatabaseField(columnName = "totalactual")
    private Float totalactual;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @SerializedName("isupdated")
    @DatabaseField(columnName = "is_updated")
    private boolean isupdated;

    @DatabaseField(columnName = "is_inserted")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @DatabaseField(columnName = "distributionAreaId")
    private Integer distributionAreaId;

    @SerializedName("distributionSubAreaId")
    @DatabaseField(columnName = "distributionSubAreaId")
    private Integer distributionSubAreaId;

    @SerializedName("salesManagerId")
    @DatabaseField(columnName = "salesManagerId")
    private Integer salesManagerId;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private long routeassignmentsummaryid;

    @SerializedName("alertempid")
    @DatabaseField(columnName = "alertempid")
    private Integer alertempid;

    @SerializedName("resourceflag")
    @DatabaseField(columnName = "resourceflag")
    private Boolean resourceflag;

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
    private long id;

    @SerializedName("partnerId")
    @DatabaseField(columnName = "partnerId")
    private Integer partnerId;

    @SerializedName("distributionRouteId")
    @DatabaseField(columnName = "distributionRouteId")
    private Integer distributionRouteId;

    @SerializedName("evFlag")
    @DatabaseField(columnName = "evFlag")
    private String evFlag;

    @SerializedName("qasubmitflag")
    @DatabaseField(columnName = "qasubmitflag")
    private Boolean qasubmitflag;

    @SerializedName("projectstatusid")
    @DatabaseField(columnName = "projectstatusid")
    private long projectstatusid;

    @SerializedName("milestoneflag")
    @DatabaseField(columnName = "milestoneflag")
    private Boolean milestoneflag;

    @SerializedName("tasklink")
    @DatabaseField(columnName = "tasklink")
    private String tasklink;

    @SerializedName("typeoftask")
    @DatabaseField(columnName = "typeoftask")
    private String typeoftask;

    @SerializedName("vendorid")
    @DatabaseField(columnName = "vendorid")
    private Integer vendorid;

    @SerializedName("managerid")
    @DatabaseField(columnName = "managerid")
    private Integer managerid;

    @SerializedName("hcflag")
    @DatabaseField(columnName = "hcflag")
    private Boolean hcflag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActualdate() {
        return actualdate;
    }

    public void setActualdate(String actualdate) {
        this.actualdate = actualdate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTourtype() {
        return tourtype;
    }

    public void setTourtype(Integer tourtype) {
        this.tourtype = tourtype;
    }

    public Integer getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(Integer subtaskid) {
        this.subtaskid = subtaskid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
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

    public String getSubmitflag() {
        return submitflag;
    }

    public void setSubmitflag(String submitflag) {
        this.submitflag = submitflag;
    }

    public String getStartseq() {
        return startseq;
    }

    public void setStartseq(String startseq) {
        this.startseq = startseq;
    }

    public String getEndseq() {
        return endseq;
    }

    public void setEndseq(String endseq) {
        this.endseq = endseq;
    }

    public Float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(Float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public Float getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(Float totalactual) {
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

    public Integer getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(Integer salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public Integer getAlertempid() {
        return alertempid;
    }

    public void setAlertempid(Integer alertempid) {
        this.alertempid = alertempid;
    }

    public Boolean getResourceflag() {
        return resourceflag;
    }

    public void setResourceflag(Boolean resourceflag) {
        this.resourceflag = resourceflag;
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

    public Integer getDistributionSubAreaId() {
        return distributionSubAreaId;
    }

    public void setDistributionSubAreaId(Integer distributionSubAreaId) {
        this.distributionSubAreaId = distributionSubAreaId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getDistributionRouteId() {
        return distributionRouteId;
    }

    public void setDistributionRouteId(Integer distributionRouteId) {
        this.distributionRouteId = distributionRouteId;
    }

    public String getEvFlag() {
        return evFlag;
    }

    public void setEvFlag(String evFlag) {
        this.evFlag = evFlag;
    }

    public Boolean getQasubmitflag() {
        return qasubmitflag;
    }

    public void setQasubmitflag(Boolean qasubmitflag) {
        this.qasubmitflag = qasubmitflag;
    }

    public long getProjectstatusid() {
        return projectstatusid;
    }

    public void setProjectstatusid(long projectstatusid) {
        this.projectstatusid = projectstatusid;
    }

    public Boolean getMilestoneflag() {
        return milestoneflag;
    }

    public void setMilestoneflag(Boolean milestoneflag) {
        this.milestoneflag = milestoneflag;
    }

    public String getTasklink() {
        return tasklink;
    }

    public void setTasklink(String tasklink) {
        this.tasklink = tasklink;
    }

    public String getTypeoftask() {
        return typeoftask;
    }

    public void setTypeoftask(String typeoftask) {
        this.typeoftask = typeoftask;
    }

    public Integer getVendorid() {
        return vendorid;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Boolean getHcflag() {
        return hcflag;
    }

    public void setHcflag(Boolean hcflag) {
        this.hcflag = hcflag;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public boolean isIsupdated() {
        return isupdated;
    }

    public void setIsupdated(boolean isupdated) {
        this.isupdated = isupdated;
    }
}