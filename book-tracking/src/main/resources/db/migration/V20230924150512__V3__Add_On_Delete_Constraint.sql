ALTER TABLE Book
    DROP CONSTRAINT book_person_id_fkey;

ALTER TABLE Book
    ADD CONSTRAINT book_person_id_fkey
        FOREIGN KEY (person_id)
            REFERENCES Person (id)
            ON DELETE SET NULL;