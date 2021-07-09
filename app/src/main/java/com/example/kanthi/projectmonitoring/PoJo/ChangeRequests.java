package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 4/30/2018.
 */

public class ChangeRequests {

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

    @SerializedName("employeeid")
    @DatabaseField(columnName = "employeeid")
    private Integer employeeid;

    @SerializedName("managerid")
    @DatabaseField(columnName = "managerid")
    private Integer managerid;

    @SerializedName("consultedId")
    @DatabaseField(columnName = "consultedId")
    private Integer consultedId;

    @SerializedName("informedId")
    @DatabaseField(columnName = "informedId")
    private Integer informedId;

    @SerializedName("qaId")
    @DatabaseField(columnName = "qaId")
    private Integer qaId;

    @SerializedName("changereqno")
    @DatabaseField(columnName = "changereqno")
    private String changereqno;

    @SerializedName("priorityId")
    @DatabaseField(columnName = "priorityId")
    private Integer priorityId;

    @SerializedName("changereqCategoryId")
    @DatabaseField(columnName = "changereqCategoryId")
    private Integer changereqCategoryId;

    @SerializedName("description")
    @DatabaseField(columnName = "description")
    private String description;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private String totaltarget;

    @SerializedName("actualtarget")
    @DatabaseField(columnName = "actualtarget")
    private String actualtarget;

    @SerializedName("fromdate")
    @DatabaseField(columnName = "fromdate")
    private String fromdate;

    @SerializedName("todate")
    @DatabaseField(columnName = "todate")
    private String todate;

    @SerializedName("requestDate")
    @DatabaseField(columnName = "requestDate")
    private String requestDate;

    @SerializedName("deleteFlag")
    @DatabaseField(columnName = "deleteFlag")
    private Boolean deleteFlag;

    @SerializedName("updateFlag")
    @DatabaseField(columnName = "updateFlag")
    private boolean updateFlag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("approvedFlag")
    @DatabaseField(columnName = "approvedFlag")
    private Boolean approvedFlag;

    @SerializedName("completedFlag")
    @DatabaseField(columnName = "completedFlag")
    private Boolean completedFlag;

    @SerializedName("lastModifiedDate")
    @DatabaseField(columnName = "lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("routeassignmentId")
    @DatabaseField(columnName = "routeassignmentId")
    private Long routeassignmentId;

    @SerializedName("unitmeasurementid")
    @DatabaseField(columnName = "unitmeasurementid")
    private Integer unitmeasurementid;

    @SerializedName("categoryname")
    @DatabaseField(columnName = "categoryname")
    private String categoryname;

    @SerializedName("priorityname")
    @DatabaseField(columnName = "priorityname")
    private String priorityname;

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

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getConsultedId() {
        return consultedId;
    }

    public void setConsultedId(Integer consultedId) {
        this.consultedId = consultedId;
    }

    public Integer getInformedId() {
        return informedId;
    }

    public void setInformedId(Integer informedId) {
        this.informedId = informedId;
    }

    public Integer getQaId() {
        return qaId;
    }

    public void setQaId(Integer qaId) {
        this.qaId = qaId;
    }

    public String getChangereqno() {
        return changereqno;
    }

    public void setChangereqno(String changereqno) {
        this.changereqno = changereqno;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getChangereqCategoryId() {
        return changereqCategoryId;
    }

    public void setChangereqCategoryId(Integer changereqCategoryId) {
        this.changereqCategoryId = changereqCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(String totaltarget) {
        this.totaltarget = totaltarget;
    }

    public String getActualtarget() {
        return actualtarget;
    }

    public void setActualtarget(String actualtarget) {
        this.actualtarget = actualtarget;
    }

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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public Boolean getApprovedFlag() {
        return approvedFlag;
    }

    public void setApprovedFlag(Boolean approvedFlag) {
        this.approvedFlag = approvedFlag;
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

    public long getRouteassignmentId() {
        return routeassignmentId;
    }

    public void setRouteassignmentId(long routeassignmentId) {
        this.routeassignmentId = routeassignmentId;
    }

    public Integer getUnitmeasurementid() {
        return unitmeasurementid;
    }

    public void setUnitmeasurementid(Integer unitmeasurementid) {
        this.unitmeasurementid = unitmeasurementid;
    }

    public boolean getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(boolean insertFlag) {
        this.insertFlag = insertFlag;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }


    public String getPriorityname() {
        return priorityname;
    }

    public void setPriorityname(String priorityname) {
        this.priorityname = priorityname;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}