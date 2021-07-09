package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 5/10/2018.
 */

public class ProjectRiskViews {

    @SerializedName("risktypeid")
    @DatabaseField(columnName = "risktypeid")
    private Integer risktypeid;

    @SerializedName("risktype")
    @DatabaseField(columnName = "risktype")
    private String risktype;

    @SerializedName("riskprobabilityid")
    @DatabaseField(columnName = "riskprobabilityid")
    private Integer riskprobabilityid;

    @SerializedName("probability")
    @DatabaseField(columnName = "probability")
    private String probability;

    @SerializedName("impactid")
    @DatabaseField(columnName = "impactid")
    private Integer impactid;

    @SerializedName("impact")
    @DatabaseField(columnName = "impact")
    private String impact;

    @SerializedName("responseid")
    @DatabaseField(columnName = "responseid")
    private Integer responseid;

    @SerializedName("response")
    @DatabaseField(columnName = "response")
    private String response;

    @SerializedName("status")
    @DatabaseField(columnName = "status")
    private String status;

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
    private Long id;

    public Integer getRisktypeid() {
        return risktypeid;
    }

    public void setRisktypeid(Integer risktypeid) {
        this.risktypeid = risktypeid;
    }

    public String getRisktype() {
        return risktype;
    }

    public void setRisktype(String risktype) {
        this.risktype = risktype;
    }

    public Integer getRiskprobabilityid() {
        return riskprobabilityid;
    }

    public void setRiskprobabilityid(Integer riskprobabilityid) {
        this.riskprobabilityid = riskprobabilityid;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public Integer getImpactid() {
        return impactid;
    }

    public void setImpactid(Integer impactid) {
        this.impactid = impactid;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Integer getResponseid() {
        return responseid;
    }

    public void setResponseid(Integer responseid) {
        this.responseid = responseid;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}