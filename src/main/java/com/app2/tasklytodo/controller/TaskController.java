package com.app2.tasklytodo.controller;

import com.app2.tasklytodo.dto.ApiResponse;
import com.app2.tasklytodo.dto.task.TaskCreateRequest;
import com.app2.tasklytodo.dto.task.TaskResponse;
import com.app2.tasklytodo.dto.task.TaskUpdateRequest;
import com.app2.tasklytodo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask
            (@RequestPart("task") @Valid TaskCreateRequest request,
             @RequestPart(value = "file", required = false) MultipartFile file
            ) {
        request.setFile(file);
        log.info("Received request to create task with file: {}", file != null ?
                file.getOriginalFilename() : "none");

        TaskResponse response = taskService.createTask(request);
        return ApiResponse.created("Task created successfully", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long id) {
        log.info("Received request to fetch task with id: {}", id);
        TaskResponse response = taskService.getTaskById(id);
        return ApiResponse.success("Task retrieved successfully", response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasks(@RequestParam String userId) {
        log.info("Received request to fetch all tasks for user: {}", userId);
        List<TaskResponse> responses = taskService.getTasksByUser(userId);
        return ApiResponse.success("Tasks retrieved successfully", responses);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> searchTasks(
            @RequestParam String userId,
            @RequestParam String query) {
        log.info("Received search request from user: {} for query: {}", userId, query);
        List<TaskResponse> responses = taskService.searchTasks(userId, query);
        return ApiResponse.success("Tasks retrieved successfully", responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {
        log.info("Received request to update task with id: {}", id);
        TaskResponse response = taskService.updateTask(id, request);
        return ApiResponse.success("Task updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        log.info("Received request to delete task with id: {}", id);
        taskService.deleteTask(id);
        return ApiResponse.deleted("Task deleted successfully");
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<TaskResponse>> completeTask(@PathVariable Long id) {
        log.info("Received request to complete task with id: {}", id);
        TaskResponse response = taskService.completeTask(id);
        return ApiResponse.success("Task completed successfully", response);
    }

    @PatchMapping(value = "/{id}/attachment", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        log.info("Received request to update attachment for task id: {}", id);
        TaskResponse response = taskService.updateTaskAttachment(id, file);
        return ApiResponse.success("Attachment updated successfully", response);
    }
}