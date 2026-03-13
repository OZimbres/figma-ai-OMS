package com.oms.service;

import com.oms.dto.CreateBillRequest;
import com.oms.model.Bill;
import com.oms.model.enums.BillStatus;
import com.oms.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(String id) {
        return billRepository.findById(id);
    }

    public Bill addBill(CreateBillRequest request) {
        Bill bill = new Bill(
                UUID.randomUUID().toString(),
                request.getTableId(),
                request.getTableNumber(),
                request.getClientName(),
                request.getOrderId(),
                request.getTotal(),
                request.getPaymentMethod(),
                BillStatus.PENDING,
                LocalDateTime.now()
        );
        return billRepository.save(bill);
    }

    public Optional<Bill> updateBillStatus(String id, BillStatus status) {
        return billRepository.findById(id).map(bill -> {
            bill.setStatus(status);
            return billRepository.update(bill);
        });
    }
}
