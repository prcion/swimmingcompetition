-- DROP DATABASE IF EXISTS swimmingcompetition;
-- 
-- CREATE DATABASE swimmingcompetition;

CREATE TABLE IF NOT EXISTS user (`id` INT PRIMARY KEY AUTO_INCREMENT, `user_name` VARCHAR(50) NOT NULL, `password` VARCHAR(50) NOT NULL);