ALTER TABLE tasks ADD COLUMN display_order INT DEFAULT 0;
CREATE INDEX idx_task_display_order ON tasks(display_order);