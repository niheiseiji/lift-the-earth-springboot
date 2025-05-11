ALTER TABLE users ADD COLUMN profile_image_url TEXT;
ALTER TABLE users RENAME COLUMN name TO display_name;

ALTER TABLE users ADD COLUMN unique_name TEXT;

-- 非NULLのunique_nameを後から追加したため特殊対応
-- 仮のユニーク名を生成（PostgreSQL関数で乱数と組み合わせ）
UPDATE users
SET unique_name = '@user_' || id;

-- NULLがなくなった状態で制約を追加
ALTER TABLE users
  ALTER COLUMN unique_name SET NOT NULL,
  ADD CONSTRAINT unique_name_unique UNIQUE (unique_name),
  ADD CONSTRAINT unique_name_format CHECK (unique_name ~ '^@[A-Za-z0-9_]+$');

CREATE TABLE user_auth_providers (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  provider TEXT NOT NULL,
  provider_user_id TEXT NOT NULL,
  email TEXT,

  created_user_id INTEGER,
  updated_user_id INTEGER,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE,

  UNIQUE (provider, provider_user_id)
);

COMMENT ON COLUMN user_auth_providers.provider IS '例: google, line, email';
COMMENT ON COLUMN user_auth_providers.provider_user_id IS '各SNSごとのユニークID';
COMMENT ON COLUMN user_auth_providers.email IS 'SNSから取得できた場合のみ保持';