package com.arcadio.triplover.models.mybookings;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MyBookings {

    @SerializedName("data")
    @Expose
    private List<MyBookingRes> data = null;

    public List<MyBookingRes> getData() {
        return data;
    }

    public void setData(List<MyBookingRes> data) {
        this.data = data;
    }

}
