package com.mprzenzak.lab06.models;

import java.util.ArrayList;
import java.util.List;

public class PriceListWrapper {
    private List<PriceList> prices;

    public PriceListWrapper() {
        this.prices = new ArrayList<>();
    }

    public List<PriceList> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceList> prices) {
        this.prices = prices;
    }
}
