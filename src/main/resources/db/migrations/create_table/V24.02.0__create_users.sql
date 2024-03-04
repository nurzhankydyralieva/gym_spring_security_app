DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY,
    user_name     VARCHAR(40) NOT NULL,
    first_name    VARCHAR(40) NOT NULL,
    last_name     VARCHAR(40) NOT NULL,
    password      VARCHAR(255),
    is_active     BOOLEAN,
    criteria      VARCHAR(255),
    role          VARCHAR(40),
    is_assigned   BOOLEAN
);
