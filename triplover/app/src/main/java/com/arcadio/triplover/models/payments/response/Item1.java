
package com.arcadio.triplover.models.payments.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Item1 {
    @Expose(serialize = false)
    public String issueDate = "";
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = null;
    @SerializedName("ticketInfoes")
    @Expose
    private List<TicketInfo> ticketInfoes = null;
    @SerializedName("flightInfo")
    @Expose
    private FlightInfo flightInfo;
    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("itemCodeRef")
    @Expose
    private String itemCodeRef;
    @SerializedName("priceCodeRef")
    @Expose
    private String priceCodeRef;
    @SerializedName("bookingCodeRef")
    @Expose
    private String bookingCodeRef;
    @SerializedName("ticketCodeRef")
    @Expose
    private String ticketCodeRef;
    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public List<TicketInfo> getTicketInfoes() {
        return ticketInfoes;
    }

    public void setTicketInfoes(List<TicketInfo> ticketInfoes) {
        this.ticketInfoes = ticketInfoes;
    }

    public FlightInfo getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(FlightInfo flightInfo) {
        this.flightInfo = flightInfo;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public String getItemCodeRef() {
        return itemCodeRef;
    }

    public void setItemCodeRef(String itemCodeRef) {
        this.itemCodeRef = itemCodeRef;
    }

    public String getPriceCodeRef() {
        return priceCodeRef;
    }

    public void setPriceCodeRef(String priceCodeRef) {
        this.priceCodeRef = priceCodeRef;
    }

    public String getBookingCodeRef() {
        return bookingCodeRef;
    }

    public void setBookingCodeRef(String bookingCodeRef) {
        this.bookingCodeRef = bookingCodeRef;
    }

    public String getTicketCodeRef() {
        return ticketCodeRef;
    }

    public void setTicketCodeRef(String ticketCodeRef) {
        this.ticketCodeRef = ticketCodeRef;
    }

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

}
