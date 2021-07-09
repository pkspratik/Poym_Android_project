package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class TaskItemLinkView {
    
    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("tasktypeid")
    @DatabaseField(columnName = "tasktypeid")
    private Integer tasktypeid;

    @SerializedName("resourcetypeid")
    @DatabaseField(columnName = "resourcetypeid")
    private Integer resourcetypeid;

    @SerializedName("quantity")
    @DatabaseField(columnName = "quantity")
    private Integer quantity;

    @SerializedName("itemcost")
    @DatabaseField(columnName = "itemcost")
    private String itemcost;

    @SerializedName("resourceunitid")
    @DatabaseField(columnName = "resourceunitid")
    private Integer resourceunitid;

    @SerializedName("itemid")
    @DatabaseField(columnName = "itemid")
    private Integer itemid;

    @SerializedName("itemname")
    @DatabaseField(columnName = "itemname")
    private String itemname;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("unitprice")
    @DatabaseField(columnName = "unitprice")
    private Integer unitprice;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getTasktypeid() {
        return tasktypeid;
    }

    public void setTasktypeid(Integer tasktypeid) {
        this.tasktypeid = tasktypeid;
    }

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemcost() {
        return itemcost;
    }

    public void setItemcost(String itemcost) {
        this.itemcost = itemcost;
    }

    public Integer getResourceunitid() {
        return resourceunitid;
    }

    public void setResourceunitid(Integer resourceunitid) {
        this.resourceunitid = resourceunitid;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Integer unitprice) {
        this.unitprice = unitprice;
    }
}
