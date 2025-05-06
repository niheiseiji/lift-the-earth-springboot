CREATE TABLE preset_trainings (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  preset_name TEXT NOT NULL,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE preset_training_menus (
  id BIGSERIAL PRIMARY KEY,
  preset_training_id BIGINT NOT NULL REFERENCES preset_trainings(id) ON DELETE CASCADE,
  display_order INTEGER NOT NULL,
  name TEXT,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE preset_training_menu_sets (
  id BIGSERIAL PRIMARY KEY,
  preset_training_menu_id BIGINT NOT NULL REFERENCES preset_training_menus(id) ON DELETE CASCADE,
  set_order INTEGER NOT NULL,
  reps INTEGER,
  weight INTEGER,

  created_user_id BIGINT,
  updated_user_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);
