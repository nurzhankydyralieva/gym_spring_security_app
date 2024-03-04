DROP TABLE IF EXISTS trainee;

CREATE TABLE IF NOT EXISTS trainee
(
    id            UUID PRIMARY KEY,
    user_name     VARCHAR(40) NOT NULL,
    first_name    VARCHAR(40) NOT NULL,
    last_name     VARCHAR(40) NOT NULL,
    date_of_birth DATE,
    address       VARCHAR(255),
    criteria      VARCHAR(255),
    password      VARCHAR(255),
    role          VARCHAR(40),
    is_active     BOOLEAN,
    is_assigned   BOOLEAN
);
