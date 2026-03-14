package com.restaurantos.model;

import com.restaurantos.model.enums.ProductCategory;
import javafx.beans.property.*;

public class Product {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<ProductCategory> category = new SimpleObjectProperty<>();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty prepTime = new SimpleIntegerProperty();
    private final BooleanProperty available = new SimpleBooleanProperty();
    private final StringProperty emoji = new SimpleStringProperty();

    public Product(String id, String name, ProductCategory category, double price, int prepTime, boolean available, String emoji) {
        this.id.set(id);
        this.name.set(name);
        this.category.set(category);
        this.price.set(price);
        this.prepTime.set(prepTime);
        this.available.set(available);
        this.emoji.set(emoji);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<ProductCategory> categoryProperty() { return category; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty prepTimeProperty() { return prepTime; }
    public BooleanProperty availableProperty() { return available; }
    public StringProperty emojiProperty() { return emoji; }

    // Getters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public ProductCategory getCategory() { return category.get(); }
    public double getPrice() { return price.get(); }
    public int getPrepTime() { return prepTime.get(); }
    public boolean isAvailable() { return available.get(); }
    public String getEmoji() { return emoji.get(); }

    // Setters
    public void setName(String name) { this.name.set(name); }
    public void setCategory(ProductCategory category) { this.category.set(category); }
    public void setPrice(double price) { this.price.set(price); }
    public void setPrepTime(int prepTime) { this.prepTime.set(prepTime); }
    public void setAvailable(boolean available) { this.available.set(available); }
    public void setEmoji(String emoji) { this.emoji.set(emoji); }
}
