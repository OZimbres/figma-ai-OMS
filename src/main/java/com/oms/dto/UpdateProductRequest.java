package com.oms.dto;

import com.oms.model.enums.ProductCategory;

public class UpdateProductRequest {
    private String name;
    private ProductCategory category;
    private Double price;
    private Integer prepTime;
    private Boolean available;
    private String emoji;

    public UpdateProductRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getPrepTime() { return prepTime; }
    public void setPrepTime(Integer prepTime) { this.prepTime = prepTime; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
}
