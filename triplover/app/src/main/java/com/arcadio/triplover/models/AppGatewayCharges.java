package com.arcadio.triplover.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AppGatewayCharges {


    @SerializedName("data")
    @Expose
    private List<AppGatewayCharge> data = new ArrayList<>();

    public List<AppGatewayCharge> getData() {
        return data;
    }

    public void setData(List<AppGatewayCharge> data) {
        this.data = data;
    }

}