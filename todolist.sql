USE todolist;

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending'
);

INSERT INTO tasks (task_name)
VALUES ('Complete Java project');
SELECT * FROM tasks;
USE todolist;
SELECT * FROM tasks;
USE todolist;

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending'
);

