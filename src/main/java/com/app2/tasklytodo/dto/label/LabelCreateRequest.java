package com.app2.tasklytodo.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LabelCreateRequest {

    @NotBlank(message = "Label name is required")
    @Size(max = 50, message = "Label name cannot exceed 50 characters")
    private String name;

    private String color;

    private String userId;
}
