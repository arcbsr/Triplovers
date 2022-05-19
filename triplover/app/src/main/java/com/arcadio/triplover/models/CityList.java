package com.arcadio.triplover.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class CityList {


    @SerializedName("data")
    @Expose
    private List<CityModels> data = new ArrayList<>();

    public List<CityModels> getData() {
        return data;
    }

    public void setData(List<CityModels> data) {
        this.data = data;
    }
}
