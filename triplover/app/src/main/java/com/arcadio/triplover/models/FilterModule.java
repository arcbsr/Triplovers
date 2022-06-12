package com.arcadio.triplover.models;

import java.util.ArrayList;
import java.util.List;

public class FilterModule {
    public int priceMin = 0;

    public FilterModule(int priceMin, int priceMax) {
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.priceHighest = priceMax;
    }

    public int priceMax = 0;
    public int priceHighest = 0;

    @Override
    public String toString() {
        return "FilterModule{" +
                "priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", priceHighest=" + priceHighest +
                ", flightName=" + flightName +
                '}';
    }

    public List<String> flightName = new ArrayList<>();
    public boolean[] stops = new boolean[3];
}
