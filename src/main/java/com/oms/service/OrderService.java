package com.oms.service;

import com.oms.model.Order;
import com.oms.model.OrderItem;
import com.oms.model.RestaurantTable;
import com.oms.model.enums.OrderItemStatus;
import com.oms.model.enums.OrderStatus;
import com.oms.model.enums.Priority;
import com.oms.model.enums.TableStatus;
import com.oms.repository.OrderRepository;
import com.oms.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;
    private final AtomicInteger orderCounter = new AtomicInteger(1100);

    public OrderService(OrderRepository orderRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(String tableId, int tableNumber, String clientName, String waiterName) {
        String id = "#" + orderCounter.getAndIncrement();
        Order order = new Order(id, tableId, tableNumber, clientName, waiterName,
                LocalDateTime.now(), OrderStatus.NEW, Priority.NORMAL,
                new ArrayList<>(), null, 0);
        return orderRepository.save(order);
    }

    public Optional<Order> updateOrderStatus(String orderId, OrderStatus status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            // Update item statuses based on order status
            if (status == OrderStatus.PREPARING) {
                order.getItems().forEach(item -> item.setStatus(OrderItemStatus.PREPARING));
            } else if (status == OrderStatus.READY) {
                order.getItems().forEach(item -> item.setStatus(OrderItemStatus.READY));
            }
            // Update linked table status
            tableRepository.findById(order.getTableId()).ifPresent(table -> {
                if (status == OrderStatus.PREPARING) {
                    table.setStatus(TableStatus.WAITING);
                } else if (status == OrderStatus.READY || status == OrderStatus.SERVED) {
                    table.setStatus(TableStatus.OCCUPIED);
                } else if (status == OrderStatus.COMPLETED) {
                    table.setStatus(TableStatus.PAY);
                }
                tableRepository.update(table);
            });
            return orderRepository.update(order);
        });
    }

    public Optional<Order> updateOrderPriority(String orderId, Priority priority) {
        return orderRepository.findById(orderId).map(order -> {
            order.setPriority(priority);
            return orderRepository.update(order);
        });
    }

    public Optional<Order> addItemToOrder(String orderId, OrderItem item) {
        return orderRepository.findById(orderId).map(order -> {
            // If same product exists, increment quantity
            Optional<OrderItem> existing = order.getItems().stream()
                    .filter(i -> i.getProductId().equals(item.getProductId()))
                    .findFirst();
            if (existing.isPresent()) {
                existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
            } else {
                order.getItems().add(item);
            }
            // Recalculate estimated prep time
            int maxPrepTime = order.getItems().stream()
                    .mapToInt(OrderItem::getPrepTime)
                    .max().orElse(0);
            order.setEstimatedPrepTime(maxPrepTime);
            return orderRepository.update(order);
        });
    }

    public Optional<Order> removeItemFromOrder(String orderId, String itemId) {
        return orderRepository.findById(orderId).map(order -> {
            order.getItems().removeIf(item -> item.getId().equals(itemId));
            int maxPrepTime = order.getItems().stream()
                    .mapToInt(OrderItem::getPrepTime)
                    .max().orElse(0);
            order.setEstimatedPrepTime(maxPrepTime);
            return orderRepository.update(order);
        });
    }
}
