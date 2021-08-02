package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/2/2018.
 */

public class  Surveys {

    @SerializedName("linkid")
    @DatabaseField(columnName = "linkid")
    private Integer linkid;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private Double latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private Double longitude;

    @SerializedName("slno")
    @DatabaseField(columnName = "slno")
    private Integer slno;

    @SerializedName("landmark")
    @DatabaseField(columnName = "landmark")
    private String landmark;

    @SerializedName("projectid")
    @DatabaseField(columnName = "projectid")
    private Integer projectid;

    @SerializedName("userid")
    @DatabaseField(columnName = "userid")
    private Integer userid;

    @SerializedName("imagename")
    @DatabaseField(columnName = "imagename")
    private String imagename;

    @SerializedName("to_latitude")
    @DatabaseField(columnName = "toLatitude")
    private Double toLatitude;

    @SerializedName("to_longitude")
    @DatabaseField(columnName = "toLongitude")
    private Double toLongitude;

    @SerializedName("pendingflag")
    @DatabaseField(columnName = "pendingflag")
    private Boolean pendingflag;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("distareaid")
    @DatabaseField(columnName = "distareaid")
    private Integer distareaid;

    @SerializedName("distsubareaid")
    @DatabaseField(columnName = "distsubareaid")
    private Integer distsubareaid;

    @SerializedName("remarkid")
    @DatabaseField(columnName = "remarkid")
    private Integer remarkid;

    @SerializedName("remark")
    @DatabaseField(columnName = "remark")
    private String remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("qaflag")
    @DatabaseField(columnName = "qaflag")
    private Boolean qaflag;

    @SerializedName("distance")
    @DatabaseField(columnName = "distance")
    private Double distance;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private Long routeassignmentid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Long routeassignmentsummaryid;

    @SerializedName("deleteFlag")
    @DatabaseField(columnName = "deleteFlag")
    private Boolean deleteFlag;

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("fromaddress")
    @DatabaseField(columnName = "fromaddress")
    private String fromaddress;

    @SerializedName("toaddress")
    @DatabaseField(columnName = "toaddress")
    private String toaddress;

    @SerializedName("order")
    @DatabaseField(columnName = "order")
    private Integer order;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;



    @SerializedName("detail")
    @DatabaseField(columnName = "detail")
    private String detail;

    @SerializedName("prevslno")
    @DatabaseField(columnName = "prevslno")
    private Integer prevslno;




    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public double getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(double toLatitude) {
        this.toLatitude = toLatitude;
    }

    public double getToLongitude() {
        return toLongitude;
    }

    public void setToLongitude(double toLongitude) {
        this.toLongitude = toLongitude;
    }

    public Boolean getPendingflag() {
        return pendingflag;
    }

    public void setPendingflag(Boolean pendingflag) {
        this.pendingflag = pendingflag;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getDistareaid() {
        return distareaid;
    }

    public void setDistareaid(Integer distareaid) {
        this.distareaid = distareaid;
    }

    public Integer getDistsubareaid() {
        return distsubareaid;
    }

    public void setDistsubareaid(Integer distsubareaid) {
        this.distsubareaid = distsubareaid;
    }

    public Integer getRemarkid() {
        return remarkid;
    }

    public void setRemarkid(Integer remarkid) {
        this.remarkid = remarkid;
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

    public Boolean getQaflag() {
        return qaflag;
    }

    public void setQaflag(Boolean qaflag) {
        this.qaflag = qaflag;
    }

    public long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getFromaddress() {
        return fromaddress;
    }

    public void setFromaddress(String fromaddress) {
        this.fromaddress = fromaddress;
    }

    public String getToaddress() {
        return toaddress;
    }

    public void setToaddress(String toaddress) {
        this.toaddress = toaddress;
    }


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String details) {
        this.detail = details;
    }

    public Integer getPrevslno() {
        return prevslno;
    }

    public void setPrevslno(Integer prevslno) {
        this.prevslno = prevslno;
    }


}