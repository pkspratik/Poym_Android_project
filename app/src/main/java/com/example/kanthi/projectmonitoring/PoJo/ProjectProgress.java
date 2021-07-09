package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class ProjectProgress {
    @SerializedName("projectid")
    @DatabaseField(columnName = "projectid")
    private Integer projectid;

    @SerializedName("projectname")
    @DatabaseField(columnName = "projectname")
    private String projectname;

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

    @SerializedName("taskname")
    @DatabaseField(columnName = "taskname")
    private String taskname;

    @SerializedName("worktypeid")
    @DatabaseField(columnName = "worktypeid")
    private Integer worktypeid;

    @SerializedName("worktypename")
    @DatabaseField(columnName = "worktypename")
    private String worktypename;

    @SerializedName("noofdays")
    @DatabaseField(columnName = "noofdays")
    private Integer noofdays;

    @SerializedName("totalactual")
    @DatabaseField(columnName = "totalactual")
    private Integer totalactual;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private Float totaltarget;

    @SerializedName("unitid")
    @DatabaseField(columnName = "unitid")
    private Integer unitid;

    @SerializedName("unitname")
    @DatabaseField(columnName = "unitname")
    private String unitname;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
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

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public String getWorktypename() {
        return worktypename;
    }

    public void setWorktypename(String worktypename) {
        this.worktypename = worktypename;
    }

    public Integer getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(Integer noofdays) {
        this.noofdays = noofdays;
    }

    public Integer getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(Integer totalactual) {
        this.totalactual = totalactual;
    }

    public Float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(Float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
