package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class DistributionRoutes{

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("budget")
    @DatabaseField(columnName = "budget")
    private String budget;

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
