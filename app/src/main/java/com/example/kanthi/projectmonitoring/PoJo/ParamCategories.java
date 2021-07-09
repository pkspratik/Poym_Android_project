package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 3/13/2018.
 */

public class ParamCategories {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("inputtype")
    @DatabaseField(columnName = "inputtype")
    private String inputtype;

    @SerializedName("inputvalues")
    @DatabaseField(columnName = "inputvalues")
    private String inputvalues;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}