package com.restaurantos.model;

import com.restaurantos.model.enums.BillStatus;
import com.restaurantos.model.enums.PaymentMethod;
import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Bill {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty tableId = new SimpleStringProperty();
    private final IntegerProperty tableNumber = new SimpleIntegerProperty();
    private final StringProperty clientName = new SimpleStringProperty();
    private final StringProperty orderId = new SimpleStringProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private final ObjectProperty<PaymentMethod> paymentMethod = new SimpleObjectProperty<>();
    private final ObjectProperty<BillStatus> status = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();

    public Bill(String id, String tableId, int tableNumber, String clientName, String orderId,
                double total, PaymentMethod paymentMethod, BillStatus status, LocalDateTime createdAt) {
        this.id.set(id);
        this.tableId.set(tableId);
        this.tableNumber.set(tableNumber);
        this.clientName.set(clientName);
        this.orderId.set(orderId);
        this.total.set(total);
        this.paymentMethod.set(paymentMethod);
        this.status.set(status);
        this.createdAt.set(createdAt);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public StringProperty tableIdProperty() { return tableId; }
    public IntegerProperty tableNumberProperty() { return tableNumber; }
    public StringProperty clientNameProperty() { return clientName; }
    public StringProperty orderIdProperty() { return orderId; }
    public DoubleProperty totalProperty() { return total; }
    public ObjectProperty<PaymentMethod> paymentMethodProperty() { return paymentMethod; }
    public ObjectProperty<BillStatus> statusProperty() { return status; }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    // Getters
    public String getId() { return id.get(); }
    public String getTableId() { return tableId.get(); }
    public int getTableNumber() { return tableNumber.get(); }
    public String getClientName() { return clientName.get(); }
    public String getOrderId() { return orderId.get(); }
    public double getTotal() { return total.get(); }
    public PaymentMethod getPaymentMethod() { return paymentMethod.get(); }
    public BillStatus getStatus() { return status.get(); }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }

    // Setters
    public void setStatus(BillStatus status) { this.status.set(status); }
    public void setPaymentMethod(PaymentMethod method) { this.paymentMethod.set(method); }
}
