DROP TABLE IF EXISTS token;

CREATE TABLE IF NOT EXISTS token
(
    id         INTEGER PRIMARY KEY,
    token      VARCHAR(255),
    token_type VARCHAR(255),
    revoked    BOOLEAN,
    expired    BOOLEAN,
    user_id    UUID
);
