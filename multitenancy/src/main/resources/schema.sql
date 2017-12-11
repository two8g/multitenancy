CREATE TABLE `tenant` (
  id       BIGINT(20)   NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(255) NOT NULL,
  url      VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL             DEFAULT ''
);
CREATE UNIQUE INDEX tenant_name_uindex
  ON tenant (name);