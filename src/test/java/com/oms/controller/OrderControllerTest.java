package com.oms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.dto.CreateOrderRequest;
import com.oms.dto.UpdateOrderStatusRequest;
import com.oms.dto.AddItemToOrderRequest;
import com.oms.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getOrderById() throws Exception {
        mockMvc.perform(get(new URI("/api/orders/%231001")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("#1001"));
    }

    @Test
    void getOrderByIdNotFound() throws Exception {
        mockMvc.perform(get(new URI("/api/orders/nonexistent")))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setTableId("t4");
        request.setTableNumber(4);
        request.setClientName("Test Client");
        request.setWaiterName("Test Waiter");

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("Test Client"))
                .andExpect(jsonPath("$.status").value("new"));
    }

    @Test
    void updateOrderStatus() throws Exception {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.PREPARING);

        mockMvc.perform(put(new URI("/api/orders/%231001/status"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("preparing"));
    }

    @Test
    void addItemToOrder() throws Exception {
        AddItemToOrderRequest request = new AddItemToOrderRequest();
        request.setProductId("p1");
        request.setProductName("Espresso");
        request.setQuantity(1);
        request.setPrice(2.50);
        request.setPrepTime(2);
        request.setNotes("Extra hot");

        mockMvc.perform(post(new URI("/api/orders/%231001/items"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    void getDashboard() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeOrders").isNumber());
    }

    @Test
    void getRevenueReport() throws Exception {
        mockMvc.perform(get("/api/reports/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
