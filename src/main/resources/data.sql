INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Pizzeria'),
       ('Sushi'),
       ('Chinese');

INSERT INTO MENU (date_effective, restaurant_id)
VALUES ('2023-12-23', 1),
       ('2023-12-23', 2),
       (CURRENT_DATE, 1),
       (CURRENT_DATE, 2);

INSERT INTO DISH (name, price, menu_id)
VALUES ('Cheese pizza', 299, 1),
       ('Pepperoni', 349, 2),
       ('Sushi set', 400, 4),
       ('Soup', 250, 3),
       ('4 Cheese', 299, 3),
       ('Pepperoni', 349, 4);
