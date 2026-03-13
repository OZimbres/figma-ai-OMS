package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethod {
    @JsonProperty("cash") CASH,
    @JsonProperty("card") CARD,
    @JsonProperty("digital") DIGITAL
}
