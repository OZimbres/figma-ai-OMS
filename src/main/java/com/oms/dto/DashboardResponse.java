package com.oms.dto;

public class DashboardResponse {
    private long activeOrders;
    private long preparing;
    private long readyToServe;
    private long completedToday;
    private double tableOccupancyPercent;
    private double avgPrepTime;

    public DashboardResponse() {}

    public DashboardResponse(long activeOrders, long preparing, long readyToServe,
                             long completedToday, double tableOccupancyPercent,
                             double avgPrepTime) {
        this.activeOrders = activeOrders;
        this.preparing = preparing;
        this.readyToServe = readyToServe;
        this.completedToday = completedToday;
        this.tableOccupancyPercent = tableOccupancyPercent;
        this.avgPrepTime = avgPrepTime;
    }

    public long getActiveOrders() { return activeOrders; }
    public void setActiveOrders(long activeOrders) { this.activeOrders = activeOrders; }

    public long getPreparing() { return preparing; }
    public void setPreparing(long preparing) { this.preparing = preparing; }

    public long getReadyToServe() { return readyToServe; }
    public void setReadyToServe(long readyToServe) { this.readyToServe = readyToServe; }

    public long getCompletedToday() { return completedToday; }
    public void setCompletedToday(long completedToday) { this.completedToday = completedToday; }

    public double getTableOccupancyPercent() { return tableOccupancyPercent; }
    public void setTableOccupancyPercent(double tableOccupancyPercent) { this.tableOccupancyPercent = tableOccupancyPercent; }

    public double getAvgPrepTime() { return avgPrepTime; }
    public void setAvgPrepTime(double avgPrepTime) { this.avgPrepTime = avgPrepTime; }
}
