CREATE TABLE
    groups (
        id BIGSERIAL PRIMARY KEY,
        group_id VARCHAR(255) NOT NULL UNIQUE,
        name VARCHAR(255) NOT NULL,
        created_user_id BIGINT NOT NULL,
        updated_user_id BIGINT,
        created_at TIMESTAMP DEFAULT NOW (),
        updated_at TIMESTAMP DEFAULT NOW (),
        delete_flag BOOLEAN DEFAULT FALSE
    );

CREATE TABLE
    group_members (
        id BIGSERIAL PRIMARY KEY,
        group_id BIGINT NOT NULL REFERENCES groups (id),
        user_id BIGINT NOT NULL,
        joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        created_user_id BIGINT NOT NULL,
        updated_user_id BIGINT,
        created_at TIMESTAMP DEFAULT NOW (),
        updated_at TIMESTAMP DEFAULT NOW (),
        delete_flag BOOLEAN DEFAULT FALSE
    );

CREATE TABLE
    group_timelines (
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT NOT NULL,
        training_id BIGINT,
        is_public BOOLEAN DEFAULT TRUE,
        comment TEXT,
        image_url TEXT,
        created_user_id BIGINT NOT NULL,
        updated_user_id BIGINT,
        created_at TIMESTAMP DEFAULT NOW (),
        updated_at TIMESTAMP DEFAULT NOW (),
        delete_flag BOOLEAN DEFAULT FALSE
    );