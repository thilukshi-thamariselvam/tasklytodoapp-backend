package com.app2.tasklytodo.service.impl;

import com.app2.tasklytodo.dto.task.TaskCreateRequest;
import com.app2.tasklytodo.dto.task.TaskResponse;
import com.app2.tasklytodo.dto.task.TaskUpdateRequest;
import com.app2.tasklytodo.entity.Label;
import com.app2.tasklytodo.entity.Project;
import com.app2.tasklytodo.entity.Task;
import com.app2.tasklytodo.entity.User;
import com.app2.tasklytodo.entity.enums.TaskPriority;
import com.app2.tasklytodo.entity.enums.TaskStatus;
import com.app2.tasklytodo.exception.BadRequestException;
import com.app2.tasklytodo.exception.ResourceNotFoundException;
import com.app2.tasklytodo.mapper.TaskMapper;
import com.app2.tasklytodo.repository.LabelRepository;
import com.app2.tasklytodo.repository.ProjectRepository;
import com.app2.tasklytodo.repository.TaskRepository;
import com.app2.tasklytodo.repository.UserRepository;
import com.app2.tasklytodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final LabelRepository labelRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        log.info("Creating task: {} for user: {}", request.getTitle(), request.getUserId());

        User user = userRepository.findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(TaskStatus.PENDING)
                .priority(request.getPriority() != null ? request.getPriority() : TaskPriority.LOW)
                .user(user)
                .project(project)
                .build();

        if (request.getLabelIds() != null && !request.getLabelIds().isEmpty()) {
            Set<Label> labels = new HashSet<>(labelRepository.findAllById(request.getLabelIds()));
            task.setLabels(labels);
        }

        Task savedTask = taskRepository.save(task);

        if (request.getSubtaskTitles() != null && !request.getSubtaskTitles().isEmpty()) {
            List<Task> newSubtasks = new ArrayList<>();

            for (String subtaskTitle : request.getSubtaskTitles()) {
                Task subtask = Task.builder()
                        .title(subtaskTitle)
                        .status(TaskStatus.PENDING)
                        .priority(task.getPriority())
                        .user(user)
                        .parentTask(savedTask)
                        .build();

                newSubtasks.add(subtask);
            }

            savedTask.setSubtasks(newSubtasks);
            savedTask = taskRepository.save(savedTask);
        }

        return taskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByUser(String userId) {
        log.info("Fetching all tasks for user: {}", userId);
        List<Task> tasks = taskRepository.findByUserIdAndParentTaskIsNullAndDeletedAtIsNullOrderByDueDateAscCreatedAtDesc(Long.valueOf(userId));
        return taskMapper.toResponseList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long taskId) {
        log.info("Fetching task with id: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (task.getDeletedAt() != null) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }

        return taskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> searchTasks(String userId, String query) {
        log.info("Searching tasks for user: {} with query: {}", userId, query);
        List<Task> tasks = taskRepository.findByUserIdAndTitleContainingIgnoreCaseAndDeletedAtIsNullAndParentTaskIsNullOrderByDueDateAscCreatedAtDesc(Long.valueOf(userId), query);
        return taskMapper.toResponseList(tasks);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskUpdateRequest request) {
        log.info("Updating task with id: {}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getSubtaskTitles() != null) {
            if (task.getSubtasks() == null) {
                task.setSubtasks(new ArrayList<>());
            } else {
                task.getSubtasks().clear();
            }
            for (String subtaskTitle : request.getSubtaskTitles()) {
                Task subtask = Task.builder()
                        .title(subtaskTitle)
                        .status(TaskStatus.PENDING)
                        .priority(task.getPriority())
                        .user(task.getUser())
                        .parentTask(task)
                        .build();
                task.getSubtasks().add(subtask);
            }
        }

        if (request.getLabelIds() != null) {
            Set<Label> labels = new HashSet<>(labelRepository.findAllById(request.getLabelIds()));
            task.setLabels(labels);
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        log.info("Soft deleting task with id: {}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (task.getDeletedAt() != null) {
            throw new BadRequestException("Task is already deleted");
        }

        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public TaskResponse completeTask(Long taskId) {
        log.info("Completing task with id: {}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setStatus(TaskStatus.COMPLETED);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }
}