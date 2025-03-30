-- Insert sample data
INSERT INTO public.admins (email, password) VALUES
('admin1@example.com', 'password123'),
('admin2@example.com', 'securepass456');

INSERT INTO public.customers (email, password, balance) VALUES
('customer1@example.com', 'pass123', 50.00),
('customer2@example.com', 'mypassword', 75.50),
('customer3@example.com', 'securepass', 30.25);

INSERT INTO public.status (is_paid) VALUES
(TRUE, FALSE),   -- Paid but not picked up
(FALSE, FALSE),  -- Not paid and not picked up
(TRUE, TRUE),    -- Paid and picked up

INSERT INTO public.cupcake_bottoms (price, bottom_name) VALUES
(5.00, 'Chocolate'),
(5.00, 'Vanilla'),
(5.00, 'Nutmeg'),
(6.00, 'Pistachio'),
(7.00, 'Almond');

INSERT INTO public.cupcake_tops (price, top_name) VALUES
(5.00, 'Chocolate'),
(5.00, 'Blueberry'),
(5.00, 'Raspberry'),
(6.00, 'Crispy'),
(6.00, 'Strawberry'),
(7.00, 'Rum/Raisin'),
(8.00, 'Orange'),
(8.00, 'Lemon'),
(9.00, 'Blue cheese');
