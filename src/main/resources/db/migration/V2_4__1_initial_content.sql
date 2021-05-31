insert into dbo.user_data (id, password, status, user_name) VALUES
(2, '$2a$10$h/ds7n4hWYEH6NHduOMlxePZbciJdxC76A35KeD2uLAdK3ajj5EPi' , 'ACTIVE', 'admin');

insert into dbo.user_role (role_id, user_id) VALUES
(1, 2);

insert into dbo.bike_station (id, location_name, max_bikes, status) VALUES
(3, 'Ankh-Morpork', 10, 'Working'),
(4, 'Kontynent Przeciwwagi', 10, 'Working'),
(5, 'Iksiksiksiks', 10, 'Working');

insert into dbo.bike (id, status, bike_station_id) VALUES
(6, 'ACTIVE', 3),
(7, 'ACTIVE', 3),
(8, 'ACTIVE', 3),
(9, 'ACTIVE', 4),
(10, 'ACTIVE', 4),
(11, 'ACTIVE', 4),
(12, 'ACTIVE', 5),
(13, 'ACTIVE', 5),
(14, 'ACTIVE', 5);