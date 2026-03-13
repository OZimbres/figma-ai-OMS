package com.oms.dto;

import com.oms.model.enums.Priority;

public class UpdateOrderPriorityRequest {
    private Priority priority;

    public UpdateOrderPriorityRequest() {}

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
}
