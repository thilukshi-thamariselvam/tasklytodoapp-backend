package com.app2.tasklytodo.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectCreateRequest {

    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must be less than 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 7, message = "Color must be a valid hex code (e.g., #DC4C3E)")
    private String color;

    @NotBlank(message = "User ID is required")
    private String userId;
}
