package com.oms.dto;

import com.oms.model.enums.BillStatus;
import com.oms.model.enums.PaymentMethod;

public class CreateBillRequest {
    private String tableId;
    private int tableNumber;
    private String clientName;
    private String orderId;
    private double total;
    private PaymentMethod paymentMethod;

    public CreateBillRequest() {}

    public String getTableId() { return tableId; }
    public void setTableId(String tableId) { this.tableId = tableId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
}
