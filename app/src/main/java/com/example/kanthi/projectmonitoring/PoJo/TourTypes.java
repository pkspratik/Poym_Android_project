package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 5/22/2018.
 */

public class TourTypes {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("worktypeid")
    @DatabaseField(columnName = "worktypeid")
    private Integer worktypeid;

    @SerializedName("phaseId")
    @DatabaseField(columnName = "phaseId")
    private Integer phaseId;

    @SerializedName("processId")
    @DatabaseField(columnName = "processId")
    private Integer processId;

    @SerializedName("subProcessId")
    @DatabaseField(columnName = "subProcessId")
    private Integer subProcessId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(Integer subProcessId) {
        this.subProcessId = subProcessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}