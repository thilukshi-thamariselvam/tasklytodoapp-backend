package com.app2.tasklytodo.repository;

import com.app2.tasklytodo.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findByUserIdOrderByCreatedAtDesc(Long userId);
}
