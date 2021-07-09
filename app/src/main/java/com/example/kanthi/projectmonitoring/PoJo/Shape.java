package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;

public class Shape {

    //https://github.com/j256/ormlite-jdbc/tree/master/src/test/java/com/j256/ormlite/examples/foreignCollection

    @SerializedName("id")
    @DatabaseField(columnName = "id",generatedId = true)
    private int id;

    @ForeignCollectionField
    private ForeignCollection<Cord> cords;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public ForeignCollection<Cord> getCords() {
        return cords;
    }

    public void setCords(ForeignCollection<Cord> cords) {
        this.cords = cords;
    }
}