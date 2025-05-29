-- group_idカラムを追加
ALTER TABLE group_timelines
ADD COLUMN group_id BIGINT NOT NULL DEFAULT 1;

-- 外部キー制約を追加
-- ALTER TABLE group_timelines ADD CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups (id);