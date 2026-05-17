CREATE TABLE task(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(150),
    priority VARCHAR(6) NOT NULL,
    deadline DATE NOT NULL,
    task_status VARCHAR(10) NOT NULL
);