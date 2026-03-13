package com.oms.dto;

public class RevenueDataPoint {
    private String hour;
    private int orders;
    private double revenue;

    public RevenueDataPoint() {}

    public RevenueDataPoint(String hour, int orders, double revenue) {
        this.hour = hour;
        this.orders = orders;
        this.revenue = revenue;
    }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public int getOrders() { return orders; }
    public void setOrders(int orders) { this.orders = orders; }

    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
}
