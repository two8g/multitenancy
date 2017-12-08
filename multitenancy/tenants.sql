CREATE SCHEMA 'tenants';
USE tenants;
CREATE TABLE `tenant` (
  id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(15),
  url      VARCHAR(200),
  username VARCHAR(50),
  password VARCHAR(50)
);