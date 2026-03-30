CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(2000),
                       due_date DATE,
                       status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                       user_id BIGINT NOT NULL,
                       project_id BIGINT,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES users(id),
                       CONSTRAINT fk_tasks_project FOREIGN KEY (project_id) REFERENCES projects(id)
);