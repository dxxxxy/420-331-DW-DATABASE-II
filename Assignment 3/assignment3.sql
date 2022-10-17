create or replace PACKAGE book_store AS
--    FUNCTION price_after_discount(book_isbn VARCHAR2) RETURN NUMBER;
    TYPE customerarray IS VARRAY(100) OF NUMBER(4);
    FUNCTION get_price_after_tax(book_isbn VARCHAR2) RETURN NUMBER;
    FUNCTION book_purchasers(book_isbn VARCHAR2) RETURN customerarray;
    PROCEDURE show_purchases;
    PROCEDURE add_book(isbn_ IN VARCHAR2, title_ IN VARCHAR2, pubdate_ IN DATE, pubid_ IN NUMBER, cost_ IN NUMBER, retail_ IN NUMBER, discount_ IN NUMBER, category_ IN VARCHAR2);

    END book_store;
/
create or replace PACKAGE BODY book_store AS
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
     
    FUNCTION book_purchasers(book_isbn VARCHAR2)
        RETURN customerarray IS customer#array customerarray;
        BEGIN
            SELECT O.CUSTOMER# BULK COLLECT INTO customer#array FROM BOOKS B
            JOIN ORDERITEMS OI USING(ISBN)
            JOIN ORDERS O USING(ORDER#)
            WHERE ISBN = book_isbn;
            RETURN customer#array;
        END;
    
    PROCEDURE show_purchases IS
        customer#array customerarray;
        names VARCHAR2(1000);
        name VARCHAR2(100);
    BEGIN
        FOR arow IN (SELECT * FROM BOOKS) LOOP
            customer#array := book_purchasers(arow.ISBN);
            FOR i IN 1 .. customer#array.COUNT LOOP
                SELECT FIRSTNAME || ' ' || LASTNAME INTO name FROM CUSTOMERS WHERE CUSTOMER# = customer#array(i);
                names := names || name || ', ';
            END LOOP;
            dbms_output.put_line('ISBN: ' || arow.ISBN || ' Title: ' || arow.TITLE || ' Names: ' || names);
            names := '';
        END LOOP;
    END;
    
    PROCEDURE add_book(isbn_ IN VARCHAR2, title_ IN VARCHAR2, pubdate_ IN DATE, pubid_ IN NUMBER, cost_ IN NUMBER, retail_ IN NUMBER, discount_ IN NUMBER, category_ IN VARCHAR2) IS
        c NUMBER(4);
    BEGIN
        SELECT COUNT(*) INTO c FROM BOOKS B WHERE B.isbn = isbn_;
        IF c > 0 THEN
            UPDATE BOOKS B SET 
                B.title = title_,
                B.pubdate = pubdate_,
                B.pubid = pubid_,
                B.cost = cost_,
                B.retail = retail_,
                B.discount = discount_,
                B.category = category_
            WHERE B.isbn = isbn_;
        ELSE
            INSERT INTO BOOKS VALUES(isbn_, title_, pubdate_, pubid_, cost_, retail_, discount_, category_);
        END IF;
    END;
END book_store;
/
BEGIN
    book_store.show_purchases();
    book_store.add_book('2135229', 'Serhiy Fed', CURRENT_DATE, null, 30, 10, 5, 'DATABASE');
    book_store.add_book('0401140733', 'REVENGE OF MICKEY', '14-DEC-05', 1, 99.99, 22, null, 'FAMILY LIFE');
    COMMIT;
END;