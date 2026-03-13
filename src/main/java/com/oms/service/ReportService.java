package com.oms.service;

import com.oms.dto.PrepTimeData;
import com.oms.dto.RevenueDataPoint;
import com.oms.dto.TopProductData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private List<RevenueDataPoint> revenueData;
    private List<TopProductData> topProducts;
    private List<PrepTimeData> prepTimeData;

    public void setRevenueData(List<RevenueDataPoint> revenueData) {
        this.revenueData = revenueData;
    }

    public void setTopProducts(List<TopProductData> topProducts) {
        this.topProducts = topProducts;
    }

    public void setPrepTimeData(List<PrepTimeData> prepTimeData) {
        this.prepTimeData = prepTimeData;
    }

    public List<RevenueDataPoint> getRevenueData() {
        return revenueData;
    }

    public List<TopProductData> getTopProducts() {
        return topProducts;
    }

    public List<PrepTimeData> getPrepTimeData() {
        return prepTimeData;
    }
}
