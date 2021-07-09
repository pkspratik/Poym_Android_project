package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Zones {
    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("length")
    @DatabaseField(columnName = "length")
    private Integer length;

    @SerializedName("unitmeasurementid")
    @DatabaseField(columnName = "unitmeasurementid")
    private Integer unitmeasurementid;

    @SerializedName("type")
    @DatabaseField(columnName = "type")
    private String type;

    @SerializedName("timeinterval")
    @DatabaseField(columnName = "timeinterval")
    private Integer timeinterval;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getUnitmeasurementid() {
        return unitmeasurementid;
    }

    public void setUnitmeasurementid(Integer unitmeasurementid) {
        this.unitmeasurementid = unitmeasurementid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(Integer timeinterval) {
        this.timeinterval = timeinterval;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}