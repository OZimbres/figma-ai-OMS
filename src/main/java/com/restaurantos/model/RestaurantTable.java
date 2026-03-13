package com.restaurantos.model;

import com.restaurantos.model.enums.TableStatus;
import javafx.beans.property.*;
import java.time.LocalDateTime;

public class RestaurantTable {
    private final StringProperty id = new SimpleStringProperty();
    private final IntegerProperty number = new SimpleIntegerProperty();
    private final IntegerProperty seats = new SimpleIntegerProperty();
    private final IntegerProperty guests = new SimpleIntegerProperty();
    private final ObjectProperty<TableStatus> status = new SimpleObjectProperty<>();
    private final StringProperty orderId = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> occupiedSince = new SimpleObjectProperty<>();

    public RestaurantTable(String id, int number, int seats, int guests, TableStatus status, String orderId, LocalDateTime occupiedSince) {
        this.id.set(id);
        this.number.set(number);
        this.seats.set(seats);
        this.guests.set(guests);
        this.status.set(status);
        this.orderId.set(orderId);
        this.occupiedSince.set(occupiedSince);
    }

    // Property accessors
    public StringProperty idProperty() { return id; }
    public IntegerProperty numberProperty() { return number; }
    public IntegerProperty seatsProperty() { return seats; }
    public IntegerProperty guestsProperty() { return guests; }
    public ObjectProperty<TableStatus> statusProperty() { return status; }
    public StringProperty orderIdProperty() { return orderId; }
    public ObjectProperty<LocalDateTime> occupiedSinceProperty() { return occupiedSince; }

    // Getters
    public String getId() { return id.get(); }
    public int getNumber() { return number.get(); }
    public int getSeats() { return seats.get(); }
    public int getGuests() { return guests.get(); }
    public TableStatus getStatus() { return status.get(); }
    public String getOrderId() { return orderId.get(); }
    public LocalDateTime getOccupiedSince() { return occupiedSince.get(); }

    // Setters
    public void setGuests(int guests) { this.guests.set(guests); }
    public void setStatus(TableStatus status) { this.status.set(status); }
    public void setOrderId(String orderId) { this.orderId.set(orderId); }
    public void setOccupiedSince(LocalDateTime occupiedSince) { this.occupiedSince.set(occupiedSince); }
}
