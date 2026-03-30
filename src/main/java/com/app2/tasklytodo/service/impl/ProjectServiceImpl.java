package com.app2.tasklytodo.service.impl;

import com.app2.tasklytodo.repository.ProjectRepository;
import com.app2.tasklytodo.service.ProjectService;
import com.app2.tasklytodo.dto.project.ProjectCreateRequest;
import com.app2.tasklytodo.dto.project.ProjectResponse;
import com.app2.tasklytodo.entity.Project;
import com.app2.tasklytodo.entity.User;
import com.app2.tasklytodo.mapper.ProjectMapper;
import com.app2.tasklytodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        log.info("Creating project: {} for user: {}", request.getName(), request.getUserId());

        User user = userRepository.findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .color(request.getColor())
                .user(user)
                .build();

        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsByUser(String userId) {
        log.info("Fetching projects for user: {}", userId);
        List<Project> projects = projectRepository.findByUserIdOrderByCreatedAtDesc(Long.valueOf(userId));
        return projectMapper.toResponseList(projects);
    }
}
