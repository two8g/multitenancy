CREATE SCHEMA tenants;
USE tenants;
CREATE TABLE `tenant` (
  id       BIGINT(20)   NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL             DEFAULT ''
);
CREATE UNIQUE INDEX tenant_name_uindex
  ON tenant (name);
CREATE SCHEMA tenant_default;
USE tenant_default;
CREATE TABLE employee (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstname  VARCHAR(255),
  lastname   VARCHAR(255),
  department VARCHAR(255),
  office     VARCHAR(255)
);