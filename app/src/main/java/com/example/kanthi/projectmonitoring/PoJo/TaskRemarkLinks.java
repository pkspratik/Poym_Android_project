package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 19-06-2018.
 */

public class TaskRemarkLinks {

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("tasktypeid")
    @DatabaseField(columnName = "tasktypeid")
    private Integer tasktypeid;

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("dup_id")
    @DatabaseField(columnName = "dup_id")
    private Integer dup_id;

    @SerializedName("remarkid")
    @DatabaseField(columnName = "remarkid")
    private Integer remarkid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getTasktypeid() {
        return tasktypeid;
    }

    public void setTasktypeid(Integer tasktypeid) {
        this.tasktypeid = tasktypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDup_id() {
        return dup_id;
    }

    public void setDup_id(Integer dup_id) {
        this.dup_id = dup_id;
    }


    public Integer getRemarkid() {
        return remarkid;
    }

    public void setRemarkid(Integer remarkid) {
        this.remarkid = remarkid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}