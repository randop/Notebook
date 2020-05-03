CREATE DATABASE gateway
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

ALTER USER 'root'@'localhost' IDENTIFIED BY 'RANDOM-PASSWORD-VukeJ@LdUseLqyMyhuklugc3qic';

CREATE USER 'gateway'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON gateway.* TO 'gateway'@'%';

CREATE TABLE `datastore` (  
  `added_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `row_key` CHAR(36) NOT NULL,
  `column_name` VARCHAR(255) NOT NULL,
  `ref_key` INT UNSIGNED NOT NULL DEFAULT 0,
  `body` LONGBLOB,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (added_id),
  KEY `row_key` (`row_key`),
  KEY `row_key_column_name` (`row_key`,`column_name`)
) Engine=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `accounts` (  
  `added_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `row_key` CHAR(36) NOT NULL,  
  PRIMARY KEY (added_id),
  KEY `row_key` (`row_key`)
) Engine=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `users` (  
  `added_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `row_key` CHAR(36) NOT NULL,
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  PRIMARY KEY (added_id),
  KEY `row_key` (`row_key`)
) Engine=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
