INSERT INTO
    application_user(username, email, password)
VALUES
    ('admin', 'admin@example.com', '{noop}admin'),
    ('user', 'user@example.com', '{noop}user');


INSERT INTO
    user_role(role_name)
VALUES
    ('ADMIN'),
    ('USER'),
    ('BLOCKED_USER');

INSERT INTO
    user_roles(user_id, role_id)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO
    post(title, content, author_id)
VALUES
    ('Project', 'I have no project idea in java, could you help? ', 2),
    ('Bitcoin 122k', 'What do you think about current price? I hope for a price drop.', 2),
    ('Java 20!!!', 'Java 20 has been released! What is new? -scoped values -records patterns -vector API proposal -foreign function and memory API', 1),
    ('IT JOB','How did you guys get your first IT job? Tell me your story.', 2);
