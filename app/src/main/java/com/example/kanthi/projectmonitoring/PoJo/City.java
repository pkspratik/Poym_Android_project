package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * Created by kanthi on 21/2/18.
 */

public class City {


    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "country_id")
    private int countryId;

    @DatabaseField(columnName = "delete_flag")
    @SerializedName("deleteflag")
    private boolean deleteFlag;

    @DatabaseField(columnName = "state_id")
    private int stateId;

    @DatabaseField(columnName = "state")
    private int state;

    @DatabaseField(columnName = "district")
    private int district;

    @DatabaseField(columnName = "site")
    private int site;

    @DatabaseField(columnName = "facility")
    private int facility;

    @DatabaseField(columnName = "last_modified_by")
    @SerializedName("lastmodifiedby")
    private int lastModifiedBy;

    @DatabaseField(columnName = "last_modified_time")
    @SerializedName("lastmodifiedtime")
    private String lastModifiedTime;

    @DatabaseField(columnName = "id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public City() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof City && ((City) o).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public City(int id) {
        this.id = id;
    }
}
