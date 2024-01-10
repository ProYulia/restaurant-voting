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
VALUES (6, CURRENT_DATE, 4),
       (7, CURRENT_DATE, 5);

INSERT INTO DISH (id, name, price, menu_id)
VALUES (8, 'Cheeze pizza', 299, 6),
       (9, 'Pepperoni', 349, 6),
       (10, 'Sushi set', 400, 7),
       (11, 'Soup', 250, 7);
