CREATE TABLE task_labels (
                             task_id BIGINT NOT NULL,
                             label_id BIGINT NOT NULL,
                             PRIMARY KEY (task_id, label_id),
                             CONSTRAINT fk_tasklabels_task FOREIGN KEY (task_id) REFERENCES tasks(id),
                             CONSTRAINT fk_tasklabels_label FOREIGN KEY (label_id) REFERENCES labels(id)
);