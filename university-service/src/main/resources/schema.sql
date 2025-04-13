CREATE TABLE IF NOT EXISTS uni_info (
    uni_info_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(7000),
    avatar BLOB,
    rating FLOAT
);

CREATE TABLE IF NOT EXISTS uni_geographic (
    uni_geographic_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    uni_info_id BIGINT REFERENCES uni_info(uni_info_id) ON DELETE CASCADE,
    filial_name VARCHAR(255),
    address VARCHAR(400)
);

CREATE TABLE IF NOT EXISTS uni_socials (
    uni_socials_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    link VARCHAR(255),
    uni_info_id BIGINT REFERENCES uni_info(uni_info_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS program_conditions (
    program_conditions_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    min_course INTEGER,
    min_rate FLOAT
);

CREATE TABLE IF NOT EXISTS exchange_programs (
    exchange_programs_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(7000),
    rating FLOAT,
    agent_id INT,
    program_conditions_id BIGINT REFERENCES program_conditions(program_conditions_id) ON DELETE CASCADE,
    uni_info_id BIGINT REFERENCES uni_info(uni_info_id) ON DELETE CASCADE
);