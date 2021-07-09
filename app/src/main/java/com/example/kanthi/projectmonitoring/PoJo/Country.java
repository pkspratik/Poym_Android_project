package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 21-feb-18.
 */

public class Country {

    @DatabaseField(columnName = "name")
    @SerializedName("name")
    private String name;

    @DatabaseField(columnName = "createdBy")
    @SerializedName("createdBy")
    private Integer createdBy;

    @DatabaseField(columnName = "createdDate")
    @SerializedName("createdDate")
    private String createdDate;

    @DatabaseField(columnName = "createdByRole")
    @SerializedName("createdByRole")
    private Integer createdByRole;

    @DatabaseField(columnName = "lastModifiedBy")
    @SerializedName("lastModifiedBy")
    private Integer lastModifiedBy;

    @DatabaseField(columnName = "lastModifiedDate")
    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;

    @DatabaseField(columnName = "lastModifiedByRole")
    @SerializedName("lastModifiedByRole")
    private Integer lastModifiedByRole;

    @DatabaseField(columnName = "deleteFlag")
    @SerializedName("deleteFlag")
    private Boolean deleteFlag;

    @DatabaseField(columnName = "id")
    @SerializedName("id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedByRole() {
        return createdByRole;
    }

    public void setCreatedByRole(Integer createdByRole) {
        this.createdByRole = createdByRole;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getLastModifiedByRole() {
        return lastModifiedByRole;
    }

    public void setLastModifiedByRole(Integer lastModifiedByRole) {
        this.lastModifiedByRole = lastModifiedByRole;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
