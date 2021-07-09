package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 1/30/2018.
 */

public class MRP {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("number")
    @DatabaseField(columnName = "number")
    private Integer number;

    @SerializedName("unitmeasurement")
    @DatabaseField(columnName = "unitmeasurement")
    private Integer unitmeasurement;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getUnitmeasurement() {
        return unitmeasurement;
    }

    public void setUnitmeasurement(Integer unitmeasurement) {
        this.unitmeasurement = unitmeasurement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
