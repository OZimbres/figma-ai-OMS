package com.oms.model;

import com.oms.model.enums.BillStatus;
import com.oms.model.enums.PaymentMethod;

import java.time.LocalDateTime;

public class Bill {
    private String id;
    private String tableId;
    private int tableNumber;
    private String clientName;
    private String orderId;
    private double total;
    private PaymentMethod paymentMethod;
    private BillStatus status;
    private LocalDateTime createdAt;

    public Bill() {}

    public Bill(String id, String tableId, int tableNumber, String clientName,
                String orderId, double total, PaymentMethod paymentMethod,
                BillStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.clientName = clientName;
        this.orderId = orderId;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTableId() { return tableId; }
    public void setTableId(String tableId) { this.tableId = tableId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public BillStatus getStatus() { return status; }
    public void setStatus(BillStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
