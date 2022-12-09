DROP TABLE JLUSERS;
DROP TYPE JLUSER_TYPE;

CREATE TABLE JLUSERS (
    userID VARCHAR(20) PRIMARY KEY,
    salt RAW(16) NOT NULL,
    hash RAW(64) NOT NULL,
    failedLoginCount NUMBER(1) NOT NULL
);

CREATE TYPE JLUSER_TYPE AS OBJECT (
    userID VARCHAR(20),
    salt RAW(16),
    hash RAW(64),
    failedLoginCount NUMBER(1)
);

CREATE OR REPLACE PROCEDURE ADD_USER(user IN JLUSER_TYPE) AS
BEGIN
    INSERT INTO JLUSERS VALUES (user.userID, user.salt, user.hash, user.failedLoginCount);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        UPDATE_USER(user);
END;

CREATE OR REPLACE PROCEDURE UPDATE_USER(user IN JLUSER_TYPE) AS
BEGIN
    UPDATE JLUSERS SET salt = user.salt,
                       hash = user.hash,
                       failedLoginCount = user.failedLoginCount
        WHERE userID = user.userID;
END;

CREATE OR REPLACE FUNCTION GET_USER(userID_ IN VARCHAR2) RETURN JLUSER_TYPE IS
    user JLUSER_TYPE;
BEGIN
    SELECT JLUSER_TYPE(userID, salt, hash, failedLoginCount) INTO user FROM JLUSERS WHERE userID = userID_;
    RETURN user;
END;