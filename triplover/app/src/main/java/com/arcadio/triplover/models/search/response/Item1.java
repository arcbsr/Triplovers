
package com.arcadio.triplover.models.search.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Item1 {

    @SerializedName("airSearchResponses")
    @Expose
    private List<AirSearchResponse> airSearchResponses = null;
    @SerializedName("airlineFilters")
    @Expose
    private List<AirlineFilter> airlineFilters = null;
    @SerializedName("minMaxPrice")
    @Expose
    private MinMaxPrice minMaxPrice;
    @SerializedName("stops")
    @Expose
    private List<Integer> stops = null;

    public List<AirSearchResponse> getAirSearchResponses() {
        return airSearchResponses;
    }

    public void setAirSearchResponses(List<AirSearchResponse> airSearchResponses) {
        this.airSearchResponses = airSearchResponses;
    }

    public List<AirlineFilter> getAirlineFilters() {
        return airlineFilters;
    }

    public void setAirlineFilters(List<AirlineFilter> airlineFilters) {
        this.airlineFilters = airlineFilters;
    }

    public MinMaxPrice getMinMaxPrice() {
        return minMaxPrice;
    }

    public void setMinMaxPrice(MinMaxPrice minMaxPrice) {
        this.minMaxPrice = minMaxPrice;
    }

    public List<Integer> getStops() {
        return stops;
    }

    public void setStops(List<Integer> stops) {
        this.stops = stops;
    }

}
