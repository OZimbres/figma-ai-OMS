package com.oms.repository;

import com.oms.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public Order update(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public void delete(String id) {
        orders.remove(id);
    }
}
