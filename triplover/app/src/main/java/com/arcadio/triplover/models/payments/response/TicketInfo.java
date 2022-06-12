
package com.arcadio.triplover.models.payments.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TicketInfo {

    @SerializedName("passengerInfo")
    @Expose
    private PassengerInfo passengerInfo;
    @SerializedName("ticketNumbers")
    @Expose
    private List<String> ticketNumbers = null;

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public List<String> getTicketNumbers() {
        return ticketNumbers;
    }

    public void setTicketNumbers(List<String> ticketNumbers) {
        this.ticketNumbers = ticketNumbers;
    }

}
