--  Inicializar permisos
INSERT INTO permissions (name) VALUES 
('CREATE'),
('READ'),
('UPDATE'),
('DELETE'),
('REFACTOR');

--  Inicializar roles
INSERT INTO roles (role_name) VALUES 
('ADMIN'),
('USER'),
('DEVELOPER');

--  Relacionar roles con permisos
-- ADMIN → CREATE, READ, UPDATE, DELETE
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.role_name = 'ADMIN'
  AND p.name IN ('CREATE', 'READ', 'UPDATE', 'DELETE');

-- USER → CREATE, READ, UPDATE
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.role_name = 'USER'
  AND p.name IN ('CREATE', 'READ', 'UPDATE');

-- DEVELOPER → CREATE, READ, UPDATE, REFACTOR
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.role_name = 'DEVELOPER'
  AND p.name IN ('CREATE', 'READ', 'UPDATE', 'REFACTOR');

--  Crear usuarios
-- (Usa las contraseñas ya encriptadas con bcrypt)
INSERT INTO users (id, username, password, is_enabled, account_no_expired, account_no_locked, credential_no_expired)
VALUES
(UUID(), 'carlos', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', b'1', b'1', b'1', b'1'),
(UUID(), 'andres', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', b'1', b'1', b'1', b'1'),
(UUID(), 'laura', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', b'1', b'1', b'1', b'1');

--  Relacionar usuarios con roles
-- Carlos → ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'carlos' AND r.role_name = 'ADMIN';

-- Andres → USER
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'andres' AND r.role_name = 'USER';

-- Laura → DEVELOPER
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'laura' AND r.role_name = 'DEVELOPER';
