package com.restaurantos.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Client {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty visits = new SimpleIntegerProperty();
    private final DoubleProperty totalSpending = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDateTime> lastOrderDate = new SimpleObjectProperty<>();

    public Client(String id, String name, String phone, String email, int visits, double totalSpending, LocalDateTime lastOrderDate) {
        this.id.set(id);
        this.name.set(name);
        this.phone.set(phone);
        this.email.set(email);
        this.visits.set(visits);
        this.totalSpending.set(totalSpending);
        this.lastOrderDate.set(lastOrderDate);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty emailProperty() { return email; }
    public IntegerProperty visitsProperty() { return visits; }
    public DoubleProperty totalSpendingProperty() { return totalSpending; }
    public ObjectProperty<LocalDateTime> lastOrderDateProperty() { return lastOrderDate; }

    // Getters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getPhone() { return phone.get(); }
    public String getEmail() { return email.get(); }
    public int getVisits() { return visits.get(); }
    public double getTotalSpending() { return totalSpending.get(); }
    public LocalDateTime getLastOrderDate() { return lastOrderDate.get(); }

    public String getLoyaltyTier() {
        if (visits.get() >= 50) return "Platinum";
        if (visits.get() >= 30) return "Gold";
        if (visits.get() >= 15) return "Silver";
        return "Bronze";
    }

    public boolean isVip() { return visits.get() >= 30; }
}
