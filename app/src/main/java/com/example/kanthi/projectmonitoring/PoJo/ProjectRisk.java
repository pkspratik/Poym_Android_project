package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 4/30/2018.
 */

public class ProjectRisk {

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

    @SerializedName("risktypeId")
    @DatabaseField(columnName = "taskrisktypeIdid")
    private Integer risktypeId;

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("shortSummary")
    @DatabaseField(columnName = "shortSummary")
    private String shortSummary;

    @SerializedName("description")
    @DatabaseField(columnName = "description")
    private String description;

    @SerializedName("likelyOccuranceDate")
    @DatabaseField(columnName = "likelyOccuranceDate")
    private String likelyOccuranceDate;

    @SerializedName("riskprobabilityId")
    @DatabaseField(columnName = "riskprobabilityId")
    private Integer riskprobabilityId;

    @SerializedName("impactId")
    @DatabaseField(columnName = "impactId")
    private Integer impactId;

    @SerializedName("responseId")
    @DatabaseField(columnName = "responseId")
    private Integer responseId;

    @SerializedName("assignedTo")
    @DatabaseField(columnName = "assignedTo")
    private String assignedTo;

    @SerializedName("dueDate")
    @DatabaseField(columnName = "dueDate")
    private String dueDate;

    @SerializedName("mitigationPlan")
    @DatabaseField(columnName = "mitigationPlan")
    private String mitigationPlan;

    @SerializedName("contingencyPlan")
    @DatabaseField(columnName = "contingencyPlan")
    private String contingencyPlan;

    @SerializedName("closedate")
    @DatabaseField(columnName = "closedate")
    private String closedate;

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

    @SerializedName("closeremark")
    @DatabaseField(columnName = "closeremark")
    private String closeremark;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Long id;

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

    public Integer getRisktypeId() {
        return risktypeId;
    }

    public void setRisktypeId(Integer risktypeId) {
        this.risktypeId = risktypeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLikelyOccuranceDate() {
        return likelyOccuranceDate;
    }

    public void setLikelyOccuranceDate(String likelyOccuranceDate) {
        this.likelyOccuranceDate = likelyOccuranceDate;
    }

    public Integer getRiskprobabilityId() {
        return riskprobabilityId;
    }

    public void setRiskprobabilityId(Integer riskprobabilityId) {
        this.riskprobabilityId = riskprobabilityId;
    }

    public Integer getImpactId() {
        return impactId;
    }

    public void setImpactId(Integer impactId) {
        this.impactId = impactId;
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMitigationPlan() {
        return mitigationPlan;
    }

    public void setMitigationPlan(String mitigationPlan) {
        this.mitigationPlan = mitigationPlan;
    }

    public String getContingencyPlan() {
        return contingencyPlan;
    }

    public void setContingencyPlan(String contingencyPlan) {
        this.contingencyPlan = contingencyPlan;
    }

    public String getClosedate() {
        return closedate;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
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

    public String getCloseremark() {
        return closeremark;
    }

    public void setCloseremark(String closeremark) {
        this.closeremark = closeremark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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