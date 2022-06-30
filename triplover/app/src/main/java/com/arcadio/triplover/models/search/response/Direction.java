
package com.arcadio.triplover.models.search.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Direction {
    @Expose(serialize = false)
    public boolean isSeleced = false, isEnable = true;
    @Expose(serialize = false)
    public Direction direction;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("fromAirport")
    @Expose
    private String fromAirport;
    @SerializedName("toAirport")
    @Expose
    private String toAirport;
    @SerializedName("platingCarrierCode")
    @Expose
    private String platingCarrierCode;
    @SerializedName("platingCarrierName")
    @Expose
    private String platingCarrierName;

    public int totalPrice = 0;
    public double totalPriceD = 0, basePrice = 0, tax = 0, discount = 0, ait = 0;
    public Integer searchPosition = 0;
    public PassengerFares passengerFares = null;


    @SerializedName("stops")
    @Expose
    private Integer stops;
    @SerializedName("segments")
    @Expose
    private List<Segment> segments = null;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getPlatingCarrierCode() {
        return platingCarrierCode;
    }

    public void setPlatingCarrierCode(String platingCarrierCode) {
        this.platingCarrierCode = platingCarrierCode;
    }

    public String getPlatingCarrierName() {
        return platingCarrierName;
    }

    public void setPlatingCarrierName(String platingCarrierName) {
        this.platingCarrierName = platingCarrierName;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

}
