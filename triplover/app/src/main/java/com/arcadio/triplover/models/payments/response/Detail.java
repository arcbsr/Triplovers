
package com.arcadio.triplover.models.payments.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Detail {

    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("originName")
    @Expose
    private Object originName;
    @SerializedName("originTerminal")
    @Expose
    private Object originTerminal;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("destinationName")
    @Expose
    private Object destinationName;
    @SerializedName("destinationTerminal")
    @Expose
    private Object destinationTerminal;
    @SerializedName("departure")
    @Expose
    private String departure;
    @SerializedName("arrival")
    @Expose
    private String arrival;
    @SerializedName("flightTime")
    @Expose
    private String flightTime;
    @SerializedName("travelTime")
    @Expose
    private String travelTime;
    @SerializedName("equipment")
    @Expose
    private String equipment;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Object getOriginName() {
        return originName;
    }

    public void setOriginName(Object originName) {
        this.originName = originName;
    }

    public Object getOriginTerminal() {
        return originTerminal;
    }

    public void setOriginTerminal(Object originTerminal) {
        this.originTerminal = originTerminal;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Object getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(Object destinationName) {
        this.destinationName = destinationName;
    }

    public Object getDestinationTerminal() {
        return destinationTerminal;
    }

    public void setDestinationTerminal(Object destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

}
