package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kanthi on 1/12/2018.
 */

public class ItemFlavours {

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("itemcategoryId")
    @DatabaseField(columnName = "itemcategoryId")
    private Integer itemcategoryId;

    @SerializedName("id")
    @DatabaseField(columnName = "id")
    private Integer id;

    @SerializedName("itemtypeId")
    @DatabaseField(columnName = "itemtypeId")
    private Integer itemtypeId;

    @SerializedName("itemsubtypeId")
    @DatabaseField(columnName = "itemsubtypeId")
    private Integer itemsubtypeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemcategoryId() {
        return itemcategoryId;
    }

    public void setItemcategoryId(Integer itemcategoryId) {
        this.itemcategoryId = itemcategoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemtypeId() {
        return itemtypeId;
    }

    public void setItemtypeId(Integer itemtypeId) {
        this.itemtypeId = itemtypeId;
    }

    public Integer getItemsubtypeId() {
        return itemsubtypeId;
    }

    public void setItemsubtypeId(Integer itemsubtypeId) {
        this.itemsubtypeId = itemsubtypeId;
    }

}
