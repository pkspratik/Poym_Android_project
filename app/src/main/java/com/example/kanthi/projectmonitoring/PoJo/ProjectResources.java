package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;

/**
 * Created by Kanthi on 3/6/2018.
 */

public class ProjectResources {

    @SerializedName("taskid")
    @DatabaseField(columnName = "taskid")
    private Integer taskid;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("resourceid")
    @DatabaseField(columnName = "resourceid")
    private Integer resourceid;

    @SerializedName("imgreslinkid")
    @DatabaseField(columnName = "imgreslinkid")
    private Integer imgreslinkid;

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("qatime")
    @DatabaseField(columnName = "qatime")
    private String qatime;

    @SerializedName("resourceqty")
    @DatabaseField(columnName = "resourceqty")
    private Integer resourceqty;

    @SerializedName("remarkid")
    @DatabaseField(columnName = "remarkid")
    private Integer remarkid;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("routeassignid")
    @DatabaseField(columnName = "routeassignid")
    private Long routeassignid;

    @SerializedName("taskresourcelinkid")
    @DatabaseField(columnName = "taskresourcelinkid")
    private Integer taskresourcelinkid;

    @SerializedName("approvedqty")
    @DatabaseField(columnName = "approvedqty")
    private Integer approvedqty;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private Long routeassignmentid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Long routeassignmentsummaryid;

    @SerializedName("subtasktypeid")
    @DatabaseField(columnName = "subtasktypeid")
    private Long subtasktypeid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getImgreslinkid() {
        return imgreslinkid;
    }

    public void setImgreslinkid(Integer imgreslinkid) {
        this.imgreslinkid = imgreslinkid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getResourceqty() {
        return resourceqty;
    }

    public void setResourceqty(Integer resourceqty) {
        this.resourceqty = resourceqty;
    }

    public Integer getRemarkid() {
        return remarkid;
    }

    public void setRemarkid(Integer remarkid) {
        this.remarkid = remarkid;
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

    public String getRemarkdesc() {
        return remarkdesc;
    }

    public void setRemarkdesc(String remarkdesc) {
        this.remarkdesc = remarkdesc;
    }

    public long getRouteassignid() {
        return routeassignid;
    }

    public void setRouteassignid(long routeassignid) {
        this.routeassignid = routeassignid;
    }

    public Integer getTaskresourcelinkid() {
        return taskresourcelinkid;
    }

    public void setTaskresourcelinkid(Integer taskresourcelinkid) {
        this.taskresourcelinkid = taskresourcelinkid;
    }

    public Integer getApprovedqty() {
        return approvedqty;
    }

    public void setApprovedqty(Integer approvedqty) {
        this.approvedqty = approvedqty;
    }

    public long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public String getQatime() {
        return qatime;
    }

    public void setQatime(String qatime) {
        this.qatime = qatime;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public Long getSubtasktypeid() {
        return subtasktypeid;
    }

    public void setSubtasktypeid(Long subtasktypeid) {
        this.subtasktypeid = subtasktypeid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
