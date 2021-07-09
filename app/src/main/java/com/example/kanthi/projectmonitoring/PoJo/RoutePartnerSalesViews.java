package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 2/22/2018.
 */

public class RoutePartnerSalesViews {

    @SerializedName("routeid")
    @DatabaseField(columnName = "routeid")
    private Integer routeid;

    @SerializedName("salesCode")
    @DatabaseField(columnName = "salesCode")
    private String salesCode;

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("Month")
    @DatabaseField(columnName = "month")
    private String month;

    @SerializedName("Date")
    @DatabaseField(columnName = "date")
    private String date;

    @SerializedName("Day")
    @DatabaseField(columnName = "day")
    private String day;

    @SerializedName("Year")
    @DatabaseField(columnName = "year")
    private Integer year;

    @SerializedName("amonth")
    @DatabaseField(columnName = "amonth")
    private String amonth;

    @SerializedName("adate")
    @DatabaseField(columnName = "adate")
    private String adate;

    @SerializedName("aday")
    @DatabaseField(columnName = "aday")
    private String aday;

    @SerializedName("ayear")
    @DatabaseField(columnName = "ayear")
    private Integer ayear;

    @SerializedName("routename")
    @DatabaseField(columnName = "routename")
    private String routename;

    @SerializedName("tourtype")
    @DatabaseField(columnName = "tourtype")
    private String tourtype;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("assigndate")
    @DatabaseField(columnName = "assigndate")
    private String assigndate;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

    @SerializedName("worktypeid")
    @DatabaseField(columnName = "worktypeid")
    private Integer worktypeid;

    @SerializedName("duration")
    @DatabaseField(columnName = "duration")
    private Float duration;

    @SerializedName("startseq")
    @DatabaseField(columnName = "startseq")
    private Integer startseq;

    @SerializedName("endseq")
    @DatabaseField(columnName = "endseq")
    private Integer endseq;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private Float totaltarget;

    @SerializedName("totalactual")
    @DatabaseField(columnName = "totalactual")
    private Float totalactual;

    @SerializedName("submitflag")
    @DatabaseField(columnName = "submitflag")
    private Boolean submitflag;

    @SerializedName("unitmeasurementname")
    @DatabaseField(columnName = "unitmeasurementname")
    private String unitmeasurementname;

    @SerializedName("unitmeasurementid")
    @DatabaseField(columnName = "unitmeasurementid")
    private Integer unitmeasurementid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private Integer routeassignmentsummaryid;

    @SerializedName("summarytotalactual")
    @DatabaseField(columnName = "summarytotalactual")
    private String summarytotalactual;

    @SerializedName("resourceflag")
    @DatabaseField(columnName = "resourceflag")
    private Boolean resourceflag;

    @SerializedName("projectstatusid")
    @DatabaseField(columnName = "projectstatusid")
    private Integer projectstatusid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getRouteid() {
        return routeid;
    }

    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAmonth() {
        return amonth;
    }

    public void setAmonth(String amonth) {
        this.amonth = amonth;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getAday() {
        return aday;
    }

    public void setAday(String aday) {
        this.aday = aday;
    }

    public Integer getAyear() {
        return ayear;
    }

    public void setAyear(Integer ayear) {
        this.ayear = ayear;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getTourtype() {
        return tourtype;
    }

    public void setTourtype(String tourtype) {
        this.tourtype = tourtype;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }

    public String getAssigndate() {
        return assigndate;
    }

    public void setAssigndate(String assigndate) {
        this.assigndate = assigndate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Integer getStartseq() {
        return startseq;
    }

    public void setStartseq(Integer startseq) {
        this.startseq = startseq;
    }

    public Integer getEndseq() {
        return endseq;
    }

    public void setEndseq(Integer endseq) {
        this.endseq = endseq;
    }

    public float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public Float getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(Float totalactual) {
        this.totalactual = totalactual;
    }

    public Boolean getSubmitflag() {
        return submitflag;
    }

    public void setSubmitflag(Boolean submitflag) {
        this.submitflag = submitflag;
    }

    public String getUnitmeasurementname() {
        return unitmeasurementname;
    }

    public void setUnitmeasurementname(String unitmeasurementname) {
        this.unitmeasurementname = unitmeasurementname;
    }

    public Integer getUnitmeasurementid() {
        return unitmeasurementid;
    }

    public void setUnitmeasurementid(Integer unitmeasurementid) {
        this.unitmeasurementid = unitmeasurementid;
    }

    public Integer getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(Integer routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public String getSummarytotalactual() {
        return summarytotalactual;
    }

    public void setSummarytotalactual(String summarytotalactual) {
        this.summarytotalactual = summarytotalactual;
    }

    public Boolean getResourceflag() {
        return resourceflag;
    }

    public void setResourceflag(Boolean resourceflag) {
        this.resourceflag = resourceflag;
    }

    public Integer getProjectstatusid() {
        return projectstatusid;
    }

    public void setProjectstatusid(Integer projectstatusid) {
        this.projectstatusid = projectstatusid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}