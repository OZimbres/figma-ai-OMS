package com.restaurantos.model.enums;

public enum OrderStatus {
    NEW("New"),
    SENT("Sent"),
    PREPARING("Preparing"),
    READY("Ready"),
    SERVED("Served"),
    COMPLETED("Completed");

    private final String label;

    OrderStatus(String label) { this.label = label; }

    public String getLabel() { return label; }
}
