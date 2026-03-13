package com.oms.repository;

import com.oms.model.RestaurantTable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TableRepository {
    private final ConcurrentHashMap<String, RestaurantTable> tables = new ConcurrentHashMap<>();

    public List<RestaurantTable> findAll() {
        return new ArrayList<>(tables.values());
    }

    public Optional<RestaurantTable> findById(String id) {
        return Optional.ofNullable(tables.get(id));
    }

    public RestaurantTable save(RestaurantTable table) {
        tables.put(table.getId(), table);
        return table;
    }

    public RestaurantTable update(RestaurantTable table) {
        tables.put(table.getId(), table);
        return table;
    }

    public void delete(String id) {
        tables.remove(id);
    }
}
