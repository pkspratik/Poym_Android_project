package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kanthi on 4/30/2018.
 */

public class RiskTypes {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}