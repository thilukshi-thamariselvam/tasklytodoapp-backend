package com.app2.tasklytodo.repository;

import com.app2.tasklytodo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // 1. GET ALL MAIN TASKS (Inbox, Today, Upcoming)
    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.subtasks s " +
            "LEFT JOIN FETCH t.labels l " +
            "WHERE t.user.id = :userId " +
            "AND t.parentTask IS NULL " +
            "AND t.deletedAt IS NULL " +
            "ORDER BY t.dueDate ASC, t.createdAt DESC")
    List<Task> findMainTasksByUser(@Param("userId") Long userId);

    // 2. GET TASKS BY DATE (Today Page)
    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.subtasks s " +
            "LEFT JOIN FETCH t.labels l " +
            "WHERE t.user.id = :userId " +
            "AND t.parentTask IS NULL " +
            "AND t.deletedAt IS NULL " +
            "AND t.dueDate = :dueDate " +
            "ORDER BY t.createdAt DESC")
    List<Task> findTasksByDueDate(@Param("userId") Long userId, @Param("date") LocalDate dueDate);

    // 3. SEARCH TASKS (Command Palette)
    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.subtasks s " +
            "LEFT JOIN FETCH t.labels l " +
            "WHERE t.user.id = :userId " +
            "AND t.parentTask IS NULL " +
            "AND t.deletedAt IS NULL " +
            "AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY t.dueDate ASC, t.createdAt DESC")
    List<Task> searchTasks(@Param("userId") Long userId, @Param("query") String query);
}
