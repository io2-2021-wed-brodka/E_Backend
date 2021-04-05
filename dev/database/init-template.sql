IF SUSER_ID('renting') IS NULL
    BEGIN
        CREATE LOGIN renting WITH PASSWORD = '${DATABASE_PASSWORD}';
    END
GO

IF DB_ID('renting') IS NULL
    BEGIN
        CREATE DATABASE renting;
    END
GO

USE renting;
GO

IF USER_ID('renting') IS NULL
    BEGIN
        CREATE USER renting FOR LOGIN renting;
        GRANT CONTROL TO renting;
    END
GO
