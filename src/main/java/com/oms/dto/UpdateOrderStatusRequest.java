package com.oms.dto;

import com.oms.model.enums.OrderStatus;

public class UpdateOrderStatusRequest {
    private OrderStatus status;

    public UpdateOrderStatusRequest() {}

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
