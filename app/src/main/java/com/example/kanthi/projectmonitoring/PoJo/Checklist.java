package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Checklist{


    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("zoneId")
    @DatabaseField(columnName = "zoneId")
    private Integer zoneId;

    @SerializedName("tourtypeid")
    @DatabaseField(columnName = "tourtypeid")
    private Integer tourtypeid;

    @SerializedName("inputtype")
    @DatabaseField(columnName = "inputtype")
    private String inputtype;

    @SerializedName("inputvalues")
    @DatabaseField(columnName = "inputvalues")
    private String inputvalues;

    @SerializedName("seq")
    @DatabaseField(columnName = "seq")
    private Integer seq;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getTourtypeid() {
        return tourtypeid;
    }

    public void setTourtypeid(Integer tourtypeid) {
        this.tourtypeid = tourtypeid;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public String getInputvalues() {
        return inputvalues;
    }

    public void setInputvalues(String inputvalues) {
        this.inputvalues = inputvalues;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}