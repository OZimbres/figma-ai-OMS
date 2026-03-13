package com.restaurantos.model.enums;

public enum PaymentMethod {
    CASH("Cash"),
    CARD("Card"),
    DIGITAL("Digital");

    private final String label;

    PaymentMethod(String label) { this.label = label; }

    public String getLabel() { return label; }
}
