package com.oms.dto;

public class TopProductData {
    private String name;
    private int sales;
    private double revenue;

    public TopProductData() {}

    public TopProductData(String name, int sales, double revenue) {
        this.name = name;
        this.sales = sales;
        this.revenue = revenue;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }

    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
}
