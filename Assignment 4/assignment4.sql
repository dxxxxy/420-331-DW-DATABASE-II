create or replace PACKAGE book_store AS
--    FUNCTION price_after_discount(book_isbn VARCHAR2) RETURN NUMBER;
    FUNCTION get_price_after_tax(book_isbn VARCHAR2) RETURN NUMBER;
    FUNCTION book_purchasers(book_isbn VARCHAR2) RETURN customerarray;
    PROCEDURE show_purchases;
    PROCEDURE add_book(isbn_ IN VARCHAR2, title_ IN VARCHAR2, pubdate_ IN DATE, pubid_ IN NUMBER, cost_ IN NUMBER, retail_ IN NUMBER, discount_ IN NUMBER, category_ IN VARCHAR2);
    PROCEDURE rename_category(old IN VARCHAR2, new IN VARCHAR2);
    PROCEDURE add_publisher(PUBID_ IN NUMBER, NAME_ IN VARCHAR2, CONTACT_ IN VARCHAR2, PHONE_ IN VARCHAR2);
    category_not_found EXCEPTION;
    publisher_already_exists EXCEPTION;
END book_store;
/
    CREATE OR REPLACE TYPE customerarray IS VARRAY(100) OF NUMBER(4);
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
    
    PROCEDURE rename_category(old IN VARCHAR2, new IN VARCHAR2) IS
    i NUMBER(4);
    BEGIN
        SELECT COUNT(*) INTO i FROM BOOKS WHERE category = old;
        IF i > 0 THEN
            FOR arow IN (SELECT * FROM BOOKS WHERE category = old) LOOP
                UPDATE BOOKS SET category = new WHERE category = old;
            END LOOP;
        ELSE
            RAISE book_store.category_not_found;
        END IF;
    EXCEPTION
        WHEN book_store.category_not_found THEN
            dbms_output.put_line('This category does not exist!');
        WHEN others THEN
            dbms_output.put_line('The new category does not conform to the tables requirements!');
    END;
    
    PROCEDURE add_publisher(PUBID_ IN NUMBER, NAME_ IN VARCHAR2, CONTACT_ IN VARCHAR2, PHONE_ IN VARCHAR2) IS
    i NUMBER(4);
    BEGIN
        SELECT COUNT(*) INTO i FROM PUBLISHER WHERE name = NAME_;
        IF i > 0 THEN
            RAISE book_store.publisher_already_exists;
        ELSE
            INSERT INTO PUBLISHER VALUES(PUBID_, NAME_, CONTACT_, PHONE_);   
        END IF;
    EXCEPTION
        WHEN book_store.publisher_already_exists THEN
            dbms_output.put_line('This publisher already exist!');
        WHEN DUP_VAL_ON_INDEX THEN
            dbms_output.put_line('The PUBID must be unique!');
    END;
END book_store;
/
BEGIN
    book_store.rename_category('COMPUTER', 'COMPUTER SCIENCE');
END;
/
BEGIN
    book_store.rename_category('TEACHING', 'EDUCATION');
END;
/
BEGIN
    book_store.add_publisher('7', 'DAWSON PRINTING', 'JOHN SMITH', '111-555-2233');
END;
/
BEGIN
    book_store.add_publisher('8', 'PUBLISH OUR WAY', 'JANE TOMLIN', '010-410-0010');
END;
/
BEGIN
    COMMIT;
END;