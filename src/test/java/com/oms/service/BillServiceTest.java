package com.oms.service;

import com.oms.dto.CreateBillRequest;
import com.oms.model.Bill;
import com.oms.model.enums.BillStatus;
import com.oms.model.enums.PaymentMethod;
import com.oms.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceTest {
    private BillService billService;
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        billRepository = new BillRepository();
        billService = new BillService(billRepository);

        billRepository.save(new Bill("b1", "t1", 1, "Maria Silva", "#1001",
                23.50, PaymentMethod.CARD, BillStatus.PENDING, LocalDateTime.now()));
    }

    @Test
    void getAllBills() {
        List<Bill> bills = billService.getAllBills();
        assertEquals(1, bills.size());
    }

    @Test
    void getBillById() {
        Optional<Bill> bill = billService.getBillById("b1");
        assertTrue(bill.isPresent());
        assertEquals("Maria Silva", bill.get().getClientName());
    }

    @Test
    void getBillByIdNotFound() {
        Optional<Bill> bill = billService.getBillById("nonexistent");
        assertTrue(bill.isEmpty());
    }

    @Test
    void addBill() {
        CreateBillRequest request = new CreateBillRequest();
        request.setTableId("t2");
        request.setTableNumber(2);
        request.setClientName("Joao Santos");
        request.setOrderId("#1002");
        request.setTotal(21.50);
        request.setPaymentMethod(PaymentMethod.CASH);

        Bill created = billService.addBill(request);
        assertNotNull(created.getId());
        assertEquals(BillStatus.PENDING, created.getStatus());
        assertEquals(21.50, created.getTotal());
        assertEquals(2, billService.getAllBills().size());
    }

    @Test
    void updateBillStatus() {
        Optional<Bill> updated = billService.updateBillStatus("b1", BillStatus.PAID);
        assertTrue(updated.isPresent());
        assertEquals(BillStatus.PAID, updated.get().getStatus());
    }

    @Test
    void updateBillStatusNotFound() {
        Optional<Bill> updated = billService.updateBillStatus("nonexistent", BillStatus.PAID);
        assertTrue(updated.isEmpty());
    }
}
