package com.example.kanthi.projectmonitoring.PoJo;

/**
 * Created by Kanthi on 3/13/2018.
 */

public class Details {
    private String name,value;

    public Details(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
