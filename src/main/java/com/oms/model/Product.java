package com.oms.model;

import com.oms.model.enums.ProductCategory;

public class Product {
    private String id;
    private String name;
    private ProductCategory category;
    private double price;
    private int prepTime;
    private boolean available;
    private String emoji;

    public Product() {}

    public Product(String id, String name, ProductCategory category, double price,
                   int prepTime, boolean available, String emoji) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.prepTime = prepTime;
        this.available = available;
        this.emoji = emoji;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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
