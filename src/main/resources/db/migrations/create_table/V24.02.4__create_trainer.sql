DROP TABLE IF EXISTS trainer;

CREATE TABLE trainer
(
    id                UUID PRIMARY KEY,
    first_name        VARCHAR(40) NOT NULL,
    last_name         VARCHAR(40) NOT NULL,
    user_name         VARCHAR(40) NOT NULL,
    password          VARCHAR(255),
    role              VARCHAR(40),
    is_active         BOOLEAN,
    is_assigned       BOOLEAN,
    criteria          VARCHAR(255),
    specialization_id BIGINT

);