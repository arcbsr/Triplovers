
package com.arcadio.triplover.models.reprice.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Item1 {

    @SerializedName("isPriceChanged")
    @Expose
    private Boolean isPriceChanged;
    @SerializedName("priceCodeRef")
    @Expose
    private String priceCodeRef;
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
    private Object platingCarrierName;
    @SerializedName("platingCarrier")
    @Expose
    private Object platingCarrier;
    @SerializedName("refundable")
    @Expose
    private Boolean refundable;
    @SerializedName("directions")
    @Expose
    private List<List<Direction>> directions = null;
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

    public Boolean getIsPriceChanged() {
        return isPriceChanged;
    }

    public void setIsPriceChanged(Boolean isPriceChanged) {
        this.isPriceChanged = isPriceChanged;
    }

    public String getPriceCodeRef() {
        return priceCodeRef;
    }

    public void setPriceCodeRef(String priceCodeRef) {
        this.priceCodeRef = priceCodeRef;
    }

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

    public Double getTotalPrice() {
        return totalPrice;
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

    public Object getPlatingCarrierName() {
        return platingCarrierName;
    }

    public void setPlatingCarrierName(Object platingCarrierName) {
        this.platingCarrierName = platingCarrierName;
    }

    public Object getPlatingCarrier() {
        return platingCarrier;
    }

    public void setPlatingCarrier(Object platingCarrier) {
        this.platingCarrier = platingCarrier;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public List<List<Direction>> getDirections() {
        return directions;
    }

    public void setDirections(List<List<Direction>> directions) {
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
