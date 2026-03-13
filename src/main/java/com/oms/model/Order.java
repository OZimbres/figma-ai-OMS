package com.oms.model;

import com.oms.model.enums.OrderStatus;
import com.oms.model.enums.Priority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String tableId;
    private int tableNumber;
    private String clientName;
    private String waiterName;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private Priority priority;
    private List<OrderItem> items;
    private String specialInstructions;
    private int estimatedPrepTime;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(String id, String tableId, int tableNumber, String clientName,
                 String waiterName, LocalDateTime createdAt, OrderStatus status,
                 Priority priority, List<OrderItem> items, String specialInstructions,
                 int estimatedPrepTime) {
        this.id = id;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.clientName = clientName;
        this.waiterName = waiterName;
        this.createdAt = createdAt;
        this.status = status;
        this.priority = priority;
        this.items = items != null ? items : new ArrayList<>();
        this.specialInstructions = specialInstructions;
        this.estimatedPrepTime = estimatedPrepTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTableId() { return tableId; }
    public void setTableId(String tableId) { this.tableId = tableId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getWaiterName() { return waiterName; }
    public void setWaiterName(String waiterName) { this.waiterName = waiterName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public int getEstimatedPrepTime() { return estimatedPrepTime; }
    public void setEstimatedPrepTime(int estimatedPrepTime) { this.estimatedPrepTime = estimatedPrepTime; }
}
