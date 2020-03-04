DROP DATABASE IF EXISTS swimmingcompetition;
--
CREATE DATABASE swimmingcompetition;

use swimmingcompetition;
CREATE TABLE IF NOT EXISTS `user` (`id` INT PRIMARY KEY AUTO_INCREMENT, `user_name` VARCHAR(50) NOT NULL, `password` VARCHAR(50) NOT NULL);
CREATE TABLE IF NOT EXISTS `participant`(`id` INT PRIMARY KEY AUTO_INCREMENT, `name` VARCHAR(50), `age` INT);
CREATE TABLE IF NOT EXISTS `proba` (`id` INT PRIMARY KEY AUTO_INCREMENT, `style` VARCHAR(20), `distance` VARCHAR(20), `id_participant` INT NOT NULL, FOREIGN KEY ( `id_participant` ) REFERENCES `participant` ( `id` ));