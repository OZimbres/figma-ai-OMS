package com.oms.service;

import com.oms.dto.UpdateTableRequest;
import com.oms.model.RestaurantTable;
import com.oms.model.enums.TableStatus;
import com.oms.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TableServiceTest {
    private TableService tableService;
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        tableRepository = new TableRepository();
        tableService = new TableService(tableRepository);

        tableRepository.save(new RestaurantTable("t1", 1, 4, 0, TableStatus.FREE, null, null));
        tableRepository.save(new RestaurantTable("t2", 2, 6, 4, TableStatus.OCCUPIED, "#1001", LocalDateTime.now()));
    }

    @Test
    void getAllTables() {
        List<RestaurantTable> tables = tableService.getAllTables();
        assertEquals(2, tables.size());
    }

    @Test
    void getTableById() {
        Optional<RestaurantTable> table = tableService.getTableById("t1");
        assertTrue(table.isPresent());
        assertEquals(1, table.get().getNumber());
    }

    @Test
    void getTableByIdNotFound() {
        Optional<RestaurantTable> table = tableService.getTableById("nonexistent");
        assertTrue(table.isEmpty());
    }

    @Test
    void updateTableStatus() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setStatus(TableStatus.OCCUPIED);
        request.setGuests(3);

        Optional<RestaurantTable> updated = tableService.updateTable("t1", request);
        assertTrue(updated.isPresent());
        assertEquals(TableStatus.OCCUPIED, updated.get().getStatus());
        assertEquals(3, updated.get().getGuests());
    }

    @Test
    void updateTablePartial() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setGuests(5);

        Optional<RestaurantTable> updated = tableService.updateTable("t2", request);
        assertTrue(updated.isPresent());
        assertEquals(5, updated.get().getGuests());
        assertEquals(TableStatus.OCCUPIED, updated.get().getStatus());
    }

    @Test
    void updateTableNotFound() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setStatus(TableStatus.FREE);

        Optional<RestaurantTable> updated = tableService.updateTable("nonexistent", request);
        assertTrue(updated.isEmpty());
    }
}
