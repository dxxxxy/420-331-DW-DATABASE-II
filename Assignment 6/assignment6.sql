-- drop all data
DROP VIEW BUSINESS_BOOKS;
DROP TABLE BOOKAUTHORS2;
DROP TABLE BOOKS2;
DROP TABLE AUTHORS2;

-- create tables
CREATE TABLE BOOKS2(
    isbn VARCHAR2(10) PRIMARY KEY,
    title VARCHAR2(50),
    category VARCHAR2(20),
    price NUMBER(4,2)
);

CREATE TABLE AUTHORS2(
    author_id CHAR(4) PRIMARY KEY,
    name VARCHAR2(25),
    city VARCHAR2(20),
    country VARCHAR2(20)
);

CREATE TABLE BOOKAUTHORS2(
    isbn VARCHAR2(10) REFERENCES BOOKS2(isbn),
    author_id CHAR(4) REFERENCES AUTHORS2(author_id)
);

-- insert authors
INSERT INTO AUTHORS2 VALUES(0, 'Reginald Authorson','Montreal','Canada');
INSERT INTO AUTHORS2 VALUES(1, 'XYZ Tolkeen','Birmingham','England');
INSERT INTO AUTHORS2 VALUES(2, 'B Ronalds','Birmingham','England');
INSERT INTO AUTHORS2 VALUES(3, 'Janeet Paulton','Toronto','Canada');
INSERT INTO AUTHORS2 VALUES(4, 'Vanti Raulson','Toronto','Canada');

-- now insert books
INSERT INTO BOOKS2 VALUES('123ABC','How to book','Business',20.23);
INSERT INTO BOOKS2 VALUES('JKLXCD','Can you really ever book?','Business',30.12);
INSERT INTO BOOKS2 VALUES('AJKAJA','Wow hobbits!','Fantasy',10.2);
INSERT INTO BOOKS2 VALUES('ASDSAK','Geez its hobbits.','Fantasy',4.5);
INSERT INTO BOOKS2 VALUES('AJSHAK','Science Adventure','Sci-fi',13.12);
INSERT INTO BOOKS2 VALUES('OAISJD','Its cooking!','Cooking',35.36);
INSERT INTO BOOKS2 VALUES('ASDOA','The times and ideas of Ronald McRonald','Biography',23);
INSERT INTO BOOKS2 VALUES('ASDOA2','The times and ideas of Ronald McRonald','Biography',23);

-- now insert the author book relationship
INSERT INTO BOOKAUTHORS2 VALUES('123ABC', 0);
INSERT INTO BOOKAUTHORS2 VALUES('JKLXCD', 0);
INSERT INTO BOOKAUTHORS2 VALUES('AJKAJA', 1);
INSERT INTO BOOKAUTHORS2 VALUES('ASDSAK', 1);
INSERT INTO BOOKAUTHORS2 VALUES('AJSHAK', 2);
INSERT INTO BOOKAUTHORS2 VALUES('OAISJD', 3);
INSERT INTO BOOKAUTHORS2 VALUES('ASDOA', 3);
INSERT INTO BOOKAUTHORS2 VALUES('ASDOA2', 4);

-- How many books cost more than $15?
SELECT COUNT(*) FROM BOOKS2 WHERE price > 15;

-- List all the fantasy books, including the title and the authors name?
SELECT title, name FROM BOOKS2
JOIN BOOKAUTHORS2 USING(isbn)
JOIN AUTHORS2 USING(author_id) WHERE category = 'Fantasy';

-- List all the Canadian book titles?
SELECT title FROM BOOKS2
JOIN BOOKAUTHORS2 USING(isbn)
JOIN AUTHORS2 USING(author_id) WHERE country = 'Canada';

-- Create a view that shows the Title, Author, Price, and City for all Business books
CREATE VIEW BUSINESS_BOOKS AS
SELECT title, name, price, city FROM BOOKS2
JOIN BOOKAUTHORS2 USING(isbn)
JOIN AUTHORS2 USING(author_id) WHERE category = 'Business';

-- Add indexes to your database that would allow returning your view above more efficiently
CREATE INDEX BOOKS2_IDX ON BOOKS2(category);
CREATE INDEX BOOKAUTHORS2_IDX ON BOOKAUTHORS2(isbn);
CREATE INDEX AUTHORS2_IDX ON AUTHORS2(country);

-- Access your view with and without the index
SELECT * FROM business_books;

COMMIT;