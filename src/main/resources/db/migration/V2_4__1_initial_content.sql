insert into dbo.user_data (id, password, status, user_name) VALUES
(2, '$2a$10$XtDOt9NaJ1vfCZ4dZxdA1.EubpxiRVehVtpxuWEwO7cegBp2pOkUi' , 'ACTIVE', 'admin');

insert into dbo.user_role (role_id, user_id) VALUES
(1, 1);

insert into dbo.bike_station (id, location_name, max_bikes, status)
(3, 'stacja_1', 10, 'Working')
(4, 'stacja_2', 10, 'Working')
(5, 'stacja_3', 10, 'Working')

insert into dbo.bike (id, status, bike_station_id, reservation_id, is_blocked)
(6, 'ACTIVE', 3, NULL, 0)
(7, 'ACTIVE', 3, NULL, 0)
(8, 'ACTIVE', 3, NULL, 0)
(9, 'ACTIVE', 4, NULL, 0)
(10, 'ACTIVE', 4, NULL, 0)
(11, 'ACTIVE', 4, NULL, 0)
(12, 'ACTIVE', 5, NULL, 0)
(13, 'ACTIVE', 5, NULL, 0)
(14, 'ACTIVE', 5, NULL, 0)