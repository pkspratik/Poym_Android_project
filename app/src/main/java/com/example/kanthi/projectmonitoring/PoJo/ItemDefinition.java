package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 04/20/2018.
 */

public class ItemDefinition {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("itemcode")
    @DatabaseField(columnName = "itemcode")
    private String itemcode;

    @SerializedName("unitprice")
    @DatabaseField(columnName = "unitprice")
    private String unitprice;

    @SerializedName("itemdescription")
    @DatabaseField(columnName = "itemdescription")
    private String itemdescription;

    @SerializedName("deleteflag")
    @DatabaseField(columnName = "deleteflag")
    private Boolean deleteflag;

    @SerializedName("weight")
    @DatabaseField(columnName = "weight")
    private Integer weight;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("quantity")
    @DatabaseField(columnName = "quantity")
    private Integer quantity;

    @SerializedName("itemflavour")
    @DatabaseField(columnName = "itemflavour")
    private String itemflavour;

    @SerializedName("itemdefinitionid")
    @DatabaseField(columnName = "itemdefinitionid")
    private Integer itemdefinitionid;

    @SerializedName("itemtype")
    @DatabaseField(columnName = "itemtype")
    private String itemtype;

    @SerializedName("itemsubtype")
    @DatabaseField(columnName = "itemsubtype")
    private String itemsubtype;

    @SerializedName("itemflavourId")
    @DatabaseField(columnName = "itemflavourId")
    private Integer itemflavourId;

    @SerializedName("itemsubtypeId")
    @DatabaseField(columnName = "itemsubtypeId")
    private Integer itemsubtypeId;

    @SerializedName("itemtypeId")
    @DatabaseField(columnName = "itemtypeId")
    private Integer itemtypeId;

    @SerializedName("itemcategoryId")
    @DatabaseField(columnName = "itemcategoryId")
    private Integer itemcategoryId;

    @SerializedName("unitmeasurementId")
    @DatabaseField(columnName = "unitmeasurementId")
    private Integer unitmeasurementId;

    @DatabaseField(columnName = "casepacket")
    private String casepacket;

    @DatabaseField(columnName = "innerpack")
    private String innerpack;

    @DatabaseField(columnName = "selecetedquantity")
    private String selecetedquantity;

    @DatabaseField(columnName = "totalamount")
    private String totalamount;

    @DatabaseField(columnName = "mrp")
    private Integer mrp;

    @DatabaseField(columnName = "selectedpackettype")
    private String selectedpackettype;

    public String getSelectedpackettype() {
        return selectedpackettype;
    }

    public void setSelectedpackettype(String selectedpackettype) {
        this.selectedpackettype = selectedpackettype;
    }

    public String getSelectedpacket() {
        return selectedpacket;
    }

    public void setSelectedpacket(String selectedpacket) {
        this.selectedpacket = selectedpacket;
    }

    private String selectedpacket;

    public Integer getChildpriority() {
        return Childpriority;
    }

    public void setChildpriority(Integer childpriority) {
        Childpriority = childpriority;
    }

    @SerializedName("Childpriority")
    private Integer Childpriority;

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getSelecetedquantity() {
        return selecetedquantity;
    }

    public void setSelecetedquantity(String selecetedquantity) {
        this.selecetedquantity = selecetedquantity;
    }

    public String getCasepacket() {
        return casepacket;
    }

    public void setCasepacket(String casepacket) {
        this.casepacket = casepacket;
    }

    public String getInnerpack() {
        return innerpack;
    }

    public void setInnerpack(String innerpack) {
        this.innerpack = innerpack;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemsubtype() {
        return itemsubtype;
    }

    public void setItemsubtype(String itemsubtype) {
        this.itemsubtype = itemsubtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getItemFlavour() {
        return itemflavour;
    }

    public void setItemFlavour(String itemflavour) {
        this.itemflavour = itemflavour;
    }

    public Boolean getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Boolean deleteflag) {
        this.deleteflag = deleteflag;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemflavourId() {
        return itemflavourId;
    }

    public void setItemflavourId(Integer itemflavourId) {
        this.itemflavourId = itemflavourId;
    }

    public Integer getItemsubtypeId() {
        return itemsubtypeId;
    }

    public void setItemsubtypeId(Integer itemsubtypeId) {
        this.itemsubtypeId = itemsubtypeId;
    }

    public Integer getItemtypeId() {
        return itemtypeId;
    }

    public void setItemtypeId(Integer itemtypeId) {
        this.itemtypeId = itemtypeId;
    }

    public Integer getItemcategoryId() {
        return itemcategoryId;
    }

    public void setItemcategoryId(Integer itemcategoryId) {
        this.itemcategoryId = itemcategoryId;
    }

    public Integer getUnitmeasurementId() {
        return unitmeasurementId;
    }

    public Integer getItemdefinitionid() {
        return itemdefinitionid;
    }

    public void setItemdefinitionid(Integer itemdefinitionid) {
        this.itemdefinitionid = itemdefinitionid;
    }


    public void setUnitmeasurementId(Integer unitmeasurementId) {
        this.unitmeasurementId = unitmeasurementId;
    }
}
