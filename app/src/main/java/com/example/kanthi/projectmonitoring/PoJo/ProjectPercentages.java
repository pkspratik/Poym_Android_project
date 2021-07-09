package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class ProjectPercentages {

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("salesareaid")
    @DatabaseField(columnName = "salesareaid")
    private Integer salesareaid;

    @SerializedName("distributionareaid")
    @DatabaseField(columnName = "distributionareaid")
    private Integer distributionareaid;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("tourtype")
    @DatabaseField(columnName = "tourtype")
    private String tourtype;

    @SerializedName("worktypeid")
    @DatabaseField(columnName = "worktypeid")
    private Integer worktypeid;

    @SerializedName("totaltarget")
    @DatabaseField(columnName = "totaltarget")
    private Float totaltarget;

    @SerializedName("totalactual")
    @DatabaseField(columnName = "totalactual")
    private Float totalactual;

    @SerializedName("percentage")
    @DatabaseField(columnName = "percentage")
    private String percentage;

    @SerializedName("totpercentage")
    @DatabaseField(columnName = "totpercentage")
    private Float totpercentage;

    @SerializedName("unitmeasurementname")
    @DatabaseField(columnName = "unitmeasurementname")
    private String unitmeasurementname;

    @SerializedName("unitmeasurementid")
    @DatabaseField(columnName = "unitmeasurementid")
    private Integer unitmeasurementid;

    @SerializedName("summarytotalactual")
    @DatabaseField(columnName = "summarytotalactual")
    private Float summarytotalactual;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public Integer getSalesareaid() {
        return salesareaid;
    }

    public void setSalesareaid(Integer salesareaid) {
        this.salesareaid = salesareaid;
    }

    public Integer getDistributionareaid() {
        return distributionareaid;
    }

    public void setDistributionareaid(Integer distributionareaid) {
        this.distributionareaid = distributionareaid;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }

    public String getTourtype() {
        return tourtype;
    }

    public void setTourtype(String tourtype) {
        this.tourtype = tourtype;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
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

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Float getTotpercentage() {
        return totpercentage;
    }

    public void setTotpercentage(Float totpercentage) {
        this.totpercentage = totpercentage;
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

    public Float getSummarytotalactual() {
        return summarytotalactual;
    }

    public void setSummarytotalactual(Float summarytotalactual) {
        this.summarytotalactual = summarytotalactual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}