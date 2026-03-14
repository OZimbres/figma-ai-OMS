package com.restaurantos.model;

import com.restaurantos.model.enums.ItemStatus;
import javafx.beans.property.*;

public class OrderItem {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty productId = new SimpleStringProperty();
    private final StringProperty productName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final ObjectProperty<ItemStatus> status = new SimpleObjectProperty<>();
    private final StringProperty notes = new SimpleStringProperty();
    private final IntegerProperty prepTime = new SimpleIntegerProperty();

    public OrderItem(String id, String productId, String productName, int quantity, double price, ItemStatus status, String notes, int prepTime) {
        this.id.set(id);
        this.productId.set(productId);
        this.productName.set(productName);
        this.quantity.set(quantity);
        this.price.set(price);
        this.status.set(status);
        this.notes.set(notes);
        this.prepTime.set(prepTime);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public StringProperty productIdProperty() { return productId; }
    public StringProperty productNameProperty() { return productName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty priceProperty() { return price; }
    public ObjectProperty<ItemStatus> statusProperty() { return status; }
    public StringProperty notesProperty() { return notes; }
    public IntegerProperty prepTimeProperty() { return prepTime; }

    // Getters
    public String getId() { return id.get(); }
    public String getProductId() { return productId.get(); }
    public String getProductName() { return productName.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getPrice() { return price.get(); }
    public ItemStatus getStatus() { return status.get(); }
    public String getNotes() { return notes.get(); }
    public int getPrepTime() { return prepTime.get(); }

    // Setters
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setStatus(ItemStatus status) { this.status.set(status); }
}
