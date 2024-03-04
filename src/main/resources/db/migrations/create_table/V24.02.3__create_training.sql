DROP TABLE IF EXISTS training;

CREATE TABLE training
(
    training_id       BIGINT PRIMARY KEY,
    trainee_id        UUID,
    trainer_id        UUID,
    training_name     VARCHAR(40),
    training_type_id  BIGINT,
    training_date     TIMESTAMP,
    training_duration bytea
);