package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class ProjectIssues {

    @SerializedName("projectid")
    @DatabaseField(columnName = "projectid")
    private Integer projectid;

    @SerializedName("project")
    @DatabaseField(columnName = "project")
    private String project;

    @SerializedName("linkid")
    @DatabaseField(columnName = "linkid")
    private Integer linkid;

    @SerializedName("linkname")
    @DatabaseField(columnName = "linkname")
    private String linkname;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("areaname")
    @DatabaseField(columnName = "areaname")
    private String areaname;

    @SerializedName("taskid")
    @DatabaseField(columnName = "taskid")
    private Integer taskid;

    @SerializedName("task")
    @DatabaseField(columnName = "task")
    private String task;

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("importance")
    @DatabaseField(columnName = "importance")
    private String importance;

    @SerializedName("remark")
    @DatabaseField(columnName = "remark")
    private String remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("closedflag")
    @DatabaseField(columnName = "closedflag")
    private Boolean closedflag;

    @SerializedName("trueflag")
    @DatabaseField(columnName = "trueflag")
    private Integer trueflag;

    @SerializedName("falseflag")
    @DatabaseField(columnName = "falseflag")
    private Integer falseflag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
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

    public Boolean getClosedflag() {
        return closedflag;
    }

    public void setClosedflag(Boolean closedflag) {
        this.closedflag = closedflag;
    }

    public Integer getTrueflag() {
        return trueflag;
    }

    public void setTrueflag(Integer trueflag) {
        this.trueflag = trueflag;
    }

    public Integer getFalseflag() {
        return falseflag;
    }

    public void setFalseflag(Integer falseflag) {
        this.falseflag = falseflag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
