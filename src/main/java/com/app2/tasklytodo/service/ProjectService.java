package com.app2.tasklytodo.service;

import com.app2.tasklytodo.dto.project.ProjectCreateRequest;
import com.app2.tasklytodo.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectCreateRequest request);

    List<ProjectResponse> getProjectsByUser(String userId);
}
