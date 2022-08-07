-- roles
INSERT INTO roles (id, name, created_at, updated_at) VALUES (1, 'ROLE_USER', CURRENT_TIMESTAMP, null);
INSERT INTO roles (id, name, created_at, updated_at) VALUES (2, 'ROLE_ADMIN', CURRENT_TIMESTAMP, null);

-- users
INSERT INTO users (id, username, password, created_at, updated_at) VALUES (1, 'admin', '$2a$10$vSHN5QFT8Uizj4zea11uUewdJuxcrst4VulG3lDBe5pyc0YX5JNFO', CURRENT_TIMESTAMP, null);
INSERT INTO users (id, username, password, created_at, updated_at) VALUES (2, 'user', '$2a$10$XJWme1WY10IzrU1SOT4OAuIH924afmSoRinB8aF8Jq6snJuq9Rluq', CURRENT_TIMESTAMP, null);

-- users_roles
INSERT INTO users_roles (user_id, roles_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES (2, 1);
