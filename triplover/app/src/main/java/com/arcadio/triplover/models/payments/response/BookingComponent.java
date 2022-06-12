
package com.arcadio.triplover.models.payments.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BookingComponent {

    @SerializedName("discountPrice")
    @Expose
    private Double discountPrice;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("basePrice")
    @Expose
    private Double basePrice;
    @SerializedName("taxes")
    @Expose
    private Double taxes;
    @SerializedName("ait")
    @Expose
    private Double ait;
    @SerializedName("fareReference")
    @Expose
    private String fareReference;
    @SerializedName("agentAdditionalPrice")
    @Expose
    private Double agentAdditionalPrice;

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
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

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getAit() {
        return ait;
    }

    public void setAit(Double ait) {
        this.ait = ait;
    }

    public String getFareReference() {
        return fareReference;
    }

    public void setFareReference(String fareReference) {
        this.fareReference = fareReference;
    }

    public Double getAgentAdditionalPrice() {
        return agentAdditionalPrice;
    }

    public void setAgentAdditionalPrice(Double agentAdditionalPrice) {
        this.agentAdditionalPrice = agentAdditionalPrice;
    }

}
