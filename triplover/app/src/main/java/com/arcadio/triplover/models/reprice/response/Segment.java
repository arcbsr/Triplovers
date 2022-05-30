
package com.arcadio.triplover.models.reprice.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Segment {

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("fromAirport")
    @Expose
    private String fromAirport;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("toAirport")
    @Expose
    private String toAirport;
    @SerializedName("group")
    @Expose
    private Integer group;
    @SerializedName("departure")
    @Expose
    private String departure;
    @SerializedName("arrival")
    @Expose
    private String arrival;
    @SerializedName("airline")
    @Expose
    private String airline;
    @SerializedName("flightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("segmentCodeRef")
    @Expose
    private Object segmentCodeRef;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("serviceClass")
    @Expose
    private String serviceClass;
    @SerializedName("plane")
    @Expose
    private List<String> plane = null;
    @SerializedName("duration")
    @Expose
    private List<String> duration = null;
    @SerializedName("techStops")
    @Expose
    private List<Integer> techStops = null;
    @SerializedName("bookingClass")
    @Expose
    private String bookingClass;
    @SerializedName("bookingCount")
    @Expose
    private Object bookingCount;
    @SerializedName("baggage")
    @Expose
    private List<Object> baggage = null;
    @SerializedName("fareBasisCode")
    @Expose
    private String fareBasisCode;
    @SerializedName("airlineCode")
    @Expose
    private String airlineCode;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
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

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Object getSegmentCodeRef() {
        return segmentCodeRef;
    }

    public void setSegmentCodeRef(Object segmentCodeRef) {
        this.segmentCodeRef = segmentCodeRef;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public List<String> getPlane() {
        return plane;
    }

    public void setPlane(List<String> plane) {
        this.plane = plane;
    }

    public List<String> getDuration() {
        return duration;
    }

    public void setDuration(List<String> duration) {
        this.duration = duration;
    }

    public List<Integer> getTechStops() {
        return techStops;
    }

    public void setTechStops(List<Integer> techStops) {
        this.techStops = techStops;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
    }

    public Object getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Object bookingCount) {
        this.bookingCount = bookingCount;
    }

    public List<Object> getBaggage() {
        return baggage;
    }

    public void setBaggage(List<Object> baggage) {
        this.baggage = baggage;
    }

    public String getFareBasisCode() {
        return fareBasisCode;
    }

    public void setFareBasisCode(String fareBasisCode) {
        this.fareBasisCode = fareBasisCode;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

}
