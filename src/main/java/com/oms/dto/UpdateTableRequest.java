package com.oms.dto;

import com.oms.model.enums.TableStatus;

import java.time.LocalDateTime;

public class UpdateTableRequest {
    private TableStatus status;
    private Integer guests;
    private String orderId;
    private LocalDateTime occupiedSince;

    public UpdateTableRequest() {}

    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }

    public Integer getGuests() { return guests; }
    public void setGuests(Integer guests) { this.guests = guests; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public LocalDateTime getOccupiedSince() { return occupiedSince; }
    public void setOccupiedSince(LocalDateTime occupiedSince) { this.occupiedSince = occupiedSince; }
}
