package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 04/20/2018.
 */

public class ItemSubTypes {

    @DatabaseField(columnName = "isSelected")
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    @SerializedName("parentpriority")
    @DatabaseField(columnName = "parentpriority")
    private Integer parentpriority;

    @SerializedName("childpriority")
    @DatabaseField(columnName = "childpriority")
    private Integer childpriority;

    private String ItemTypeName;

    public String getItemTypeName() {
        return ItemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        ItemTypeName = itemTypeName;
    }

    public Integer getParentpriority() {
        return parentpriority;
    }

    public void setParentpriority(Integer parentpriority) {
        this.parentpriority = parentpriority;
    }

    public Integer getChildpriority() {
        return childpriority;
    }

    public void setChildpriority(Integer childpriority) {
        this.childpriority = childpriority;
    }

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
}