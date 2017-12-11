CREATE TABLE TENANT (
  id       IDENTITY PRIMARY KEY,
  name     VARCHAR(255),
  url      VARCHAR(255),
  username VARCHAR(255),
  password VARCHAR(255)
);