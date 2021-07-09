package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/5/2018.
 */

public class TaskResourceLinkViews {

    @SerializedName("tasktypeid")
    @DatabaseField(columnName = "tasktypeid")
    private Integer tasktypeid;

    @SerializedName("resourcetypeid")
    @DatabaseField(columnName = "resourcetypeid")
    private Integer resourcetypeid;

    @SerializedName("resourceunitid")
    @DatabaseField(columnName = "resourceunitid")
    private Integer resourceunitid;

    @SerializedName("tasktype")
    @DatabaseField(columnName = "tasktype")
    private String tasktype;

    @SerializedName("resourcetype")
    @DatabaseField(columnName = "resourcetype")
    private String resourcetype;

    @SerializedName("resourceunit")
    @DatabaseField(columnName = "resourceunit")
    private String resourceunit;

    @SerializedName("resourceapproved")
    @DatabaseField(columnName = "resourceapproved")
    private String resourceapproved;

    @SerializedName("numberofresources")
    @DatabaseField(columnName = "numberofresources")
    private Integer numberofresources;

    @SerializedName("actualofresources")
    @DatabaseField(columnName = "actualofresources")
    private Integer actualofresources;

    @SerializedName("enteredqty")
    @DatabaseField(columnName = "enteredqty")
    private Integer enteredqty;

    @SerializedName("projectResourceId")
    @DatabaseField(columnName = "projectResourceId")
    private long projectResourceId;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("resourcecost")
    @DatabaseField(columnName = "resourcecost")
    private String resourcecost;

    @SerializedName("qa_res_time")
    @DatabaseField(columnName = "qa_res_time")
    private String qa_res_time;

    @SerializedName("qatime")
    @DatabaseField(columnName = "qatime")
    private String qatime;

    @SerializedName("routeassignid")
    @DatabaseField(columnName = "routeassignid")
    private Integer routeassignid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getTasktypeid() {
        return tasktypeid;
    }

    public void setTasktypeid(Integer tasktypeid) {
        this.tasktypeid = tasktypeid;
    }

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public Integer getResourceunitid() {
        return resourceunitid;
    }

    public void setResourceunitid(Integer resourceunitid) {
        this.resourceunitid = resourceunitid;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getResourceunit() {
        return resourceunit;
    }

    public void setResourceunit(String resourceunit) {
        this.resourceunit = resourceunit;
    }


    public Integer getNumberofresources() {
        return numberofresources;
    }

    public void setNumberofresources(Integer numberofresources) {
        this.numberofresources = numberofresources;
    }

    public String getResourceapproved() {
        return resourceapproved;
    }

    public void setResourceapproved(String resourceapproved) {
        this.resourceapproved = resourceapproved;
    }

    public Integer getActualofresources() {
        return actualofresources;
    }

    public long getProjectResourceId() {
        return projectResourceId;
    }

    public void setProjectResourceId(long projectResourceId) {
        this.projectResourceId = projectResourceId;
    }

    public void setActualofresources(Integer actualofresources) {
        this.actualofresources = actualofresources;
    }

    public Integer getEnteredqty() {
        return enteredqty;
    }

    public void setEnteredqty(Integer enteredqty) {
        this.enteredqty = enteredqty;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getResourcecost() {
        return resourcecost;
    }

    public void setResourcecost(String resourcecost) {
        this.resourcecost = resourcecost;
    }


    public String getQa_res_time() {
        return qa_res_time;
    }

    public void setQa_res_time(String qa_res_time) {
        this.qa_res_time = qa_res_time;
    }


    public String getQatime() {
        return qatime;
    }

    public void setQatime(String qatime) {
        this.qatime = qatime;
    }


    public Integer getRouteassignid() {
        return routeassignid;
    }

    public void setRouteassignid(Integer routeassignid) {
        this.routeassignid = routeassignid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}