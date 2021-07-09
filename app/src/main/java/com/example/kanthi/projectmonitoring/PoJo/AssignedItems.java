package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 5/21/2018.
 */

public class AssignedItems {

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @DatabaseField(columnName = "distributionAreaId")
    private Integer distributionAreaId;

    @SerializedName("partnerId")
    @DatabaseField(columnName = "partnerId")
    private Integer partnerId;

    @SerializedName("managerid")
    @DatabaseField(columnName = "managerid")
    private Integer managerid;

    @SerializedName("warehouseid")
    @DatabaseField(columnName = "warehouseid")
    private Integer warehouseid;

    @SerializedName("itemdefinitionid")
    @DatabaseField(columnName = "itemdefinitionid")
    private Integer itemdefinitionid;

    @SerializedName("quantityinput")
    @DatabaseField(columnName = "quantityinput")
    private String quantityinput;

    @SerializedName("quantitytransfer")
    @DatabaseField(columnName = "quantitytransfer")
    private String quantitytransfer;

    @SerializedName("quantityused")
    @DatabaseField(columnName = "quantityused")
    private String quantityused;

    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("itemname")
    @DatabaseField(columnName = "itemname")
    private String itemname;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("qatime")
    @DatabaseField(columnName = "qatime")
    private String qatime;

    @SerializedName("warehousetype")
    @DatabaseField(columnName = "warehousetype")
    private String warehousetype;

    @SerializedName("linkid")
    @DatabaseField(columnName = "linkid")
    private Integer linkid;

    @SerializedName("tasktypeid")
    @DatabaseField(columnName = "tasktypeid")
    private Integer tasktypeid;

    @SerializedName("subtasktypeid")
    @DatabaseField(columnName = "subtasktypeid")
    private Long subtasktypeid;

    @SerializedName("itemapproved")
    @DatabaseField(columnName = "itemapproved")
    private String itemapproved;

    @SerializedName("itemid")
    @DatabaseField(columnName = "itemid")
    private Integer itemid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Long routeassignmentsummaryid;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private Long routeassignmentid;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

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

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(Integer warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Integer getItemdefinitionid() {
        return itemdefinitionid;
    }

    public void setItemdefinitionid(Integer itemdefinitionid) {
        this.itemdefinitionid = itemdefinitionid;
    }

    public String getQuantityinput() {
        return quantityinput;
    }

    public void setQuantityinput(String quantityinput) {
        this.quantityinput = quantityinput;
    }

    public String getQuantitytransfer() {
        return quantitytransfer;
    }

    public void setQuantitytransfer(String quantitytransfer) {
        this.quantitytransfer = quantitytransfer;
    }

    public String getQatime() {
        return qatime;
    }

    public void setQatime(String qatime) {
        this.qatime = qatime;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getQuantityused() {
        return quantityused;
    }

    public void setQuantityused(String quantityused) {
        this.quantityused = quantityused;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getWarehousetype() {
        return warehousetype;
    }

    public void setWarehousetype(String warehousetype) {
        this.warehousetype = warehousetype;
    }

    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }


    public Integer getTasktypeid() {
        return tasktypeid;
    }

    public void setTasktypeid(Integer tasktypeid) {
        this.tasktypeid = tasktypeid;
    }

    public Long getSubtasktypeid() {
        return subtasktypeid;
    }

    public void setSubtasktypeid(Long subtasktypeid) {
        this.subtasktypeid = subtasktypeid;
    }


    public String getItemapproved() {
        return itemapproved;
    }

    public void setItemapproved(String itemapproved) {
        this.itemapproved = itemapproved;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }


    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}