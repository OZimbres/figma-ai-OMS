package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderItemStatus {
    @JsonProperty("pending") PENDING,
    @JsonProperty("preparing") PREPARING,
    @JsonProperty("ready") READY
}
