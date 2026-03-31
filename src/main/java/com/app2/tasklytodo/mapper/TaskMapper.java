package com.app2.tasklytodo.mapper;

import com.app2.tasklytodo.dto.task.TaskResponse;
import com.app2.tasklytodo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    TaskResponse toResponse(Task task);

    List<TaskResponse> toResponseList(List<Task> tasks);
}
