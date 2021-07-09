package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 04/20/2018.
 */

public class ItemType {
    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("itemcategoryId")
    @DatabaseField(columnName = "itemcategoryId")
    private Integer itemcategoryId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("parentpriority")
    @DatabaseField(columnName = "parentpriority")
    private Integer parentpriority;

    @SerializedName("childpriority")
    @DatabaseField(columnName = "childpriority")
    private Integer childpriority;

    @DatabaseField(columnName = "isselected")
    private boolean isselected;

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public Integer getParentpriority() {
        return parentpriority;
    }

    public void setParentpriority(Integer parentpriority) {
        this.parentpriority = parentpriority;
    }

    public Integer getChildpriority() {
        return childpriority;
    }

    public void setChildpriority(Integer childpriority) {
        this.childpriority = childpriority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemcategoryId() {
        return itemcategoryId;
    }

    public void setItemcategoryId(Integer itemcategoryId) {
        this.itemcategoryId = itemcategoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemType() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ItemType && ((ItemType) o).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public ItemType(int id) {
        this.id = id;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }


}
