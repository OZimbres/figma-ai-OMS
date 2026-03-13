package com.oms.controller;

import com.oms.dto.CreateBillRequest;
import com.oms.dto.UpdateBillStatusRequest;
import com.oms.model.Bill;
import com.oms.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable String id) {
        return billService.getBillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bill> addBill(@RequestBody CreateBillRequest request) {
        return ResponseEntity.ok(billService.addBill(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Bill> updateBillStatus(@PathVariable String id,
                                                 @RequestBody UpdateBillStatusRequest request) {
        return billService.updateBillStatus(id, request.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
