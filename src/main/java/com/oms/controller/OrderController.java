package com.oms.controller;

import com.oms.dto.AddItemToOrderRequest;
import com.oms.dto.CreateOrderRequest;
import com.oms.dto.UpdateOrderPriorityRequest;
import com.oms.dto.UpdateOrderStatusRequest;
import com.oms.model.Order;
import com.oms.model.OrderItem;
import com.oms.model.enums.OrderItemStatus;
import com.oms.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(
                request.getTableId(), request.getTableNumber(),
                request.getClientName(), request.getWaiterName());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String id,
                                                   @RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Order> updateOrderPriority(@PathVariable String id,
                                                     @RequestBody UpdateOrderPriorityRequest request) {
        return orderService.updateOrderPriority(id, request.getPriority())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Order> addItemToOrder(@PathVariable String id,
                                                @RequestBody AddItemToOrderRequest request) {
        OrderItem item = new OrderItem(
                UUID.randomUUID().toString(),
                request.getProductId(),
                request.getProductName(),
                request.getQuantity(),
                request.getPrice(),
                OrderItemStatus.PENDING,
                request.getNotes(),
                request.getPrepTime()
        );
        return orderService.addItemToOrder(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Order> removeItemFromOrder(@PathVariable String id,
                                                     @PathVariable String itemId) {
        return orderService.removeItemFromOrder(id, itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
