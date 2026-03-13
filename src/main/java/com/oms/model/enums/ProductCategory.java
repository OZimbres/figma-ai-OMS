package com.oms.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductCategory {
    @JsonProperty("drinks") DRINKS,
    @JsonProperty("pastries") PASTRIES,
    @JsonProperty("sandwiches") SANDWICHES,
    @JsonProperty("meals") MEALS,
    @JsonProperty("desserts") DESSERTS
}
