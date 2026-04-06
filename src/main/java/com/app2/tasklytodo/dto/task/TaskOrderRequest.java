package com.app2.tasklytodo.dto.task;

import lombok.Data;

@Data
public class TaskOrderRequest {
    private Long id;
    private Integer displayOrder;
}
