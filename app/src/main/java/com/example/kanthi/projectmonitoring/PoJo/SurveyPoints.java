package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class SurveyPoints {

    @SerializedName("linkid")
    @DatabaseField(columnName = "linkid")
    private Integer linkid;

    @SerializedName("locationname")
    @DatabaseField(columnName = "locationname")
    private String locationname;

    @SerializedName("pointa")
    @DatabaseField(columnName = "pointa")
    private String pointa;

    @SerializedName("pointalat")
    @DatabaseField(columnName = "pointalat")
    private String pointalat;

    @SerializedName("pointalong")
    @DatabaseField(columnName = "pointalong")
    private String pointalong;

    @SerializedName("pointb")
    @DatabaseField(columnName = "pointb")
    private String pointb;

    @SerializedName("pointblat")
    @DatabaseField(columnName = "pointblat")
    private String pointblat;

    @SerializedName("pointblong")
    @DatabaseField(columnName = "pointblong")
    private String pointblong;

    @SerializedName("projectid")
    @DatabaseField(columnName = "projectid")
    private Integer projectid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getPointa() {
        return pointa;
    }

    public void setPointa(String pointa) {
        this.pointa = pointa;
    }

    public String getPointalat() {
        return pointalat;
    }

    public void setPointalat(String pointalat) {
        this.pointalat = pointalat;
    }

    public String getPointalong() {
        return pointalong;
    }

    public void setPointalong(String pointalong) {
        this.pointalong = pointalong;
    }

    public String getPointb() {
        return pointb;
    }

    public void setPointb(String pointb) {
        this.pointb = pointb;
    }

    public String getPointblat() {
        return pointblat;
    }

    public void setPointblat(String pointblat) {
        this.pointblat = pointblat;
    }

    public String getPointblong() {
        return pointblong;
    }

    public void setPointblong(String pointblong) {
        this.pointblong = pointblong;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
