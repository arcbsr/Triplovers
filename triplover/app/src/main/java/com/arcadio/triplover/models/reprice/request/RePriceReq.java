package com.arcadio.triplover.models.reprice.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class RePriceReq implements Serializable {

    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("itemCodeRef")
    @Expose
    private String itemCodeRef;
    @SerializedName("taxRedemptions")
    @Expose
    private List<String> taxRedemptions = new ArrayList<>();
    @SerializedName("segmentCodeRefs")
    @Expose
    private List<String> segmentCodeRefs = new ArrayList<>();

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

    public List<String> getTaxRedemptions() {
        return taxRedemptions;
    }

    public void setTaxRedemptions(List<String> taxRedemptions) {
        this.taxRedemptions = taxRedemptions;
    }

    public List<String> getSegmentCodeRefs() {
        return segmentCodeRefs;
    }

    public void setSegmentCodeRefs(List<String> segmentCodeRefs) {
        this.segmentCodeRefs = segmentCodeRefs;
    }

}