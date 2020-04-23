CREATE DATABASE gateway
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

ALTER USER 'root'@'localhost' IDENTIFIED BY 'RANDOM-PASSWORD-VukeJ@LdUseLqyMyhuklugc3qic';

CREATE USER 'gateway'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON gateway.* TO 'gateway'@'%';