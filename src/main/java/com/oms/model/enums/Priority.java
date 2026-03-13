package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Priority {
    @JsonProperty("normal") NORMAL,
    @JsonProperty("high") HIGH,
    @JsonProperty("critical") CRITICAL
}
