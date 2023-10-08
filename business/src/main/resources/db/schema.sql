CREATE SCHEMA IF NOT EXISTS country_phone;

DROP TABLE IF EXISTS country_phone.country_calling_code_descriptor;

CREATE TABLE country_phone.country_calling_code_descriptor
(
    code    VARCHAR(7) PRIMARY KEY,
    country VARCHAR(100) NOT NULL
);