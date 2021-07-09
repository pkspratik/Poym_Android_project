package com.example.kanthi.projectmonitoring.PoJo;

/**
 * Created by Kanthi on 3/28/2018.
 */

public class Days {
    private String days;
    private Float totaltarget;
    private float totalactual;
    private String gmt;
    private String tourtype;
    private boolean startFlag;
    private long routeassignId;
    private Integer routeassignsummaryId;
    private String RouteAssigndate;
    private long routesalesviewsId;

    public String getRouteAssigndate() {
        return RouteAssigndate;
    }

    public void setRouteAssigndate(String routeAssigndate) {
        RouteAssigndate = routeAssigndate;
    }

    public String getTourtype() {
        return tourtype;
    }

    public void setTourtype(String tourtype) {
        this.tourtype = tourtype;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Float getTotaltarget() {
        return totaltarget;
    }

    public void setTotaltarget(Float totaltarget) {
        this.totaltarget = totaltarget;
    }

    public float getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(float totalactual) {
        this.totalactual = totalactual;
    }

    public boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }

    public long getRouteassignId() {
        return routeassignId;
    }

    public void setRouteassignId(long routeassignId) {
        this.routeassignId = routeassignId;
    }

    public Integer getRouteassignsummaryId() {
        return routeassignsummaryId;
    }

    public void setRouteassignsummaryId(Integer routeassignsummaryId) {
        this.routeassignsummaryId = routeassignsummaryId;
    }

    public long getRoutesalesviewsId() {
        return routesalesviewsId;
    }

    public void setRoutesalesviewsId(long routesalesviewsId) {
        this.routesalesviewsId = routesalesviewsId;
    }
}
