CREATE TABLE trainings (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  performed_at TIMESTAMP NOT NULL,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE training_menus (
  id BIGSERIAL PRIMARY KEY,
  training_id BIGINT NOT NULL REFERENCES trainings(id) ON DELETE CASCADE,
  display_order INTEGER NOT NULL,
  name TEXT,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE training_menu_sets (
  id BIGSERIAL PRIMARY KEY,
  training_menu_id BIGINT NOT NULL REFERENCES training_menus(id) ON DELETE CASCADE,
  set_order INTEGER NOT NULL,
  reps INTEGER,
  weight INTEGER,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);
