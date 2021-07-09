package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 4/16/2018.
 */

public class SurveyPromotions {

    @SerializedName("retailername")
    @DatabaseField(columnName = "retailername")
    private String retailername;

    @SerializedName("retailerid")
    @DatabaseField(columnName = "retailerid")
    private Integer retailerid;

    @SerializedName("retaileraddress")
    @DatabaseField(columnName = "retaileraddress")
    private String retaileraddress;

    @SerializedName("retailerimage")
    @DatabaseField(columnName = "retailerimage")
    private String retailerimage;

    @SerializedName("displayunit")
    @DatabaseField(columnName = "displayunit")
    private Integer displayunit;

    @SerializedName("flexdisplay")
    @DatabaseField(columnName = "flexdisplay")
    private Integer flexdisplay;

    @SerializedName("posters")
    @DatabaseField(columnName = "posters")
    private Integer posters;

    @SerializedName("lcddisplay")
    @DatabaseField(columnName = "lcddisplay")
    private Integer lcddisplay;

    @SerializedName("frontdisplay")
    @DatabaseField(columnName = "frontdisplay")
    private Integer frontdisplay;

    @SerializedName("tshirts")
    @DatabaseField(columnName = "tshirts")
    private Integer tshirts;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private Double latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private Double longitude;

    @SerializedName("datetime")
    @DatabaseField(columnName = "datetime")
    private String datetime;

    @SerializedName("imgreslinkid")
    @DatabaseField(columnName = "imgreslinkid")
    private Integer imgreslinkid;

    @SerializedName("approvedflag")
    @DatabaseField(columnName = "approvedflag")
    private Boolean approvedflag;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("distareaid")
    @DatabaseField(columnName = "distareaid")
    private Integer distareaid;

    @SerializedName("salesmgrid")
    @DatabaseField(columnName = "salesmgrid")
    private Integer salesmgrid;

    @SerializedName("employeeid")
    @DatabaseField(columnName = "employeeid")
    private Integer employeeid;

    @SerializedName("routeid")
    @DatabaseField(columnName = "routeid")
    private Integer routeid;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("rejectedflag")
    @DatabaseField(columnName = "rejectedflag")
    private Boolean rejectedflag;

    @SerializedName("remark")
    @DatabaseField(columnName = "remark")
    private Integer remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("locationname")
    @DatabaseField(columnName = "locationname")
    private String locationname;

    @SerializedName("roadwidth")
    @DatabaseField(columnName = "roadwidth")
    private String roadwidth;

    @SerializedName("rowauthority")
    @DatabaseField(columnName = "rowauthority")
    private String rowauthority;

    @SerializedName("elements")
    @DatabaseField(columnName = "elements")
    private String elements;

    @SerializedName("modifiedflag")
    @DatabaseField(columnName = "modifiedflag")
    private Boolean modifiedflag;

    @SerializedName("actuallatitude")
    @DatabaseField(columnName = "actuallatitude")
    private String actuallatitude;

    @SerializedName("actuallongitude")
    @DatabaseField(columnName = "actuallongitude")
    private String actuallongitude;

    @SerializedName("qaflag")
    @DatabaseField(columnName = "qaflag")
    private Boolean qaflag;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private Long routeassignmentid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Long routeassignmentsummaryid;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    @SerializedName("names")
    @DatabaseField(columnName = "names")
    private String names;

    public String getRetailername() {
        return retailername;
    }

    public void setRetailername(String retailername) {
        this.retailername = retailername;
    }

    public Integer getRetailerid() {
        return retailerid;
    }

    public void setRetailerid(Integer retailerid) {
        this.retailerid = retailerid;
    }

    public String getRetaileraddress() {
        return retaileraddress;
    }

    public void setRetaileraddress(String retaileraddress) {
        this.retaileraddress = retaileraddress;
    }

    public String getRetailerimage() {
        return retailerimage;
    }

    public void setRetailerimage(String retailerimage) {
        this.retailerimage = retailerimage;
    }

    public Integer getDisplayunit() {
        return displayunit;
    }

    public void setDisplayunit(Integer displayunit) {
        this.displayunit = displayunit;
    }

    public Integer getFlexdisplay() {
        return flexdisplay;
    }

    public void setFlexdisplay(Integer flexdisplay) {
        this.flexdisplay = flexdisplay;
    }

    public Integer getPosters() {
        return posters;
    }

    public void setPosters(Integer posters) {
        this.posters = posters;
    }

    public Integer getLcddisplay() {
        return lcddisplay;
    }

    public void setLcddisplay(Integer lcddisplay) {
        this.lcddisplay = lcddisplay;
    }

    public Integer getFrontdisplay() {
        return frontdisplay;
    }

    public void setFrontdisplay(Integer frontdisplay) {
        this.frontdisplay = frontdisplay;
    }

    public Integer getTshirts() {
        return tshirts;
    }

    public void setTshirts(Integer tshirts) {
        this.tshirts = tshirts;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getImgreslinkid() {
        return imgreslinkid;
    }

    public void setImgreslinkid(Integer imgreslinkid) {
        this.imgreslinkid = imgreslinkid;
    }

    public Boolean getApprovedflag() {
        return approvedflag;
    }

    public void setApprovedflag(Boolean approvedflag) {
        this.approvedflag = approvedflag;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getDistareaid() {
        return distareaid;
    }

    public void setDistareaid(Integer distareaid) {
        this.distareaid = distareaid;
    }

    public Integer getSalesmgrid() {
        return salesmgrid;
    }

    public void setSalesmgrid(Integer salesmgrid) {
        this.salesmgrid = salesmgrid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getRouteid() {
        return routeid;
    }

    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }

    public Boolean getRejectedflag() {
        return rejectedflag;
    }

    public void setRejectedflag(Boolean rejectedflag) {
        this.rejectedflag = rejectedflag;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    public String getRemarkdesc() {
        return remarkdesc;
    }

    public void setRemarkdesc(String remarkdesc) {
        this.remarkdesc = remarkdesc;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getRoadwidth() {
        return roadwidth;
    }

    public void setRoadwidth(String roadwidth) {
        this.roadwidth = roadwidth;
    }

    public String getRowauthority() {
        return rowauthority;
    }

    public void setRowauthority(String rowauthority) {
        this.rowauthority = rowauthority;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    public Boolean getModifiedflag() {
        return modifiedflag;
    }

    public void setModifiedflag(Boolean modifiedflag) {
        this.modifiedflag = modifiedflag;
    }

    public String getActuallatitude() {
        return actuallatitude;
    }

    public void setActuallatitude(String actuallatitude) {
        this.actuallatitude = actuallatitude;
    }

    public String getActuallongitude() {
        return actuallongitude;
    }

    public void setActuallongitude(String actuallongitude) {
        this.actuallongitude = actuallongitude;
    }

    public Boolean getQaflag() {
        return qaflag;
    }

    public void setQaflag(Boolean qaflag) {
        this.qaflag = qaflag;
    }

    public long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

}
