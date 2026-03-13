package com.oms.model;

import com.oms.model.enums.TableStatus;

import java.time.LocalDateTime;

public class RestaurantTable {
    private String id;
    private int number;
    private int seats;
    private int guests;
    private TableStatus status;
    private String orderId;
    private LocalDateTime occupiedSince;

    public RestaurantTable() {}

    public RestaurantTable(String id, int number, int seats, int guests,
                           TableStatus status, String orderId, LocalDateTime occupiedSince) {
        this.id = id;
        this.number = number;
        this.seats = seats;
        this.guests = guests;
        this.status = status;
        this.orderId = orderId;
        this.occupiedSince = occupiedSince;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public LocalDateTime getOccupiedSince() { return occupiedSince; }
    public void setOccupiedSince(LocalDateTime occupiedSince) { this.occupiedSince = occupiedSince; }
}
