CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$wY1yM8aWqB4y3ZkM3tI/oefjUqYtqA3Z1w5Z/gI9K8q/t4J5bK5S.', 'ADMIN');
