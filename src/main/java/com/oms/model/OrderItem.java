package com.oms.model;

import com.oms.model.enums.OrderItemStatus;

public class OrderItem {
    private String id;
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private OrderItemStatus status;
    private String notes;
    private int prepTime;

    public OrderItem() {}

    public OrderItem(String id, String productId, String productName, int quantity,
                     double price, OrderItemStatus status, String notes, int prepTime) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.notes = notes;
        this.prepTime = prepTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public OrderItemStatus getStatus() { return status; }
    public void setStatus(OrderItemStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
}
