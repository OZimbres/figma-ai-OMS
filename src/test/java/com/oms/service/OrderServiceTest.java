package com.oms.service;

import com.oms.model.Order;
import com.oms.model.OrderItem;
import com.oms.model.enums.OrderItemStatus;
import com.oms.model.enums.OrderStatus;
import com.oms.model.enums.Priority;
import com.oms.model.enums.TableStatus;
import com.oms.model.RestaurantTable;
import com.oms.repository.OrderRepository;
import com.oms.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        tableRepository = new TableRepository();
        orderService = new OrderService(orderRepository, tableRepository);

        tableRepository.save(new RestaurantTable("t1", 1, 4, 2,
                TableStatus.OCCUPIED, null, LocalDateTime.now()));
    }

    @Test
    void createOrder() {
        Order order = orderService.createOrder("t1", 1, "Test Client", "Test Waiter");
        assertNotNull(order);
        assertNotNull(order.getId());
        assertTrue(order.getId().startsWith("#"));
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertEquals(Priority.NORMAL, order.getPriority());
        assertEquals("Test Client", order.getClientName());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void getAllOrders() {
        orderService.createOrder("t1", 1, "Client A", "Waiter A");
        orderService.createOrder("t1", 1, "Client B", "Waiter B");
        List<Order> orders = orderService.getAllOrders();
        assertEquals(2, orders.size());
    }

    @Test
    void getOrderById() {
        Order created = orderService.createOrder("t1", 1, "Client", "Waiter");
        Optional<Order> found = orderService.getOrderById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created.getId(), found.get().getId());
    }

    @Test
    void getOrderByIdNotFound() {
        Optional<Order> found = orderService.getOrderById("nonexistent");
        assertTrue(found.isEmpty());
    }

    @Test
    void updateOrderStatus() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        order.setItems(new ArrayList<>(List.of(
                new OrderItem("i1", "p1", "Espresso", 1, 2.50, OrderItemStatus.PENDING, null, 2)
        )));
        orderRepository.update(order);

        Optional<Order> updated = orderService.updateOrderStatus(order.getId(), OrderStatus.PREPARING);
        assertTrue(updated.isPresent());
        assertEquals(OrderStatus.PREPARING, updated.get().getStatus());
        assertEquals(OrderItemStatus.PREPARING, updated.get().getItems().get(0).getStatus());
    }

    @Test
    void updateOrderStatusToReady() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        order.setItems(new ArrayList<>(List.of(
                new OrderItem("i1", "p1", "Espresso", 1, 2.50, OrderItemStatus.PREPARING, null, 2)
        )));
        orderRepository.update(order);

        Optional<Order> updated = orderService.updateOrderStatus(order.getId(), OrderStatus.READY);
        assertTrue(updated.isPresent());
        assertEquals(OrderStatus.READY, updated.get().getStatus());
        assertEquals(OrderItemStatus.READY, updated.get().getItems().get(0).getStatus());
    }

    @Test
    void updateOrderStatusUpdatesTable() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        orderService.updateOrderStatus(order.getId(), OrderStatus.COMPLETED);

        RestaurantTable table = tableRepository.findById("t1").orElseThrow();
        assertEquals(TableStatus.PAY, table.getStatus());
    }

    @Test
    void updateOrderPriority() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        Optional<Order> updated = orderService.updateOrderPriority(order.getId(), Priority.HIGH);
        assertTrue(updated.isPresent());
        assertEquals(Priority.HIGH, updated.get().getPriority());
    }

    @Test
    void addItemToOrder() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        OrderItem item = new OrderItem("i1", "p1", "Espresso", 1, 2.50, OrderItemStatus.PENDING, null, 2);

        Optional<Order> updated = orderService.addItemToOrder(order.getId(), item);
        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getItems().size());
        assertEquals(2, updated.get().getEstimatedPrepTime());
    }

    @Test
    void addDuplicateItemIncrementsQuantity() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        OrderItem item1 = new OrderItem("i1", "p1", "Espresso", 1, 2.50, OrderItemStatus.PENDING, null, 2);
        OrderItem item2 = new OrderItem("i2", "p1", "Espresso", 2, 2.50, OrderItemStatus.PENDING, null, 2);

        orderService.addItemToOrder(order.getId(), item1);
        Optional<Order> updated = orderService.addItemToOrder(order.getId(), item2);

        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getItems().size());
        assertEquals(3, updated.get().getItems().get(0).getQuantity());
    }

    @Test
    void removeItemFromOrder() {
        Order order = orderService.createOrder("t1", 1, "Client", "Waiter");
        OrderItem item = new OrderItem("i1", "p1", "Espresso", 1, 2.50, OrderItemStatus.PENDING, null, 2);
        orderService.addItemToOrder(order.getId(), item);

        Optional<Order> updated = orderService.removeItemFromOrder(order.getId(), "i1");
        assertTrue(updated.isPresent());
        assertTrue(updated.get().getItems().isEmpty());
        assertEquals(0, updated.get().getEstimatedPrepTime());
    }
}
