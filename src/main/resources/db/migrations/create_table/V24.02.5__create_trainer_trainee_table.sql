-- DROP TABLE trainer_trainee_table CASCADE;
DROP TABLE IF EXISTS trainer_trainee_table;

CREATE TABLE IF NOT EXISTS trainer_trainee_table
(
    trainer_id UUID,
    trainee_id UUID
);
