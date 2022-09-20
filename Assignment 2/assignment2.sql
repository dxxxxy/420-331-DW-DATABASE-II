-- Serhiy Fedurtsya 2135229
CREATE OR REPLACE PACKAGE book_store AS
--    FUNCTION price_after_discount(book_isbn VARCHAR2) RETURN NUMBER;
    FUNCTION get_price_after_tax(book_isbn VARCHAR2) RETURN NUMBER;
END book_store;
/
CREATE OR REPLACE PACKAGE BODY book_store AS
    FUNCTION price_after_discount(book_isbn VARCHAR2)
        RETURN NUMBER IS discounted NUMBER;
        BEGIN
            SELECT retail - discount INTO discounted FROM books WHERE isbn = book_isbn;
            RETURN discounted;
        END;
        
    FUNCTION get_price_after_tax(book_isbn VARCHAR2)
        RETURN NUMBER IS taxed NUMBER;
        BEGIN
            taxed := price_after_discount(book_isbn) * 1.15;
            RETURN taxed;
        END;
END book_store;
/
DECLARE

BEGIN
--  BUILDING A CAR WITH TOOTHPICKS  
    dbms_output.put_line(book_store.get_price_after_tax('4981341710'));
--  HOLY GRAIL OF ORACLE
    dbms_output.put_line(book_store.get_price_after_tax('3957136468'));
END;