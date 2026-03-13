package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableStatus {
    @JsonProperty("free") FREE,
    @JsonProperty("occupied") OCCUPIED,
    @JsonProperty("ordering") ORDERING,
    @JsonProperty("waiting") WAITING,
    @JsonProperty("pay") PAY
}
