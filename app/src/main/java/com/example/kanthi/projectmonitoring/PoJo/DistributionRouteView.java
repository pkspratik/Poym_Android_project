package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class DistributionRouteView {
    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("budget")
    @DatabaseField(columnName = "budget")
    private String budget;

    @SerializedName("tid")
    @DatabaseField(columnName = "tid")
    private String tid;

    @SerializedName("isSurvey")
    @DatabaseField(columnName = "isSurvey")
    private Boolean isSurvey;

    @SerializedName("isChecklist")
    @DatabaseField(columnName = "isChecklist")
    private Boolean isChecklist;

    @SerializedName("tourtypename")
    @DatabaseField(columnName = "tourtypename")
    private String tourtypename;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Boolean getIsSurvey() {
        return isSurvey;
    }

    public void setIsSurvey(Boolean isSurvey) {
        this.isSurvey = isSurvey;
    }

    public Boolean getIsChecklist() {
        return isChecklist;
    }

    public void setIsChecklist(Boolean isChecklist) {
        this.isChecklist = isChecklist;
    }

    public String getTourtypename() {
        return tourtypename;
    }

    public void setTourtypename(String tourtypename) {
        this.tourtypename = tourtypename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
