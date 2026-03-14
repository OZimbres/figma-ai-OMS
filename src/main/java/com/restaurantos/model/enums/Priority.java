package com.restaurantos.model.enums;

public enum Priority {
    NORMAL("Normal"),
    HIGH("High"),
    CRITICAL("Critical");

    private final String label;

    Priority(String label) { this.label = label; }

    public String getLabel() { return label; }
}
