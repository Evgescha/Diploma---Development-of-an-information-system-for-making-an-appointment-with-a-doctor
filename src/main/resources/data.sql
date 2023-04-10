MERGE INTO role (id, name)
    KEY (id)
    VALUES (1, 'USER');

MERGE INTO role (id, name)
    KEY (id)
    VALUES (2, 'MANAGER');

MERGE INTO role (id, name)
    KEY (id)
    VALUES (3, 'ADMIN');
