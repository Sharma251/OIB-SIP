-- SQL script to create database and user for ATMInterface application

CREATE DATABASE atm_db;

CREATE USER 'atm_user'@'localhost' IDENTIFIED BY 'secure_password';

GRANT ALL PRIVILEGES ON atm_db.* TO 'atm_user'@'localhost';

FLUSH PRIVILEGES;
