package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kanthi on 5/2/2018.
 */

public class Documents {

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("salesareaId")
    @DatabaseField(columnName = "salesareaId")
    private Integer salesareaId;

    @SerializedName("distributionareaId")
    @DatabaseField(columnName = "distributionareaId")
    private Integer distributionareaId;

    @SerializedName("distributionsubareaId")
    @DatabaseField(columnName = "distributionsubareaId")
    private Integer distributionsubareaId;

    @SerializedName("tasktypeId")
    @DatabaseField(columnName = "tasktypeId")
    private Integer tasktypeId;

    @SerializedName("documenttype")
    @DatabaseField(columnName = "documenttype")
    private String documenttype;

    @SerializedName("documentname")
    @DatabaseField(columnName = "documentname")
    private String documentname;

    @SerializedName("filename")
    @DatabaseField(columnName = "filename")
    private String filename;

    @SerializedName("deleteFlag")
    @DatabaseField(columnName = "deleteFlag")
    private Boolean deleteFlag;

    @SerializedName("completedFlag")
    @DatabaseField(columnName = "completedFlag")
    private Boolean completedFlag;

    @SerializedName("lastModifiedDate")
    @DatabaseField(columnName = "lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("taskname")
    @DatabaseField(columnName = "taskname")
    private String taskname;

    @SerializedName("docname")
    @DatabaseField(columnName = "docname")
    private String docname;

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

    public Integer getDistributionareaId() {
        return distributionareaId;
    }

    public void setDistributionareaId(Integer distributionareaId) {
        this.distributionareaId = distributionareaId;
    }

    public Integer getDistributionsubareaId() {
        return distributionsubareaId;
    }

    public void setDistributionsubareaId(Integer distributionsubareaId) {
        this.distributionsubareaId = distributionsubareaId;
    }

    public Integer getTasktypeId() {
        return tasktypeId;
    }

    public void setTasktypeId(Integer tasktypeId) {
        this.tasktypeId = tasktypeId;
    }

    public String getDocumenttype() {
        return documenttype;
    }

    public void setDocumenttype(String documenttype) {
        this.documenttype = documenttype;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(Boolean completedFlag) {
        this.completedFlag = completedFlag;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }


}