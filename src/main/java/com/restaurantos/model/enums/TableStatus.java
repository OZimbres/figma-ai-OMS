package com.restaurantos.model.enums;

public enum TableStatus {
    FREE("Free"),
    OCCUPIED("Occupied"),
    ORDERING("Ordering"),
    WAITING("Waiting"),
    PAY("Pay");

    private final String label;

    TableStatus(String label) { this.label = label; }

    public String getLabel() { return label; }
}
