package com.restaurantos.model.enums;

public enum ItemStatus {
    PENDING("Pending"),
    PREPARING("Preparing"),
    READY("Ready");

    private final String label;

    ItemStatus(String label) { this.label = label; }

    public String getLabel() { return label; }
}
