create database if not exists customer_db;

create user if not exists 'customer_service_user'@'localhost' identified by 'pass_123';
grant all privileges on customer_db.* to 'customer_service_user'@'localhost';
flush privileges;