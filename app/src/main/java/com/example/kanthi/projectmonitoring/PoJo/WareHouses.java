package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kanthi on 5/21/2018.
 */

public class WareHouses {

    @SerializedName("flag")
    @DatabaseField(columnName = "flag")
    private Boolean flag;

    @SerializedName("warehousecode")
    @DatabaseField(columnName = "warehousecode")
    private String warehousecode;

    @SerializedName("warehousename")
    @DatabaseField(columnName = "warehousename")
    private String warehousename;

    @SerializedName("salescode")
    @DatabaseField(columnName = "salescode")
    private String salescode;

    @SerializedName("address")
    @DatabaseField(columnName = "address")
    private String address;

    @SerializedName("city")
    @DatabaseField(columnName = "city")
    private String city;

    @SerializedName("state")
    @DatabaseField(columnName = "state")
    private String state;

    @SerializedName("zip")
    @DatabaseField(columnName = "zip")
    private String zip;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("countryId")
    @DatabaseField(columnName = "countryId")
    private Integer countryId;

    @SerializedName("stateId")
    @DatabaseField(columnName = "stateId")
    private Integer stateId;

    @SerializedName("cityId")
    @DatabaseField(columnName = "cityId")
    private Integer cityId;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesAreaId")
    @DatabaseField(columnName = "salesAreaId")
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @DatabaseField(columnName = "distributionAreaId")
    private Integer distributionAreaId;

    @SerializedName("distributionSubareaId")
    @DatabaseField(columnName = "distributionSubareaId")
    private Integer distributionSubareaId;

    @SerializedName("partnerId")
    @DatabaseField(columnName = "partnerId")
    private Integer partnerId;

    @SerializedName("warehousetypeId")
    @DatabaseField(columnName = "warehousetypeId")
    private Integer warehousetypeId;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getSalescode() {
        return salescode;
    }

    public void setSalescode(String salescode) {
        this.salescode = salescode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public Integer getDistributionAreaId() {
        return distributionAreaId;
    }

    public void setDistributionAreaId(Integer distributionAreaId) {
        this.distributionAreaId = distributionAreaId;
    }

    public Integer getDistributionSubareaId() {
        return distributionSubareaId;
    }

    public void setDistributionSubareaId(Integer distributionSubareaId) {
        this.distributionSubareaId = distributionSubareaId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getWarehousetypeId() {
        return warehousetypeId;
    }

    public void setWarehousetypeId(Integer warehousetypeId) {
        this.warehousetypeId = warehousetypeId;
    }

}
