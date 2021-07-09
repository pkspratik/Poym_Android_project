package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/7/2018.
 */

public class Promotions {
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
    private String latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @SerializedName("datetime")
    @DatabaseField(columnName = "datetime")
    private String datetime;

    @SerializedName("qatime")
    @DatabaseField(columnName = "qatime")
    private String qatime;

    @SerializedName("imgreslinkid")
    @DatabaseField(columnName = "imgreslinkid")
    private Integer imgreslinkid;

    @SerializedName("approvedflag")
    @DatabaseField(columnName = "approvedflag")
    private String approvedflag;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("areaid")
    @DatabaseField(columnName = "areaid")
    private Integer areaid;

    @SerializedName("distareaid")
    @DatabaseField(columnName = "distareaid")
    private Integer distareaid;

    @SerializedName("distsubareaid")
    @DatabaseField(columnName = "distsubareaid")
    private Integer distsubareaid;

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
    private String remark;

    @SerializedName("remarkdesc")
    @DatabaseField(columnName = "remarkdesc")
    private String remarkdesc;

    @SerializedName("qaflag")
    @DatabaseField(columnName = "qaflag")
    private Boolean qaflag;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private Long routeassignmentid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Long routeassignmentsummaryid;

    @SerializedName("address")
    @DatabaseField(columnName = "address")
    private String address;

    @SerializedName("comment")
    @DatabaseField(columnName = "comment")
    private String comment;

    @SerializedName("qaimage")
    @DatabaseField(columnName = "qaimage")
    private String qaimage;

    @SerializedName("height")
    @DatabaseField(columnName = "height")
    private Float height;

    @SerializedName("distance")
    @DatabaseField(columnName = "distance")
    private Float distance;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    @DatabaseField(columnName = "previous_flag")
    @SerializedName("previous_flag")
    private boolean previous_flag;

    @SerializedName("previouslocation")
    @DatabaseField(columnName = "previouslocation")
    private Long previouslocation;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Long id;

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    public String getQatime() {
        return qatime;
    }

    public void setQatime(String qatime) {
        this.qatime = qatime;
    }

    public Integer getImgreslinkid() {
        return imgreslinkid;
    }

    public void setImgreslinkid(Integer imgreslinkid) {
        this.imgreslinkid = imgreslinkid;
    }

    public String getApprovedflag() {
        return approvedflag;
    }

    public void setApprovedflag(String approvedflag) {
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


    public Integer getDistsubareaid() {
        return distsubareaid;
    }

    public void setDistsubareaid(Integer distsubareaid) {
        this.distsubareaid = distsubareaid;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkdesc() {
        return remarkdesc;
    }

    public void setRemarkdesc(String remarkdesc) {
        this.remarkdesc = remarkdesc;
    }


    public Boolean getQaflag() {
        return qaflag;
    }

    public void setQaflag(Boolean qaflag) {
        this.qaflag = qaflag;
    }

    public Long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(Long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQaimage() {
        return qaimage;
    }

    public void setQaimage(String qaimage) {
        this.qaimage = qaimage;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
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

    public boolean isPrevious_flag() {
        return previous_flag;
    }

    public void setPrevious_flag(boolean previous_flag) {
        this.previous_flag = previous_flag;
    }

    public Long getPreviouslocation() {
        return previouslocation;
    }

    public void setPreviouslocation(Long previouslocation) {
        this.previouslocation = previouslocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}