INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (id, name)
VALUES (1, 'Pizzeria'),
       (2, 'Sushi');

INSERT INTO MENU (id, date_created, restaurant_id)
VALUES (1, CURRENT_DATE, 1),
       (2, CURRENT_DATE, 2);

INSERT INTO DISH (id, name, price, menu_id)
VALUES (1, 'Cheeze pizza', 299, 1),
       (2, 'Pepperoni', 349, 1),
       (3, 'Sushi set', 400, 2),
       (4, 'Soup', 250, 2);
