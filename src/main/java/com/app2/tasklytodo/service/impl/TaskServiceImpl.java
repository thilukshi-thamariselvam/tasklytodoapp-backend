package com.app2.tasklytodo.service.impl;

import com.app2.tasklytodo.dto.task.TaskCreateRequest;
import com.app2.tasklytodo.dto.task.TaskResponse;
import com.app2.tasklytodo.entity.Project;
import com.app2.tasklytodo.entity.Task;
import com.app2.tasklytodo.entity.User;
import com.app2.tasklytodo.entity.enums.TaskStatus;
import com.app2.tasklytodo.mapper.TaskMapper;
import com.app2.tasklytodo.repository.ProjectRepository;
import com.app2.tasklytodo.repository.TaskRepository;
import com.app2.tasklytodo.repository.UserRepository;
import com.app2.tasklytodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        log.info("Creating task: {} for user: {}", request.getTitle(), request.getUserId());

        User user = userRepository.findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(TaskStatus.PENDING)
                .user(user)
                .project(project)
                .build();

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByUser(String userId) {
        log.info("Fetching all tasks for user: {}", userId);
        List<Task> tasks = taskRepository.findByUserIdOrderByDueDateAscCreatedAtDesc(Long.valueOf(userId));
        return taskMapper.toResponseList(tasks);
    }
}
