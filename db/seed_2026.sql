BEGIN;

-- Reset seedable tables for a deterministic local dataset.
TRUNCATE TABLE ordered_food_items, flight_orders, flights, food_options RESTART IDENTITY;

INSERT INTO food_options (id, name, type) VALUES
  (1, 'Grilled Ribeye', 'Beef'),
  (2, 'Beef Stroganoff', 'Beef'),
  (3, 'Roast Chicken', 'Chicken'),
  (4, 'Grilled Chicken Skewers', 'Chicken'),
  (5, 'Grilled Salmon', 'Fish'),
  (6, 'Baked Cod', 'Fish'),
  (7, 'Vegetable Lasagna', 'Vegetarian'),
  (8, 'Stuffed Peppers', 'Vegetarian'),
  (9, 'Vegan Burger', 'Vegan'),
  (10, 'Quinoa Salad', 'Vegan');

INSERT INTO flights (
  id, brand, plane_type, crew_count, seats, preferred_food,
  arrival_date, departure_date, departure_time, food_requested
) VALUES
  (1, 'Oman Air', 'Airbus A320', 6, 162, 'Chicken', '2026-01-20', '2026-01-20', '08:10:00', true),
  (2, 'Emirates', 'Boeing 777-300ER', 12, 354, 'Beef', '2026-01-28', '2026-01-28', '13:45:00', true),
  (3, 'Qatar Airways', 'Airbus A350-900', 11, 320, 'Vegan', '2026-02-03', '2026-02-03', '09:20:00', true),
  (4, 'Turkish Airlines', 'Boeing 737-800', 7, 189, 'Vegetarian', '2026-02-10', '2026-02-10', '17:30:00', true),
  (5, 'Lufthansa', 'Airbus A321neo', 8, 200, 'Fish', '2026-02-15', '2026-02-15', '06:55:00', true),
  (6, 'Etihad Airways', 'Boeing 787-9', 10, 290, 'Chicken', '2026-02-18', '2026-02-18', '11:40:00', true),
  (7, 'KLM', 'Boeing 737-900', 7, 186, 'Beef', '2026-02-24', '2026-02-24', '19:10:00', true),
  (8, 'Delta', 'Airbus A330-300', 10, 281, 'Vegetarian', '2026-03-02', '2026-03-02', '14:05:00', true),
  (9, 'United', 'Boeing 757-200', 8, 176, 'Mixed', '2026-03-10', '2026-03-10', '07:25:00', true),
  (10, 'Air France', 'Airbus A320', 6, 168, 'Vegan', '2026-03-18', '2026-03-18', '21:00:00', true),
  (11, 'British Airways', 'Boeing 787-8', 10, 247, 'Fish', '2026-03-26', '2026-03-26', '15:50:00', true),
  (12, 'Singapore Airlines', 'Airbus A350-900', 11, 303, 'Chicken', '2026-04-02', '2026-04-02', '10:15:00', true),
  (13, 'ANA', 'Boeing 767-300ER', 9, 211, 'Vegetarian', '2026-04-11', '2026-04-11', '12:35:00', true),
  (14, 'Cathay Pacific', 'Airbus A330-300', 9, 265, 'Beef', '2026-04-22', '2026-04-22', '18:20:00', true),
  (15, 'Saudia', 'Boeing 777-200ER', 12, 340, 'Mixed', '2026-04-29', '2026-04-29', '05:40:00', true);

INSERT INTO flight_orders (id, flight_id, status, last_updated) VALUES
  (1, 1, 'PENDING', '2026-01-18 10:30:00'),
  (2, 2, 'CONFIRMED', '2026-01-26 09:10:00'),
  (3, 3, 'PREPARING', '2026-02-02 14:25:00'),
  (4, 4, 'DELIVERED', '2026-02-10 19:05:00'),
  (5, 5, 'CONFIRMED', '2026-02-14 16:20:00'),
  (6, 6, 'PENDING', '2026-02-17 08:45:00'),
  (7, 7, 'PREPARING', '2026-02-24 20:15:00'),
  (8, 8, 'CONFIRMED', '2026-03-01 11:40:00'),
  (9, 9, 'PENDING', '2026-03-09 15:30:00'),
  (10, 10, 'DELIVERED', '2026-03-18 22:10:00'),
  (11, 11, 'PREPARING', '2026-03-25 13:50:00'),
  (12, 12, 'CONFIRMED', '2026-04-01 18:20:00'),
  (13, 13, 'PENDING', '2026-04-10 09:55:00'),
  (14, 14, 'CONFIRMED', '2026-04-21 17:05:00'),
  (15, 15, 'PENDING', '2026-04-28 12:35:00'),
  (16, 6, 'CONFIRMED', '2026-02-18 09:12:00'),
  (17, 8, 'DELIVERED', '2026-03-02 18:45:00'),
  (18, 12, 'PREPARING', '2026-04-02 12:40:00'),
  (19, 14, 'DELIVERED', '2026-04-22 22:05:00'),
  (20, 3, 'CONFIRMED', '2026-02-03 10:15:00');

INSERT INTO ordered_food_items (id, order_id, food_id, quantity) VALUES
  (1, 1, 1, 24),
  (2, 1, 7, 18),
  (3, 2, 2, 36),
  (4, 2, 8, 14),
  (5, 3, 4, 22),
  (6, 3, 10, 10),
  (7, 4, 3, 16),
  (8, 4, 9, 8),
  (9, 5, 5, 20),
  (10, 5, 7, 12),
  (11, 6, 1, 28),
  (12, 6, 6, 15),
  (13, 7, 2, 19),
  (14, 7, 3, 21),
  (15, 8, 8, 25),
  (16, 8, 7, 11),
  (17, 9, 10, 14),
  (18, 9, 4, 18),
  (19, 10, 4, 20),
  (20, 10, 7, 10),
  (21, 11, 5, 23),
  (22, 11, 3, 17),
  (23, 12, 1, 26),
  (24, 12, 6, 14),
  (25, 13, 3, 19),
  (26, 14, 2, 21),
  (27, 15, 4, 24),
  (28, 16, 1, 12),
  (29, 17, 9, 9),
  (30, 18, 5, 13),
  (31, 19, 7, 16),
  (32, 20, 10, 11);

-- Keep identity sequences in sync after explicit IDs.
SELECT setval(pg_get_serial_sequence('flights', 'id'), (SELECT MAX(id) FROM flights), true);
SELECT setval(pg_get_serial_sequence('flight_orders', 'id'), (SELECT MAX(id) FROM flight_orders), true);
SELECT setval(pg_get_serial_sequence('ordered_food_items', 'id'), (SELECT MAX(id) FROM ordered_food_items), true);

COMMIT;
