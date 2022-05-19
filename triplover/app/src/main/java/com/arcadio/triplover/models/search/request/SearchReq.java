
package com.arcadio.triplover.models.search.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

public class SearchReq {

    @SerializedName("routes")
    @Expose
    private List<Route> routes = new ArrayList<>();
    @SerializedName("adults")
    @Expose
    private Integer adults = 0;
    @SerializedName("childs")
    @Expose
    private Integer childs = 0;
    @SerializedName("infants")
    @Expose
    private Integer infants = 0;
    @SerializedName("cabinClass")
    @Expose
    private Integer cabinClass = 1;
    @SerializedName("prohibitedCarriers")
    @Expose
    private List<Object> prohibitedCarriers = null;
    @SerializedName("childrenAges")
    @Expose
    private List<Object> childrenAges = null;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }

    public Integer getInfants() {
        return infants;
    }

    public void setInfants(Integer infants) {
        this.infants = infants;
    }

    public Integer getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(Integer cabinClass) {
        this.cabinClass = cabinClass;
    }

    public List<Object> getProhibitedCarriers() {
        return prohibitedCarriers;
    }

    public void setProhibitedCarriers(List<Object> prohibitedCarriers) {
        this.prohibitedCarriers = prohibitedCarriers;
    }

    public List<Object> getChildrenAges() {
        return childrenAges;
    }

    public void setChildrenAges(List<Object> childrenAges) {
        this.childrenAges = childrenAges;
    }

}
