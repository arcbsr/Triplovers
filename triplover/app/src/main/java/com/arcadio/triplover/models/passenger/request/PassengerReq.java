
package com.arcadio.triplover.models.passenger.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PassengerReq {

    @SerializedName("passengerInfoes")
    @Expose
    private List<PassengerInfo> passengerInfoes = new ArrayList<>();
    @SerializedName("taxRedemptions")
    @Expose
    private List<Object> taxRedemptions = null;
    @SerializedName("priceCodeRef")
    @Expose
    private String priceCodeRef;
    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("itemCodeRef")
    @Expose
    private String itemCodeRef;

    public List<PassengerInfo> getPassengerInfoes() {
        return passengerInfoes;
    }

    public void setPassengerInfoes(List<PassengerInfo> passengerInfoes) {
        this.passengerInfoes = passengerInfoes;
    }

    public void addPassengerInfoes(PassengerInfo passengerInfoe) {
        this.passengerInfoes.add(passengerInfoe);
    }

    public List<Object> getTaxRedemptions() {
        return taxRedemptions;
    }

    public void setTaxRedemptions(List<Object> taxRedemptions) {
        this.taxRedemptions = taxRedemptions;
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

}
