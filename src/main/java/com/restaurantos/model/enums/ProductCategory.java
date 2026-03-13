package com.restaurantos.model.enums;

public enum ProductCategory {
    DRINKS("Drinks"),
    PASTRIES("Pastries"),
    SANDWICHES("Sandwiches"),
    MEALS("Meals"),
    DESSERTS("Desserts");

    private final String label;

    ProductCategory(String label) { this.label = label; }

    public String getLabel() { return label; }
}
