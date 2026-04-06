package com.app2.tasklytodo.dto.task;

import com.app2.tasklytodo.entity.enums.TaskPriority;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskCreateRequest {

    @NotBlank(message = "Task title is required")
    @Size(max = 255, message = "Task title must be less than 255 characters")
    private String title;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    private TaskPriority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private Long projectId;

    private List<String> subtaskTitles;

    private List<Long> labelIds;

    @JsonIgnore
    private transient MultipartFile file;

    @NotBlank(message = "User ID is required")
    private String userId;
}
