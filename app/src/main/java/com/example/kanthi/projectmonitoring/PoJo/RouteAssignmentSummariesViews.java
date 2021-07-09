package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/27/2018.
 */

public class RouteAssignmentSummariesViews {

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

    @SerializedName("percentage")
    @DatabaseField(columnName = "percentage")
    private Float percentage;

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

    @SerializedName("fromdate")
    @DatabaseField(columnName = "fromdate")
    private String fromdate;

    @SerializedName("todate")
    @DatabaseField(columnName = "todate")
    private String todate;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("distributionareaid")
    @DatabaseField(columnName = "distributionareaid")
    private Integer distributionareaid;

    @SerializedName("salesareaid")
    @DatabaseField(columnName = "salesareaid")
    private Integer salesareaid;

    @SerializedName("salesmanagerid")
    @DatabaseField(columnName = "salesmanagerid")
    private Integer salesmanagerid;

    @SerializedName("partnerid")
    @DatabaseField(columnName = "partnerid")
    private Integer partnerid;

    @SerializedName("phaseid")
    @DatabaseField(columnName = "phaseid")
    private Integer phaseid;

    @SerializedName("processid")
    @DatabaseField(columnName = "processid")
    private Integer processid;

    @SerializedName("subprocessid")
    @DatabaseField(columnName = "subprocessid")
    private Integer subprocessid;

    @SerializedName("consultUserId")
    @DatabaseField(columnName = "consultUserId")
    private Integer consultUserId;

    @SerializedName("informedUserId")
    @DatabaseField(columnName = "informedUserId")
    private Integer informedUserId;

    @SerializedName("supportUserId")
    @DatabaseField(columnName = "supportUserId")
    private Integer supportUserId;

    @SerializedName("dependentTaskId")
    @DatabaseField(columnName = "dependentTaskId")
    private String dependentTaskId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    @SerializedName("from_month")
    @DatabaseField(columnName = "from_month")
    private String from_month;

    @SerializedName("from_date")
    @DatabaseField(columnName = "from_date")
    private String from_date;

    @SerializedName("from_day")
    @DatabaseField(columnName = "from_day")
    private String from_day;

    @SerializedName("from_year")
    @DatabaseField(columnName = "from_year")
    private Integer from_year;

    @SerializedName("from_assigndate")
    @DatabaseField(columnName = "from_assigndate")
    private String from_assigndate;

    @SerializedName("to_month")
    @DatabaseField(columnName = "to_month")
    private String to_month;

    @SerializedName("to_date")
    @DatabaseField(columnName = "to_date")
    private String to_date;

    @SerializedName("to_day")
    @DatabaseField(columnName = "to_day")
    private String to_day;

    @SerializedName("to_year")
    @DatabaseField(columnName = "to_year")
    private Integer to_year;

    @SerializedName("to_assigndate")
    @DatabaseField(columnName = "to_assigndate")
    private String to_assigndate;

    @SerializedName("evFlag")
    @DatabaseField(columnName = "evFlag")
    private String evFlag;

    @SerializedName("hcflag")
    @DatabaseField(columnName = "hcflag")
    private String hcflag;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @SerializedName("startFlag")
    @DatabaseField(columnName = "startFlag")
    private Boolean startFlag;

    @SerializedName("completedFlag")
    @DatabaseField(columnName = "completedFlag")
    private Boolean completedFlag;

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

    public Float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(Float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public Float getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(Float totalactual) {
        this.totalactual = totalactual;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
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

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public Integer getDistributionareaid() {
        return distributionareaid;
    }

    public void setDistributionareaid(Integer distributionareaid) {
        this.distributionareaid = distributionareaid;
    }

    public Integer getSalesareaid() {
        return salesareaid;
    }

    public void setSalesareaid(Integer salesareaid) {
        this.salesareaid = salesareaid;
    }

    public Integer getSalesmanagerid() {
        return salesmanagerid;
    }

    public void setSalesmanagerid(Integer salesmanagerid) {
        this.salesmanagerid = salesmanagerid;
    }

    public Integer getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(Integer partnerid) {
        this.partnerid = partnerid;
    }

    public Integer getPhaseid() {
        return phaseid;
    }

    public void setPhaseid(Integer phaseid) {
        this.phaseid = phaseid;
    }

    public Integer getProcessid() {
        return processid;
    }

    public void setProcessid(Integer processid) {
        this.processid = processid;
    }

    public Integer getSubprocessid() {
        return subprocessid;
    }

    public void setSubprocessid(Integer subprocessid) {
        this.subprocessid = subprocessid;
    }

    public Integer getConsultUserId() {
        return consultUserId;
    }

    public void setConsultUserId(Integer consultUserId) {
        this.consultUserId = consultUserId;
    }

    public Integer getInformedUserId() {
        return informedUserId;
    }

    public void setInformedUserId(Integer informedUserId) {
        this.informedUserId = informedUserId;
    }

    public Integer getSupportUserId() {
        return supportUserId;
    }

    public void setSupportUserId(Integer supportUserId) {
        this.supportUserId = supportUserId;
    }

    public String getDependentTaskId() {
        return dependentTaskId;
    }

    public void setDependentTaskId(String dependentTaskId) {
        this.dependentTaskId = dependentTaskId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom_Month() {
        return from_month;
    }

    public void setFrom_Month(String from_month) {
        this.from_month = from_month;
    }

    public String getFrom_Date() {
        return from_date;
    }

    public void setFrom_Date(String from_date) {
        this.from_date = from_date;
    }

    public String getFrom_Day() {
        return from_day;
    }

    public void setFrom_Day(String from_day) {
        this.from_day = from_day;
    }

    public Integer getFrom_Year() {
        return from_year;
    }

    public void setFrom_Year(Integer from_year) {
        this.from_year = from_year;
    }

    public String getFrom_Assigndate() {
        return from_assigndate;
    }

    public void setFrom_Assigndate(String from_assigndate) {
        this.from_assigndate = from_assigndate;
    }

    public String getTo_Month() {
        return to_month;
    }

    public void setTo_Month(String to_month) {
        this.to_month = to_month;
    }

    public String getTo_Date() {
        return to_date;
    }

    public void setTo_Date(String to_date) {
        this.to_date = to_date;
    }

    public String getTo_Day() {
        return to_day;
    }

    public void setTo_Day(String to_day) {
        this.to_day = to_day;
    }

    public Integer getTo_Year() {
        return to_year;
    }

    public void setTo_Year(Integer to_year) {
        this.to_year = to_year;
    }

    public String getTo_Assigndate() {
        return to_assigndate;
    }

    public void setTo_Assigndate(String to_assigndate) {
        this.to_assigndate = to_assigndate;
    }

    public String getEvFlag() {
        return evFlag;
    }

    public void setEvFlag(String evFlag) {
        this.evFlag = evFlag;
    }

    public String getHcflag() {
        return hcflag;
    }

    public void setHcflag(String hcflag) {
        this.hcflag = hcflag;
    }

    public boolean isUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(boolean updateflag) {
        this.updateflag = updateflag;
    }

    public Boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public Boolean getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(Boolean completedFlag) {
        this.completedFlag = completedFlag;
    }
}
