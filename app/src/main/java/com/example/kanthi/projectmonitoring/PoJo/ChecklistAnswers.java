package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class ChecklistAnswers {
    @SerializedName("checklistid")
    @DatabaseField(columnName = "checklistid")
    private Integer checklistid;

    @SerializedName("questiontype")
    @DatabaseField(columnName = "questiontype")
    private String questiontype;

    @SerializedName("answers")
    @DatabaseField(columnName = "answers")
    private String answers;

    @SerializedName("usernameid")
    @DatabaseField(columnName = "usernameid")
    private Integer usernameid;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("createdBy")
    @DatabaseField(columnName = "createdBy")
    private Integer createdBy;

    @SerializedName("lastModifiedBy")
    @DatabaseField(columnName = "lastModifiedBy")
    private Integer lastModifiedBy;

    @SerializedName("createdDate")
    @DatabaseField(columnName = "createdDate")
    private String createdDate;

    @SerializedName("lastModifiedDate")
    @DatabaseField(columnName = "lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("routeassignmentid")
    @DatabaseField(columnName = "routeassignmentid")
    private long routeassignmentid;

    @SerializedName("routeassignmentsummaryid")
    @DatabaseField(columnName = "routeassignmentsummaryid")
    private long routeassignmentsummaryid;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private long id;

    @SerializedName("updateflag")
    @DatabaseField(columnName = "updateflag")
    private boolean updateflag;

    @DatabaseField(columnName = "insert_flag")
    @SerializedName("insertflag")
    private boolean insertFlag;

    public Integer getChecklistid() {
        return checklistid;
    }

    public void setChecklistid(Integer checklistid) {
        this.checklistid = checklistid;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Integer getUsernameid() {
        return usernameid;
    }

    public void setUsernameid(Integer usernameid) {
        this.usernameid = usernameid;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getRouteassignmentid() {
        return routeassignmentid;
    }

    public void setRouteassignmentid(long routeassignmentid) {
        this.routeassignmentid = routeassignmentid;
    }

    public long getRouteassignmentsummaryid() {
        return routeassignmentsummaryid;
    }

    public void setRouteassignmentsummaryid(long routeassignmentsummaryid) {
        this.routeassignmentsummaryid = routeassignmentsummaryid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(boolean updateflag) {
        this.updateflag = updateflag;
    }

    public boolean getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(boolean insertFlag) {
        this.insertFlag = insertFlag;
    }
}
