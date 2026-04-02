ALTER TABLE tasks ADD COLUMN parent_task_id BIGINT NULL;

ALTER TABLE tasks
    ADD CONSTRAINT fk_task_parent
        FOREIGN KEY (parent_task_id) REFERENCES tasks(id)
            ON DELETE CASCADE;