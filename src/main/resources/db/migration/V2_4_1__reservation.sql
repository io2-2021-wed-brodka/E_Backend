alter table renting.dbo.bike add status nvarchar(255) default 'ACTIVE';

alter table renting.dbo.user_data add status nvarchar(255) default 'ACTIVE';

create table renting.dbo.reservation (
    id int primary key,
    user_id bigint references user_data(id),
    bike_id bigint references bike(id),
    create_date datetime2,
    reserved_at datetime2,
    reserved_till datetime2
);
