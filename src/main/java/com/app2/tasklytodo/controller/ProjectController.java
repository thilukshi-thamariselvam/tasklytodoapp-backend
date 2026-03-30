package com.app2.tasklytodo.controller;

import com.app2.tasklytodo.dto.project.ProjectCreateRequest;
import com.app2.tasklytodo.dto.project.ProjectResponse;
import com.app2.tasklytodo.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(@RequestParam String userId) {
        List<ProjectResponse> responses = projectService.getProjectsByUser(userId);
        return ResponseEntity.ok(responses);
    }
}
