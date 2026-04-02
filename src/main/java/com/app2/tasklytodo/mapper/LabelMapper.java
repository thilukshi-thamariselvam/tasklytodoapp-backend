package com.app2.tasklytodo.mapper;

import com.app2.tasklytodo.dto.label.LabelResponse;
import com.app2.tasklytodo.entity.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    LabelResponse toResponse(Label label);

    Set<LabelResponse> toResponseSet(Set<Label> labels);

    List<LabelResponse> toResponseList(List<Label> labels);
}
