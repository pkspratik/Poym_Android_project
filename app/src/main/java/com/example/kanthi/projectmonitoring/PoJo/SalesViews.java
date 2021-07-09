package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 2/27/2018.
 */

public class SalesViews {

    @SerializedName("salescode")
    @DatabaseField(columnName = "salescode")
    private String salescode;

    @SerializedName("fname")
    @DatabaseField(columnName = "fname")
    private String fname;

    @SerializedName("lname")
    @DatabaseField(columnName = "lname")
    private String lname;

    @SerializedName("zone")
    @DatabaseField(columnName = "zone")
    private String zone;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("area")
    @DatabaseField(columnName = "area")
    private String area;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("distarea")
    @DatabaseField(columnName = "distarea")
    private String distarea;

    @SerializedName("distareaid")
    @DatabaseField(columnName = "distareaid")
    private Integer distareaid;

    @SerializedName("salesmgrid")
    @DatabaseField(columnName = "salesmgrid")
    private Integer salesmgrid;

    @SerializedName("salesmgr")
    @DatabaseField(columnName = "salesmgr")
    private String salesmgr;

    @SerializedName("surveycount")
    @DatabaseField(columnName = "surveycount")
    private Integer surveycount;

    @SerializedName("interval")
    @DatabaseField(columnName = "interval")
    private String interval;

    @SerializedName("groupid")
    @DatabaseField(columnName = "groupid")
    private Integer groupid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getSalescode() {
        return salescode;
    }

    public void setSalescode(String salescode) {
        this.salescode = salescode;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public String getDistarea() {
        return distarea;
    }

    public void setDistarea(String distarea) {
        this.distarea = distarea;
    }

    public Integer getDistareaid() {
        return distareaid;
    }

    public void setDistareaid(Integer distareaid) {
        this.distareaid = distareaid;
    }

    public Integer getSalesmgrid() {
        return salesmgrid;
    }

    public void setSalesmgrid(Integer salesmgrid) {
        this.salesmgrid = salesmgrid;
    }

    public String getSalesmgr() {
        return salesmgr;
    }

    public void setSalesmgr(String salesmgr) {
        this.salesmgr = salesmgr;
    }

    public Integer getSurveycount() {
        return surveycount;
    }

    public void setSurveycount(Integer surveycount) {
        this.surveycount = surveycount;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}