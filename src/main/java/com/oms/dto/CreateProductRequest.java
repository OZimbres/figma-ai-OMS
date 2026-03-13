package com.oms.dto;

import com.oms.model.enums.ProductCategory;

public class CreateProductRequest {
    private String name;
    private ProductCategory category;
    private double price;
    private int prepTime;
    private boolean available;
    private String emoji;

    public CreateProductRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
}
