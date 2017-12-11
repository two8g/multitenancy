CREATE TABLE IF NOT EXISTS `employee` (
  id         IDENTITY PRIMARY KEY,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  department VARCHAR(255),
  office     VARCHAR(255)
);