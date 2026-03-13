package com.oms.controller;

import com.oms.dto.PrepTimeData;
import com.oms.dto.RevenueDataPoint;
import com.oms.dto.TopProductData;
import com.oms.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueDataPoint>> getRevenueData() {
        return ResponseEntity.ok(reportService.getRevenueData());
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductData>> getTopProducts() {
        return ResponseEntity.ok(reportService.getTopProducts());
    }

    @GetMapping("/prep-times")
    public ResponseEntity<List<PrepTimeData>> getPrepTimeData() {
        return ResponseEntity.ok(reportService.getPrepTimeData());
    }
}
