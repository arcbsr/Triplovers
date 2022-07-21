package com.arcadio.triplover.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AppGatewayCharge {

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("charge")
    @Expose
    private Double charge = 0d;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

}