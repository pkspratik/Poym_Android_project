package com.example.kanthi.projectmonitoring.Graphs;

public class ResourcePojo {
    private String taskname,resourcetype,resttype;
    private int projected_value,utilized_value;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getResttype() {
        return resttype;
    }

    public void setResttype(String resttype) {
        this.resttype = resttype;
    }

    public int getProjected_value() {
        return projected_value;
    }

    public void setProjected_value(int projected_value) {
        this.projected_value = projected_value;
    }

    public int getUtilized_value() {
        return utilized_value;
    }

    public void setUtilized_value(int utilized_value) {
        this.utilized_value = utilized_value;
    }
}
