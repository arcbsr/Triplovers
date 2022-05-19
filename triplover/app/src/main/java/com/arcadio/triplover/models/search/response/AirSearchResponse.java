
package com.arcadio.triplover.models.search.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AirSearchResponse {

    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("itemCodeRef")
    @Expose
    private String itemCodeRef;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("basePrice")
    @Expose
    private Double basePrice;
    @SerializedName("eqivqlBasePrice")
    @Expose
    private Double eqivqlBasePrice;
    @SerializedName("taxes")
    @Expose
    private Double taxes;
    @SerializedName("platingCarrierName")
    @Expose
    private String platingCarrierName;
    @SerializedName("platingCarrier")
    @Expose
    private String platingCarrier;
    @SerializedName("refundable")
    @Expose
    private Boolean refundable;
    @SerializedName("directions")
    @Expose
    private List<ArrayList<Direction>> directions = null;
    @SerializedName("bookingComponents")
    @Expose
    private List<BookingComponent> bookingComponents = null;
    @SerializedName("passengerFares")
    @Expose
    private PassengerFares passengerFares;
    @SerializedName("passengerCounts")
    @Expose
    private PassengerCounts passengerCounts;
    @SerializedName("bookable")
    @Expose
    private Boolean bookable;

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

    public String getItemCodeRef() {
        return itemCodeRef;
    }

    public void setItemCodeRef(String itemCodeRef) {
        this.itemCodeRef = itemCodeRef;
    }

    public int getTotalPrice() {
        try {
            return totalPrice.intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getEqivqlBasePrice() {
        return eqivqlBasePrice;
    }

    public void setEqivqlBasePrice(Double eqivqlBasePrice) {
        this.eqivqlBasePrice = eqivqlBasePrice;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public String getPlatingCarrierName() {
        return platingCarrierName;
    }

    public void setPlatingCarrierName(String platingCarrierName) {
        this.platingCarrierName = platingCarrierName;
    }

    public String getPlatingCarrier() {
        return platingCarrier;
    }

    public void setPlatingCarrier(String platingCarrier) {
        this.platingCarrier = platingCarrier;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public List<ArrayList<Direction>> getDirections() {
        return directions;
    }

    public void setDirections(List<ArrayList<Direction>> directions) {
        this.directions = directions;
    }

    public List<BookingComponent> getBookingComponents() {
        return bookingComponents;
    }

    public void setBookingComponents(List<BookingComponent> bookingComponents) {
        this.bookingComponents = bookingComponents;
    }

    public PassengerFares getPassengerFares() {
        return passengerFares;
    }

    public void setPassengerFares(PassengerFares passengerFares) {
        this.passengerFares = passengerFares;
    }

    public PassengerCounts getPassengerCounts() {
        return passengerCounts;
    }

    public void setPassengerCounts(PassengerCounts passengerCounts) {
        this.passengerCounts = passengerCounts;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

}
