package com.chigozie.pojo;

import java.util.Objects;

import static java.util.Objects.hash;

public class MarketData {

    private final String lender;
    private final double rate;
    private final int available;

    public MarketData(String lender, double rate, int available) {
        this.lender = lender;
        this.rate = rate;
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    public String getLender() {
        return lender;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketData)) return false;
        MarketData that = (MarketData) o;
        return Double.compare(that.rate, rate) == 0 &&
                available == that.available &&
                Objects.equals(lender, that.lender);
    }

    @Override
    public int hashCode() {
        return hash(lender, rate, available);
    }
}
