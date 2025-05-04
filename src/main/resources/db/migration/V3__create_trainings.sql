CREATE TABLE trainings (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  performed_at TIMESTAMP NOT NULL,

  created_user_id INTEGER,
  updated_user_id INTEGER,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE training_menus (
  id SERIAL PRIMARY KEY,
  training_id INTEGER NOT NULL REFERENCES trainings(id) ON DELETE CASCADE,
  display_order INTEGER NOT NULL,
  name TEXT,

  created_user_id INTEGER,
  updated_user_id INTEGER,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);

CREATE TABLE training_menu_sets (
  id SERIAL PRIMARY KEY,
  training_menu_id INTEGER NOT NULL REFERENCES training_menus(id) ON DELETE CASCADE,
  set_order INTEGER NOT NULL,
  reps INTEGER,
  weight INTEGER,

  created_user_id INTEGER,
  updated_user_id INTEGER,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  delete_flag BOOLEAN DEFAULT FALSE
);
