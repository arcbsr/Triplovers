
package com.arcadio.triplover.models.uidecoration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Home {
    @SerializedName("splash")
    @Expose
    private String splash = "";
    @SerializedName("background")
    @Expose
    private String background = "";
    @SerializedName("flight_icon")
    @Expose
    private String flightIcon = "";
    @SerializedName("hotel_icon")
    @Expose
    private String hotelIcon = "";
    @SerializedName("tour_icon")
    @Expose
    private String tourIcon = "";
    @SerializedName("visa_icon")
    @Expose
    private String visaIcon = "";
    @SerializedName("home_offer_place1")
    @Expose
    private HomeOfferPlace1 homeOfferPlace1 = new HomeOfferPlace1();
    @SerializedName("home_offer_place2")
    @Expose
    private HomeOfferPlace2 homeOfferPlace2 = new HomeOfferPlace2();
    @SerializedName("search_offer_place3")
    @Expose
    private SearchOfferPlace3 searchOfferPlace3 = new SearchOfferPlace3();

    public String getSplash() {
        return splash;
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getFlightIcon() {
        return flightIcon;
    }

    public void setFlightIcon(String flightIcon) {
        this.flightIcon = flightIcon;
    }

    public String getHotelIcon() {
        return hotelIcon;
    }

    public void setHotelIcon(String hotelIcon) {
        this.hotelIcon = hotelIcon;
    }

    public String getTourIcon() {
        return tourIcon;
    }

    public void setTourIcon(String tourIcon) {
        this.tourIcon = tourIcon;
    }

    public String getVisaIcon() {
        return visaIcon;
    }

    public void setVisaIcon(String visaIcon) {
        this.visaIcon = visaIcon;
    }

    public HomeOfferPlace1 getHomeOfferPlace1() {
        return homeOfferPlace1;
    }

    public void setHomeOfferPlace1(HomeOfferPlace1 homeOfferPlace1) {
        this.homeOfferPlace1 = homeOfferPlace1;
    }

    public HomeOfferPlace2 getHomeOfferPlace2() {
        return homeOfferPlace2;
    }

    public void setHomeOfferPlace2(HomeOfferPlace2 homeOfferPlace2) {
        this.homeOfferPlace2 = homeOfferPlace2;
    }

    public SearchOfferPlace3 getSearchOfferPlace3() {
        return searchOfferPlace3;
    }

    public void setSearchOfferPlace3(SearchOfferPlace3 searchOfferPlace3) {
        this.searchOfferPlace3 = searchOfferPlace3;
    }

}
