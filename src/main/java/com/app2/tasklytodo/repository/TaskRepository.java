package com.app2.tasklytodo.repository;

import com.app2.tasklytodo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserIdAndParentTaskIsNullOrderByDueDateAscCreatedAtDesc(Long userId);

    List<Task> findByUserIdAndDueDateAndParentTaskIsNull(Long userId, LocalDate dueDate);
}
