DROP TABLE IF EXISTS USER_AUTH_DETAILS;
CREATE TABLE USER_AUTH_DETAILS (
  USERNAME VARCHAR(20) NOT NULL PRIMARY KEY,
  PASSWORD VARCHAR(100) NOT NULL,
  ROLE VARCHAR(10) NOT NULL
);