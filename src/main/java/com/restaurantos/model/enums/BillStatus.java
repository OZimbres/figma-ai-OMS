package com.restaurantos.model.enums;

public enum BillStatus {
    PENDING("Pending"),
    PAID("Paid"),
    CANCELLED("Cancelled");

    private final String label;

    BillStatus(String label) { this.label = label; }

    public String getLabel() { return label; }
}
