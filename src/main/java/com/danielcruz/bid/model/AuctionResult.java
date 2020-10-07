package com.danielcruz.bid.model;

public class AuctionResult extends Bid {
    private double revenue;

    public AuctionResult(Bid bid, double revenue) {
        super(bid.getName(), bid.getValue());
        this.revenue = revenue;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
