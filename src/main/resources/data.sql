INSERT INTO USERS (id, name, email, password)
VALUES (1, 'User', 'user@yandex.ru', '{noop}password'),
       (2, 'Admin', 'admin@gmail.com', '{noop}admin'),
       (3, 'Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (id, name)
VALUES (4, 'Pizzeria'),
       (5, 'Sushi');

INSERT INTO MENU (id, date_created, restaurant_id)
VALUES (6, '2023-12-23', 4),
       (7, '2023-12-23', 5),
       (8, CURRENT_DATE, 4),
       (9, CURRENT_DATE, 5);

INSERT INTO DISH (id, name, price, menu_id)
VALUES (10, 'Cheeze pizza', 299, 6),
       (11, 'Pepperoni', 349, 7),
       (12, 'Sushi set', 400, 9),
       (13, 'Soup', 250, 8),
       (14, '4 Cheeze', 299, 8),
       (15, 'Pepperoni', 349, 9);
