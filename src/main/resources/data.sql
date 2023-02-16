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

INSERT INTO
    post(title, content, author_id)
VALUES
    ('first post', 'this is first post', 1),
    ('second post', 'this is second post', 2),
    ('third post', 'this is third post', 2),
    ('fourth post', 'this is fourth post', 2),
    ('fifth', 'this is fifth post', 1),
    ('sixth', 'this is sixts post', 1);

insert into
    report(cause, post_id)
values
    ('spam', 1),
    ('spam2', 1);