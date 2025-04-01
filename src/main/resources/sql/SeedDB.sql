-- Insert sample data
INSERT INTO public.admins (email, password)
VALUES ('admin1@example.com', 'password123'),
       ('admin2@example.com', 'securepass456');

INSERT INTO public.customers (email, password, balance)
VALUES ('customer1@example.com', 'pass123', 50),
       ('customer2@example.com', 'mypassword', 75),
       ('customer3@example.com', 'securepass', 30);

INSERT INTO public.cupcake_bottoms (price, bottom_name)
VALUES (5, 'Chocolate'),
       (5, 'Vanilla'),
       (5, 'Nutmeg'),
       (6, 'Pistachio'),
       (7, 'Almond');

INSERT INTO public.cupcake_tops (price, top_name)
VALUES (5, 'Chocolate'),
       (5, 'Blueberry'),
       (5, 'Raspberry'),
       (6, 'Crispy'),
       (6, 'Strawberry'),
       (7, 'Rum/Raisin'),
       (8, 'Orange'),
       (8, 'Lemon'),
       (9, 'Blue cheese');
