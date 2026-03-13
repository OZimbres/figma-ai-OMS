package com.restaurantos.model;

import com.restaurantos.model.enums.OrderStatus;
import com.restaurantos.model.enums.Priority;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

public class Order {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty tableId = new SimpleStringProperty();
    private final IntegerProperty tableNumber = new SimpleIntegerProperty();
    private final StringProperty clientName = new SimpleStringProperty();
    private final StringProperty waiterName = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();
    private final ObjectProperty<OrderStatus> status = new SimpleObjectProperty<>();
    private final ObjectProperty<Priority> priority = new SimpleObjectProperty<>();
    private final ObservableList<OrderItem> items = FXCollections.observableArrayList();
    private final StringProperty specialInstructions = new SimpleStringProperty();
    private final IntegerProperty estimatedPrepTime = new SimpleIntegerProperty();

    public Order(String id, String tableId, int tableNumber, String clientName, String waiterName,
                 LocalDateTime createdAt, OrderStatus status, Priority priority,
                 String specialInstructions, int estimatedPrepTime) {
        this.id.set(id);
        this.tableId.set(tableId);
        this.tableNumber.set(tableNumber);
        this.clientName.set(clientName);
        this.waiterName.set(waiterName);
        this.createdAt.set(createdAt);
        this.status.set(status);
        this.priority.set(priority);
        this.specialInstructions.set(specialInstructions);
        this.estimatedPrepTime.set(estimatedPrepTime);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public StringProperty tableIdProperty() { return tableId; }
    public IntegerProperty tableNumberProperty() { return tableNumber; }
    public StringProperty clientNameProperty() { return clientName; }
    public StringProperty waiterNameProperty() { return waiterName; }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }
    public ObjectProperty<OrderStatus> statusProperty() { return status; }
    public ObjectProperty<Priority> priorityProperty() { return priority; }
    public StringProperty specialInstructionsProperty() { return specialInstructions; }
    public IntegerProperty estimatedPrepTimeProperty() { return estimatedPrepTime; }

    // Getters
    public String getId() { return id.get(); }
    public String getTableId() { return tableId.get(); }
    public int getTableNumber() { return tableNumber.get(); }
    public String getClientName() { return clientName.get(); }
    public String getWaiterName() { return waiterName.get(); }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public OrderStatus getStatus() { return status.get(); }
    public Priority getPriority() { return priority.get(); }
    public ObservableList<OrderItem> getItems() { return items; }
    public String getSpecialInstructions() { return specialInstructions.get(); }
    public int getEstimatedPrepTime() { return estimatedPrepTime.get(); }

    // Setters
    public void setStatus(OrderStatus status) { this.status.set(status); }
    public void setPriority(Priority priority) { this.priority.set(priority); }

    public double getTotal() {
        return items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
}
