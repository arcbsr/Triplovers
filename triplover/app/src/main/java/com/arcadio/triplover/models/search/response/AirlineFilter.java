
package com.arcadio.triplover.models.search.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AirlineFilter {

    @SerializedName("airlineCode")
    @Expose
    private String airlineCode;
    @SerializedName("airlineName")
    @Expose
    private String airlineName;
    @SerializedName("totalFlights")
    @Expose
    private Integer totalFlights;
    @SerializedName("minPrice")
    @Expose
    private Double minPrice;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Integer getTotalFlights() {
        return totalFlights;
    }

    public void setTotalFlights(Integer totalFlights) {
        this.totalFlights = totalFlights;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

}
