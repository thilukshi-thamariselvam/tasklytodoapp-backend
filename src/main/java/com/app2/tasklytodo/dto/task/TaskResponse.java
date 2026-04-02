package com.app2.tasklytodo.dto.task;

import com.app2.tasklytodo.entity.enums.TaskPriority;
import com.app2.tasklytodo.entity.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;

    private TaskPriority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private TaskStatus status;

    private List<TaskResponse> subtasks;

    private Long projectId;
    private String projectName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
