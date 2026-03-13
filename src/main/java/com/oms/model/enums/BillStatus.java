package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BillStatus {
    @JsonProperty("pending") PENDING,
    @JsonProperty("paid") PAID,
    @JsonProperty("cancelled") CANCELLED
}
