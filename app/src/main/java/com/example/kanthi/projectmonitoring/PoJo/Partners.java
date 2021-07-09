package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 22-06-2018.
 */

public class Partners {

    @SerializedName("partnerCode")
    @DatabaseField(columnName = "partnerCode")
    private String partnerCode;

    @SerializedName("firstName")
    @DatabaseField(columnName = "firstName")
    private String firstName;

    @SerializedName("lastName")
    @DatabaseField(columnName = "lastName")
    private String lastName;

    @SerializedName("email")
    @DatabaseField(columnName = "email")
    private String email;

    @SerializedName("mobile")
    @DatabaseField(columnName = "mobile")
    private String mobile;

    @SerializedName("im")
    @DatabaseField(columnName = "im")
    private String im;

    @SerializedName("address")
    @DatabaseField(columnName = "address")
    private String address;

    @SerializedName("zip")
    @DatabaseField(columnName = "zip")
    private Integer zip;

    @SerializedName("gpsMonitoring")
    @DatabaseField(columnName = "gpsMonitoring")
    private Boolean gpsMonitoring;

    @SerializedName("interval")
    @DatabaseField(columnName = "interval")
    private Integer interval;

    @SerializedName("creditLimit")
    @DatabaseField(columnName = "creditLimit")
    private Integer creditLimit;

    @SerializedName("loyaltyNumber")
    @DatabaseField(columnName = "loyaltyNumber")
    private Integer loyaltyNumber;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("start")
    @DatabaseField(columnName = "start")
    private String start;

    @SerializedName("end")
    @DatabaseField(columnName = "end")
    private String end;

    @SerializedName("modified")
    @DatabaseField(columnName = "modified")
    private String modified;

    @SerializedName("sequence")
    @DatabaseField(columnName = "sequence")
    private String sequence;

    @SerializedName("surveycount")
    @DatabaseField(columnName = "surveycount")
    private Integer surveycount;

    @SerializedName("imagename")
    @DatabaseField(columnName = "imagename")
    private String imagename;

    @SerializedName("language")
    @DatabaseField(columnName = "language")
    private String language;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesareaId")
    @DatabaseField(columnName = "salesareaId")
    private Integer salesareaId;

    @SerializedName("distributionareaid")
    @DatabaseField(columnName = "distributionareaid")
    private Integer distributionareaid;

    @SerializedName("distributionsubareaid")
    @DatabaseField(columnName = "distributionsubareaid")
    private Integer distributionsubareaid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("groupId")
    @DatabaseField(columnName = "groupId")
    private Integer groupId;

    @SerializedName("cityId")
    @DatabaseField(columnName = "cityId")
    private Integer cityId;

    @SerializedName("stateId")
    @DatabaseField(columnName = "stateId")
    private Integer stateId;

    @SerializedName("distributionRouteId")
    @DatabaseField(columnName = "distributionRouteId")
    private Integer distributionRouteId;

    @SerializedName("salesManagerId")
    @DatabaseField(columnName = "salesManagerId")
    private Integer salesManagerId;

    @SerializedName("partnerManagerCode")
    @DatabaseField(columnName = "partnerManagerCode")
    private Integer partnerManagerCode;

    @SerializedName("modifiedBy")
    @DatabaseField(columnName = "modifiedBy")
    private String modifiedBy;

    @SerializedName("imId")
    @DatabaseField(columnName = "imId")
    private Integer imId;

    @SerializedName("retailerId")
    @DatabaseField(columnName = "retailerId")
    private Integer retailerId;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Boolean getGpsMonitoring() {
        return gpsMonitoring;
    }

    public void setGpsMonitoring(Boolean gpsMonitoring) {
        this.gpsMonitoring = gpsMonitoring;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getLoyaltyNumber() {
        return loyaltyNumber;
    }

    public void setLoyaltyNumber(Integer loyaltyNumber) {
        this.loyaltyNumber = loyaltyNumber;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Integer getSurveycount() {
        return surveycount;
    }

    public void setSurveycount(Integer surveycount) {
        this.surveycount = surveycount;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getSalesareaId() {
        return salesareaId;
    }

    public void setSalesareaId(Integer salesareaId) {
        this.salesareaId = salesareaId;
    }

    public Integer getDistributionareaid() {
        return distributionareaid;
    }

    public void setDistributionareaid(Integer distributionareaid) {
        this.distributionareaid = distributionareaid;
    }

    public Integer getDistributionsubareaid() {
        return distributionsubareaid;
    }

    public void setDistributionsubareaid(Integer distributionsubareaid) {
        this.distributionsubareaid = distributionsubareaid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistributionRouteId() {
        return distributionRouteId;
    }

    public void setDistributionRouteId(Integer distributionRouteId) {
        this.distributionRouteId = distributionRouteId;
    }

    public Integer getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(Integer salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public Integer getPartnerManagerCode() {
        return partnerManagerCode;
    }

    public void setPartnerManagerCode(Integer partnerManagerCode) {
        this.partnerManagerCode = partnerManagerCode;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getImId() {
        return imId;
    }

    public void setImId(Integer imId) {
        this.imId = imId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

}