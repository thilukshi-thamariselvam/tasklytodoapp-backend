package com.app2.tasklytodo.service;

import com.app2.tasklytodo.dto.task.TaskCreateRequest;
import com.app2.tasklytodo.dto.task.TaskResponse;
import com.app2.tasklytodo.dto.task.TaskUpdateRequest;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskCreateRequest request);

    List<TaskResponse> getTasksByUser(String userId);

    TaskResponse updateTask(Long taskId, TaskUpdateRequest request);

    void deleteTask(Long taskId);

    TaskResponse completeTask(Long taskId);

    TaskResponse getTaskById(Long taskId);

    List<TaskResponse> searchTasks(String userId, String query);
}