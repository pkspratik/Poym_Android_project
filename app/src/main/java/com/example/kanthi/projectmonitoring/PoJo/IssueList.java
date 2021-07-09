package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 4/30/2018.
 */

public class IssueList {

    @SerializedName("task")
    @DatabaseField(columnName = "task")
    private String task;

    @SerializedName("taskid")
    @DatabaseField(columnName = "taskid")
    private Integer taskid;

    @SerializedName("tasktype")
    @DatabaseField(columnName = "tasktype")
    private String tasktype;

    @SerializedName("tasktypeid")
    @DatabaseField(columnName = "tasktypeid")
    private Integer tasktypeid;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("statusid")
    @DatabaseField(columnName = "statusid")
    private Integer statusid;

    @SerializedName("importance")
    @DatabaseField(columnName = "importance")
    private String importance;

    @SerializedName("importanceid")
    @DatabaseField(columnName = "importanceid")
    private Integer importanceid;

    @SerializedName("remark")
    @DatabaseField(columnName = "remark")
    private String remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("routeassignid")
    @DatabaseField(columnName = "routeassignid")
    private Integer routeassignid;

    @SerializedName("employeeid")
    @DatabaseField(columnName = "employeeid")
    private Integer employeeid;

    @SerializedName("managerid")
    @DatabaseField(columnName = "managerid")
    private Integer managerid;

    @SerializedName("project")
    @DatabaseField(columnName = "project")
    private Integer project;

    @SerializedName("link")
    @DatabaseField(columnName = "link")
    private Integer link;

    @SerializedName("location")
    @DatabaseField(columnName = "location")
    private Integer location;

    @SerializedName("routeassigndate")
    @DatabaseField(columnName = "routeassigndate")
    private String routeassigndate;

    @SerializedName("insertdate")
    @DatabaseField(columnName = "insertdate")
    private String insertdate;

    @SerializedName("closeddate")
    @DatabaseField(columnName = "closeddate")
    private String closeddate;

    @SerializedName("closedremark")
    @DatabaseField(columnName = "closedremark")
    private String closedremark;

    @SerializedName("closedflag")
    @DatabaseField(columnName = "closedflag")
    private Boolean closedflag;

    @SerializedName("assignedTo")
    @DatabaseField(columnName = "assignedTo")
    private Integer assignedTo;

    @SerializedName("issuetypeId")
    @DatabaseField(columnName = "issuetypeId")
    private Integer issuetypeId;

    @SerializedName("dueDate")
    @DatabaseField(columnName = "dueDate")
    private String dueDate;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public Integer getTasktypeid() {
        return tasktypeid;
    }

    public void setTasktypeid(Integer tasktypeid) {
        this.tasktypeid = tasktypeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public Integer getImportanceid() {
        return importanceid;
    }

    public void setImportanceid(Integer importanceid) {
        this.importanceid = importanceid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkdesc() {
        return remarkdesc;
    }

    public void setRemarkdesc(String remarkdesc) {
        this.remarkdesc = remarkdesc;
    }

    public Integer getRouteassignid() {
        return routeassignid;
    }

    public void setRouteassignid(Integer routeassignid) {
        this.routeassignid = routeassignid;
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

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getLink() {
        return link;
    }

    public void setLink(Integer link) {
        this.link = link;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getRouteassigndate() {
        return routeassigndate;
    }

    public void setRouteassigndate(String routeassigndate) {
        this.routeassigndate = routeassigndate;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getCloseddate() {
        return closeddate;
    }

    public void setCloseddate(String closeddate) {
        this.closeddate = closeddate;
    }

    public String getClosedremark() {
        return closedremark;
    }

    public void setClosedremark(String closedremark) {
        this.closedremark = closedremark;
    }

    public Boolean getClosedflag() {
        return closedflag;
    }

    public void setClosedflag(Boolean closedflag) {
        this.closedflag = closedflag;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getIssuetypeId() {
        return issuetypeId;
    }

    public void setIssuetypeId(Integer issuetypeId) {
        this.issuetypeId = issuetypeId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}