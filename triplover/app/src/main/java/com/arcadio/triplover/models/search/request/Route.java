
package com.arcadio.triplover.models.search.request;

import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Route {

    @Expose(serialize = false)
    private String destinationcityname = "Click to search";
    @Expose(serialize = false)
    public String departCountryName = "", desCountryName = "";

    public long getTimeMilisecon() {
        return timeMilisecon;
    }

    public void setTimeMilisecon(long timeMilisecon) {
        this.timeMilisecon = timeMilisecon;
    }

    @Expose(serialize = false)
    private long timeMilisecon = System.currentTimeMillis();

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Expose(serialize = false)
    private Direction direction = null;
    @SerializedName("origin")
    @Expose
    private String origin = "DAC";
    @SerializedName("destination")
    @Expose
    private String destination = "CGP";
    @SerializedName("departureDate")
    @Expose
    private String departureDate = Utils.getDateString(Calendar.getInstance().getTime());

    public String getDepartCityName() {
        return departCityName;
    }

    public void setDepartCityName(String departCityName) {
        this.departCityName = departCityName;
    }

    private String departCityName = "Click to search";

    public String getDestinationcityname() {
        return destinationcityname;
    }

    public void setDestinationcityname(String destinationcityname) {
        this.destinationcityname = destinationcityname;
    }


    public Route() {
    }

    public Route(String origin, String destination, String departureDate) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

}
