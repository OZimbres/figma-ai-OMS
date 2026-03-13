package com.oms.service;

import com.oms.dto.UpdateTableRequest;
import com.oms.model.RestaurantTable;
import com.oms.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<RestaurantTable> getTableById(String id) {
        return tableRepository.findById(id);
    }

    public Optional<RestaurantTable> updateTable(String tableId, UpdateTableRequest request) {
        return tableRepository.findById(tableId).map(table -> {
            if (request.getStatus() != null) table.setStatus(request.getStatus());
            if (request.getGuests() != null) table.setGuests(request.getGuests());
            if (request.getOrderId() != null) table.setOrderId(request.getOrderId());
            if (request.getOccupiedSince() != null) table.setOccupiedSince(request.getOccupiedSince());
            return tableRepository.update(table);
        });
    }
}
