package com.app2.tasklytodo.mapper;

import com.app2.tasklytodo.dto.project.ProjectResponse;
import com.app2.tasklytodo.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toResponse(Project project);

    List<ProjectResponse> toResponseList(List<Project> projects);
}
