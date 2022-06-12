
package com.arcadio.triplover.models.payments.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Adt {

    @SerializedName("discountPrice")
    @Expose
    private Double discountPrice;
    @SerializedName("ait")
    @Expose
    private Double ait;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("basePrice")
    @Expose
    private Double basePrice;
    @SerializedName("equivalentBasePrice")
    @Expose
    private Double equivalentBasePrice;
    @SerializedName("taxes")
    @Expose
    private Double taxes;
    @SerializedName("serviceCharge")
    @Expose
    private Double serviceCharge;
    @SerializedName("taxesBreakdown")
    @Expose
    private List<TaxesBreakdown> taxesBreakdown = null;

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getAit() {
        return ait;
    }

    public void setAit(Double ait) {
        this.ait = ait;
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

    public Double getEquivalentBasePrice() {
        return equivalentBasePrice;
    }

    public void setEquivalentBasePrice(Double equivalentBasePrice) {
        this.equivalentBasePrice = equivalentBasePrice;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public List<TaxesBreakdown> getTaxesBreakdown() {
        return taxesBreakdown;
    }

    public void setTaxesBreakdown(List<TaxesBreakdown> taxesBreakdown) {
        this.taxesBreakdown = taxesBreakdown;
    }

}
