package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 5/11/2018.
 */

public class ChangeReqViews {

    @SerializedName("changereqno")
    @DatabaseField(columnName = "changereqno")
    private String changereqno;

    @SerializedName("priorityid")
    @DatabaseField(columnName = "priorityid")
    private Integer priorityid;

    @SerializedName("priority")
    @DatabaseField(columnName = "priority")
    private String priority;

    @SerializedName("changereqcategoryid")
    @DatabaseField(columnName = "changereqcategoryid")
    private Integer changereqcategoryid;

    @SerializedName("category")
    @DatabaseField(columnName = "category")
    private String category;

    @SerializedName("description")
    @DatabaseField(columnName = "description")
    private String description;

    @SerializedName("zoneid")
    @DatabaseField(columnName = "zoneid")
    private Integer zoneid;

    @SerializedName("salesareaid")
    @DatabaseField(columnName = "salesareaid")
    private Integer salesareaid;

    @SerializedName("distributionareaid")
    @DatabaseField(columnName = "distributionareaid")
    private Integer distributionareaid;

    @SerializedName("distributionsubareaid")
    @DatabaseField(columnName = "distributionsubareaid")
    private Integer distributionsubareaid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    public String getChangereqno() {
        return changereqno;
    }

    public void setChangereqno(String changereqno) {
        this.changereqno = changereqno;
    }

    public Integer getPriorityid() {
        return priorityid;
    }

    public void setPriorityid(Integer priorityid) {
        this.priorityid = priorityid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getChangereqcategoryid() {
        return changereqcategoryid;
    }

    public void setChangereqcategoryid(Integer changereqcategoryid) {
        this.changereqcategoryid = changereqcategoryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Integer getDistributionsubareaid() {
        return distributionsubareaid;
    }

    public void setDistributionsubareaid(Integer distributionsubareaid) {
        this.distributionsubareaid = distributionsubareaid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}