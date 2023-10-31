ALTER TABLE Person ADD COLUMN password varchar(100) NOT NULL;

INSERT INTO Person (full_name, birth_year, date_of_birth, created_at, password)
    VALUES ('user1', 1999, date('2023-10-17'), current_timestamp, 'password');