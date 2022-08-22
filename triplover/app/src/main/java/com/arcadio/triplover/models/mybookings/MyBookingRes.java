package com.arcadio.triplover.models.mybookings;

import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MyBookingRes {

    @SerializedName("paxName")
    @Expose
    private String paxName;
    @SerializedName("issueDate")
    @Expose
    private String issueDate ;
    @SerializedName("travellDate")
    @Expose
    private String travellDate;
    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("pnr")
    @Expose
    private String pnr="";
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("status")
    @Expose
    private String status;

    public String getPaxName() {
        return paxName;
    }

    public void setPaxName(String paxName) {
        this.paxName = paxName;
    }

    public String dateFormat(String dateTime) {
        if(dateTime==null){
            return "--";
        }
        try {
            String date = "";// Utils.timeSeperator(dateTime);
            String[] dateTimes = dateTime.split("T");
            date += Utils.getDate(Utils.stringToMilliseconds(dateTimes[0], "yyyy-MM-dd"), Constants.DATE_FORMAT_NORMAL);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public String getIssueDate() {
        return dateFormat(issueDate);
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getTravellDate() {
        return dateFormat(travellDate);
    }

    public void setTravellDate(String travellDate) {
        this.travellDate = travellDate;
    }

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

    public String getPnr() {
        if(pnr == null){
            pnr = "";
        }
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}