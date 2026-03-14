package com.restaurantos.data;

import com.restaurantos.model.*;
import com.restaurantos.model.enums.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.UUID;

public final class DataStore {

    private static final DataStore INSTANCE = new DataStore();

    private final ObservableList<Order> orders;
    private final ObservableList<RestaurantTable> tables;
    private final ObservableList<Product> products;
    private final ObservableList<Client> clients;
    private final ObservableList<Bill> bills;
    private final StringProperty selectedOrderId = new SimpleStringProperty(null);

    private DataStore() {
        orders   = MockData.orders();
        tables   = MockData.tables();
        products = MockData.products();
        clients  = MockData.clients();
        bills    = MockData.bills();
    }

    public static DataStore getInstance() { return INSTANCE; }

    // ── Accessors ────────────────────────────────────────────────────────
    public ObservableList<Order> getOrders()   { return orders; }
    public ObservableList<RestaurantTable> getTables()   { return tables; }
    public ObservableList<Product> getProducts() { return products; }
    public ObservableList<Client> getClients()  { return clients; }
    public ObservableList<Bill> getBills()      { return bills; }

    public StringProperty selectedOrderIdProperty() { return selectedOrderId; }
    public String getSelectedOrderId() { return selectedOrderId.get(); }
    public void setSelectedOrderId(String id) { selectedOrderId.set(id); }

    public Order findOrder(String id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    // ── Order operations ─────────────────────────────────────────────────
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = findOrder(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            // Refresh list to trigger listeners
            int idx = orders.indexOf(order);
            if (idx >= 0) {
                orders.set(idx, order);
            }
        }
    }

    public void updateOrderPriority(String orderId, Priority newPriority) {
        Order order = findOrder(orderId);
        if (order != null) {
            order.setPriority(newPriority);
            int idx = orders.indexOf(order);
            if (idx >= 0) {
                orders.set(idx, order);
            }
        }
    }

    public void addItemToOrder(String orderId, Product product, int quantity, String notes) {
        Order order = findOrder(orderId);
        if (order != null) {
            String itemId = "oi-" + UUID.randomUUID().toString().substring(0, 8);
            OrderItem item = new OrderItem(itemId, product.getId(), product.getName(),
                    quantity, product.getPrice(), ItemStatus.PENDING, notes, product.getPrepTime());
            order.getItems().add(item);
            // Refresh
            int idx = orders.indexOf(order);
            if (idx >= 0) {
                orders.set(idx, order);
            }
        }
    }

    public void removeItemFromOrder(String orderId, String itemId) {
        Order order = findOrder(orderId);
        if (order != null) {
            order.getItems().removeIf(i -> i.getId().equals(itemId));
            int idx = orders.indexOf(order);
            if (idx >= 0) {
                orders.set(idx, order);
            }
        }
    }

    public Order createOrder(int tableNumber, String clientName, String waiterName, String specialInstructions) {
        String id = "ord-" + UUID.randomUUID().toString().substring(0, 8);
        RestaurantTable table = tables.stream()
                .filter(t -> t.getNumber() == tableNumber)
                .findFirst().orElse(null);
        String tableId = table != null ? table.getId() : "t" + tableNumber;

        Order order = new Order(id, tableId, tableNumber, clientName, waiterName,
                LocalDateTime.now(), OrderStatus.NEW, Priority.NORMAL,
                specialInstructions, 0);
        orders.add(order);

        if (table != null) {
            table.setStatus(TableStatus.ORDERING);
            table.setOrderId(id);
            table.setOccupiedSince(LocalDateTime.now());
            int idx = tables.indexOf(table);
            if (idx >= 0) {
                tables.set(idx, table);
            }
        }
        return order;
    }

    // ── Table operations ─────────────────────────────────────────────────
    public void updateTableStatus(String tableId, TableStatus newStatus) {
        tables.stream().filter(t -> t.getId().equals(tableId)).findFirst().ifPresent(table -> {
            table.setStatus(newStatus);
            if (newStatus == TableStatus.FREE) {
                table.setOrderId(null);
                table.setOccupiedSince(null);
                table.setGuests(0);
            }
            int idx = tables.indexOf(table);
            if (idx >= 0) {
                tables.set(idx, table);
            }
        });
    }

    // ── Product operations ───────────────────────────────────────────────
    public void updateProduct(Product updated) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(updated.getId())) {
                products.set(i, updated);
                break;
            }
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    // ── Bill operations ──────────────────────────────────────────────────
    public void updateBillStatus(String billId, BillStatus newStatus) {
        bills.stream().filter(b -> b.getId().equals(billId)).findFirst().ifPresent(bill -> {
            bill.setStatus(newStatus);
            int idx = bills.indexOf(bill);
            if (idx >= 0) {
                bills.set(idx, bill);
            }
        });
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }
}
