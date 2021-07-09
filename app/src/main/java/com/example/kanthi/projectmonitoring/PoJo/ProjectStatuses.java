package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/7/2018.
 */

public class ProjectStatuses {

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

    @SerializedName("percentcompleted")
    @DatabaseField(columnName = "percentcompleted")
    private float percentcompleted;

    @SerializedName("desccompleted")
    @DatabaseField(columnName = "desccompleted")
    private String desccompleted;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("statustime")
    @DatabaseField(columnName = "statustime")
    private String statustime;

    @SerializedName("target")
    @DatabaseField(columnName = "target")
    private Float target;

    @SerializedName("actual")
    @DatabaseField(columnName = "actual")
    private Float actual;

    @SerializedName("remark")
    @DatabaseField(columnName = "remark")
    private Integer remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("routeassignid")
    @DatabaseField(columnName = "routeassignid")
    private long routeassignid;

    @SerializedName("employeeid")
    @DatabaseField(columnName = "employeeid")
    private Integer employeeid;

    @SerializedName("flatitudeone")
    @DatabaseField(columnName = "flatitudeone")
    private String flatitudeone;

    @SerializedName("flongitudeone")
    @DatabaseField(columnName = "flongitudeone")
    private String flongitudeone;

    @SerializedName("flatitudetwo")
    @DatabaseField(columnName = "flatitudetwo")
    private String flatitudetwo;

    @SerializedName("flongitudetwo")
    @DatabaseField(columnName = "flongitudetwo")
    private String flongitudetwo;

    @SerializedName("flatitudethree")
    @DatabaseField(columnName = "flatitudethree")
    private String flatitudethree;

    @SerializedName("flongitudethree")
    @DatabaseField(columnName = "flongitudethree")
    private String flongitudethree;

    @SerializedName("flatitudefour")
    @DatabaseField(columnName = "flatitudefour")
    private String flatitudefour;

    @SerializedName("flongitudefour")
    @DatabaseField(columnName = "flongitudefour")
    private String flongitudefour;

    @SerializedName("tlatitudeone")
    @DatabaseField(columnName = "tlatitudeone")
    private String tlatitudeone;

    @SerializedName("tlongitudeone")
    @DatabaseField(columnName = "tlongitudeone")
    private String tlongitudeone;

    @SerializedName("tlatitudetwo")
    @DatabaseField(columnName = "tlatitudetwo")
    private String tlatitudetwo;

    @SerializedName("tlongitudetwo")
    @DatabaseField(columnName = "tlongitudetwo")
    private String tlongitudetwo;

    @SerializedName("tlatitudethree")
    @DatabaseField(columnName = "tlatitudethree")
    private String tlatitudethree;

    @SerializedName("tlongitudethree")
    @DatabaseField(columnName = "tlongitudethree")
    private String tlongitudethree;

    @SerializedName("tlatitudefour")
    @DatabaseField(columnName = "tlatitudefour")
    private String tlatitudefour;

    @SerializedName("tlongitudefour")
    @DatabaseField(columnName = "tlongitudefour")
    private String tlongitudefour;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @DatabaseField(columnName = "distributionAreaId")
    private Integer distributionAreaId;

    @SerializedName("qaremark")
    @DatabaseField(columnName = "qaremark")
    private Integer qaremark;

    @SerializedName("qaremarkdesc")
    @DatabaseField(columnName = "qaremarkdesc")
    private String qaremarkdesc;

    @SerializedName("qasubmitflag")
    @DatabaseField(columnName = "qasubmitflag")
    private boolean qasubmitflag;

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

    public float getPercentcompleted() {
        return percentcompleted;
    }

    public void setPercentcompleted(float percentcompleted) {
        this.percentcompleted = percentcompleted;
    }

    public String getDesccompleted() {
        return desccompleted;
    }

    public void setDesccompleted(String desccompleted) {
        this.desccompleted = desccompleted;
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

    public String getStatustime() {
        return statustime;
    }

    public void setStatustime(String statustime) {
        this.statustime = statustime;
    }

    public Float getTarget() {
        return target;
    }

    public void setTarget(Float target) {
        this.target = target;
    }

    public Float getActual() {
        return actual;
    }

    public void setActual(Float actual) {
        this.actual = actual;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    public String getRemarkdesc() {
        return remarkdesc;
    }

    public void setRemarkdesc(String remarkdesc) {
        this.remarkdesc = remarkdesc;
    }

    public Long getRouteassignid() {
        return routeassignid;
    }

    public void setRouteassignid(Long routeassignid) {
        this.routeassignid = routeassignid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public String getFlatitudeone() {
        return flatitudeone;
    }

    public void setFlatitudeone(String flatitudeone) {
        this.flatitudeone = flatitudeone;
    }

    public String getFlongitudeone() {
        return flongitudeone;
    }

    public void setFlongitudeone(String flongitudeone) {
        this.flongitudeone = flongitudeone;
    }

    public String getFlatitudetwo() {
        return flatitudetwo;
    }

    public void setFlatitudetwo(String flatitudetwo) {
        this.flatitudetwo = flatitudetwo;
    }

    public String getFlongitudetwo() {
        return flongitudetwo;
    }

    public void setFlongitudetwo(String flongitudetwo) {
        this.flongitudetwo = flongitudetwo;
    }

    public String getFlatitudethree() {
        return flatitudethree;
    }

    public void setFlatitudethree(String flatitudethree) {
        this.flatitudethree = flatitudethree;
    }

    public String getFlongitudethree() {
        return flongitudethree;
    }

    public void setFlongitudethree(String flongitudethree) {
        this.flongitudethree = flongitudethree;
    }

    public String getFlatitudefour() {
        return flatitudefour;
    }

    public void setFlatitudefour(String flatitudefour) {
        this.flatitudefour = flatitudefour;
    }

    public String getFlongitudefour() {
        return flongitudefour;
    }

    public void setFlongitudefour(String flongitudefour) {
        this.flongitudefour = flongitudefour;
    }

    public String getTlatitudeone() {
        return tlatitudeone;
    }

    public void setTlatitudeone(String tlatitudeone) {
        this.tlatitudeone = tlatitudeone;
    }

    public String getTlongitudeone() {
        return tlongitudeone;
    }

    public void setTlongitudeone(String tlongitudeone) {
        this.tlongitudeone = tlongitudeone;
    }

    public String getTlatitudetwo() {
        return tlatitudetwo;
    }

    public void setTlatitudetwo(String tlatitudetwo) {
        this.tlatitudetwo = tlatitudetwo;
    }

    public String getTlongitudetwo() {
        return tlongitudetwo;
    }

    public void setTlongitudetwo(String tlongitudetwo) {
        this.tlongitudetwo = tlongitudetwo;
    }

    public String getTlatitudethree() {
        return tlatitudethree;
    }

    public void setTlatitudethree(String tlatitudethree) {
        this.tlatitudethree = tlatitudethree;
    }

    public String getTlongitudethree() {
        return tlongitudethree;
    }

    public void setTlongitudethree(String tlongitudethree) {
        this.tlongitudethree = tlongitudethree;
    }

    public String getTlatitudefour() {
        return tlatitudefour;
    }

    public void setTlatitudefour(String tlatitudefour) {
        this.tlatitudefour = tlatitudefour;
    }

    public String getTlongitudefour() {
        return tlongitudefour;
    }

    public void setTlongitudefour(String tlongitudefour) {
        this.tlongitudefour = tlongitudefour;
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

    public Integer getQaremark() {
        return qaremark;
    }

    public void setQaremark(Integer qaremark) {
        this.qaremark = qaremark;
    }

    public String getQaremarkdesc() {
        return qaremarkdesc;
    }

    public void setQaremarkdesc(String qaremarkdesc) {
        this.qaremarkdesc = qaremarkdesc;
    }

    public boolean isQasubmitflag() {
        return qasubmitflag;
    }

    public void setQasubmitflag(boolean qasubmitflag) {
        this.qasubmitflag = qasubmitflag;
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
