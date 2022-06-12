
package com.arcadio.triplover.models.payments.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class FlightInfo {

    @SerializedName("directions")
    @Expose
    private List<List<Direction>> directions = null;
    @SerializedName("bookingComponents")
    @Expose
    private List<BookingComponent> bookingComponents = null;
    @SerializedName("passengerFares")
    @Expose
    private PassengerFares passengerFares;
    @SerializedName("passengerCounts")
    @Expose
    private PassengerCounts passengerCounts;

    public List<List<Direction>> getDirections() {
        return directions;
    }

    public void setDirections(List<List<Direction>> directions) {
        this.directions = directions;
    }

    public List<BookingComponent> getBookingComponents() {
        return bookingComponents;
    }

    public void setBookingComponents(List<BookingComponent> bookingComponents) {
        this.bookingComponents = bookingComponents;
    }

    public PassengerFares getPassengerFares() {
        return passengerFares;
    }

    public void setPassengerFares(PassengerFares passengerFares) {
        this.passengerFares = passengerFares;
    }

    public PassengerCounts getPassengerCounts() {
        return passengerCounts;
    }

    public void setPassengerCounts(PassengerCounts passengerCounts) {
        this.passengerCounts = passengerCounts;
    }

}
