
package com.arcadio.triplover.models.search.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MinMaxPrice {

    @SerializedName("minPrice")
    @Expose
    private Double minPrice;
    @SerializedName("maxPrice")
    @Expose
    private Double maxPrice;

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

}
