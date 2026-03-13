package com.oms.dto;

import com.oms.model.enums.BillStatus;

public class UpdateBillStatusRequest {
    private BillStatus status;

    public UpdateBillStatusRequest() {}

    public BillStatus getStatus() { return status; }
    public void setStatus(BillStatus status) { this.status = status; }
}
