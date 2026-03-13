package com.oms.service;

import com.oms.dto.DashboardResponse;
import com.oms.model.Order;
import com.oms.model.RestaurantTable;
import com.oms.model.enums.OrderStatus;
import com.oms.model.enums.TableStatus;
import com.oms.repository.OrderRepository;
import com.oms.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;

    public DashboardService(OrderRepository orderRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    public DashboardResponse getDashboardStats() {
        List<Order> orders = orderRepository.findAll();
        List<RestaurantTable> tables = tableRepository.findAll();

        long activeOrders = orders.stream()
                .filter(o -> o.getStatus() != OrderStatus.COMPLETED)
                .count();
        long preparing = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.PREPARING)
                .count();
        long readyToServe = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.READY)
                .count();
        long completedToday = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .count();

        long totalTables = tables.size();
        long occupiedTables = tables.stream()
                .filter(t -> t.getStatus() != TableStatus.FREE)
                .count();
        double tableOccupancyPercent = totalTables > 0
                ? (double) occupiedTables / totalTables * 100.0
                : 0.0;

        double avgPrepTime = orders.stream()
                .filter(o -> o.getEstimatedPrepTime() > 0)
                .mapToInt(Order::getEstimatedPrepTime)
                .average()
                .orElse(0.0);

        return new DashboardResponse(activeOrders, preparing, readyToServe,
                completedToday, tableOccupancyPercent, avgPrepTime);
    }
}
