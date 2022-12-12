INSERT INTO
    application_user(username, email, password)
VALUES
    ('admin', 'admin@example.com', '{noop}admin'),
    ('user', 'user@example.com', '{noop}user');


INSERT INTO
    user_role(role_name)
VALUES
    ('ADMIN'),
    ('USER');

INSERT INTO
    user_roles(user_id, role_id)
VALUES
    (1, 1),
    (2, 2);