
package com.arcadio.triplover.models.search.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Adt {

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
    @SerializedName("taxesBreakdown")
    @Expose
    private List<TaxesBreakdown> taxesBreakdown = null;

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

    public List<TaxesBreakdown> getTaxesBreakdown() {
        return taxesBreakdown;
    }

    public void setTaxesBreakdown(List<TaxesBreakdown> taxesBreakdown) {
        this.taxesBreakdown = taxesBreakdown;
    }

}
