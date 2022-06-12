package com.arcadio.triplover.models.mybookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MyBookingReq {

    @SerializedName("pnr")
    @Expose
    private String pnr = "";
    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID = "";
    @SerializedName("status")
    @Expose
    private String status = "";
    @SerializedName("fromDate")
    @Expose
    private String fromDate = "";
    @SerializedName("toDate")
    @Expose
    private String toDate = "";
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber = "";

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}