package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthii on 5/21/2018.
 */

public class Inventories {

    @SerializedName("batchnumber")
    @DatabaseField(columnName = "batchnumber")
    private String batchnumber;

    @SerializedName("quantityinput")
    @DatabaseField(columnName = "quantityinput")
    private String quantityinput;

    @SerializedName("quantitytransfer")
    @DatabaseField(columnName = "quantitytransfer")
    private Integer quantitytransfer;

    @SerializedName("quantityreturn")
    @DatabaseField(columnName = "quantityreturn")
    private Integer quantityreturn;

    @SerializedName("expirydate")
    @DatabaseField(columnName = "expirydate")
    private String expirydate;

    @SerializedName("qatime")
    @DatabaseField(columnName = "qatime")
    private String qatime;

    @SerializedName("verified")
    @DatabaseField(columnName = "verified")
    private String verified;

    @SerializedName("soid")
    @DatabaseField(columnName = "soid")
    private String soid;

    @SerializedName("linkId")
    @DatabaseField(columnName = "linkId")
    private String linkId;

    @SerializedName("warehouseflag")
    @DatabaseField(columnName = "warehouseflag")
    private Boolean warehouseflag;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("warehouseId")
    @DatabaseField(columnName = "warehouseId")
    private Integer warehouseId;

    @SerializedName("itemdefinitionId")
    @DatabaseField(columnName = "itemdefinitionId")
    private Integer itemdefinitionId;

    @SerializedName("itemstatusId")
    @DatabaseField(columnName = "itemstatusId")
    private Integer itemstatusId;

    @SerializedName("itemname")
    @DatabaseField(columnName = "itemname")
    private String itemname;

    @SerializedName("inventoryapproved")
    @DatabaseField(columnName = "inventoryapproved")
    private String inventoryapproved;

    @SerializedName("enteredquantity")
    @DatabaseField(columnName = "enteredquantity")
    private Integer enteredquantity;

    @SerializedName("inventoryid")
    @DatabaseField(columnName = "inventoryid")
    private Integer inventoryid;

    @SerializedName("itemnameid")
    @Expose
    private Integer itemnameid;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("popup")
    @DatabaseField(columnName = "popup")
    private Boolean popup;

    public String getBatchnumber() {
        return batchnumber;
    }

    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }

    public String getQuantityinput() {
        return quantityinput;
    }

    public void setQuantityinput(String quantityinput) {
        this.quantityinput = quantityinput;
    }

    public Integer getQuantitytransfer() {
        return quantitytransfer;
    }

    public void setQuantitytransfer(Integer quantitytransfer) {
        this.quantitytransfer = quantitytransfer;
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

    public Integer getQuantityreturn() {
        return quantityreturn;
    }

    public void setQuantityreturn(Integer quantityreturn) {
        this.quantityreturn = quantityreturn;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getQatime() {
        return qatime;
    }

    public void setQatime(String qatime) {
        this.qatime = qatime;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getSoid() {
        return soid;
    }

    public void setSoid(String soid) {
        this.soid = soid;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Boolean getWarehouseflag() {
        return warehouseflag;
    }

    public void setWarehouseflag(Boolean warehouseflag) {
        this.warehouseflag = warehouseflag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getItemdefinitionId() {
        return itemdefinitionId;
    }

    public void setItemdefinitionId(Integer itemdefinitionId) {
        this.itemdefinitionId = itemdefinitionId;
    }

    public Integer getItemstatusId() {
        return itemstatusId;
    }

    public void setItemstatusId(Integer itemstatusId) {
        this.itemstatusId = itemstatusId;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getEnteredquantity() {
        return enteredquantity;
    }

    public void setEnteredquantity(Integer enteredquantity) {
        this.enteredquantity = enteredquantity;
    }

    public String getInventoryapproved() {
        return inventoryapproved;
    }

    public void setInventoryapproved(String inventoryapproved) {
        this.inventoryapproved = inventoryapproved;
    }

    public Boolean getPopup() {
        return popup;
    }

    public void setPopup(Boolean popup) {
        this.popup = popup;
    }


    public Integer getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(Integer inventoryid) {
        this.inventoryid = inventoryid;
    }

    public Integer getItemnameid() {
        return itemnameid;
    }

    public void setItemnameid(Integer itemnameid) {
        this.itemnameid = itemnameid;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }
}
