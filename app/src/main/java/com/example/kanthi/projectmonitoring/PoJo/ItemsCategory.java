package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 04/20/2018.
 */

public class ItemsCategory {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("priority")
    @DatabaseField(columnName = "priority")
    private Integer priority;

    @DatabaseField(columnName = "isselected")
    private boolean isselected;

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public ItemsCategory() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ItemsCategory && ((ItemsCategory) o).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public ItemsCategory(int id) {
        this.id = id;
    }

}