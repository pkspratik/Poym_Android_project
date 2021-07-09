package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Sales_Area {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}