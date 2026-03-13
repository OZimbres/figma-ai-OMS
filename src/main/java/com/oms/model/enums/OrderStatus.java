package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("new") NEW,
    @JsonProperty("sent") SENT,
    @JsonProperty("preparing") PREPARING,
    @JsonProperty("ready") READY,
    @JsonProperty("served") SERVED,
    @JsonProperty("completed") COMPLETED
}
