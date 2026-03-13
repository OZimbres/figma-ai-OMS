package com.oms.repository;

import com.oms.model.Bill;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BillRepository {
    private final ConcurrentHashMap<String, Bill> bills = new ConcurrentHashMap<>();

    public List<Bill> findAll() {
        return new ArrayList<>(bills.values());
    }

    public Optional<Bill> findById(String id) {
        return Optional.ofNullable(bills.get(id));
    }

    public Bill save(Bill bill) {
        bills.put(bill.getId(), bill);
        return bill;
    }

    public Bill update(Bill bill) {
        bills.put(bill.getId(), bill);
        return bill;
    }

    public void delete(String id) {
        bills.remove(id);
    }
}
