package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.List;

public class DistributionAreas {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("imagename")
    @DatabaseField(columnName = "imagename")
    private String imagename;

    @SerializedName("description")
    @DatabaseField(columnName = "description")
    private String  description;

    @SerializedName("seq")
    @DatabaseField(columnName = "seq")
    private Integer seq;

    @SerializedName("createdDate")
    @DatabaseField(columnName = "createdDate")
    private String createdDate;

    @SerializedName("lastModifiedDate")
    @DatabaseField(columnName = "lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("shapes")
    @ForeignCollectionField(eager = false)
    private List<com.example.kanthi.projectmonitoring.PoJo.Shape> shapes = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<com.example.kanthi.projectmonitoring.PoJo.Shape> getShapes() { return this.shapes; }

    public void setShapes(List<com.example.kanthi.projectmonitoring.PoJo.Shape> shapes) { this.shapes = shapes; }

}