DROP TABLE SEASON CASCADE CONSTRAINTS;
DROP TABLE TERM CASCADE CONSTRAINTS;
DROP TABLE EDUCATION CASCADE CONSTRAINTS;
DROP TABLE COURSE CASCADE CONSTRAINTS;

DROP TYPE COURSE_TYPE;
DROP TYPE TERM_TYPE;
DROP TYPE EDUCATION_TYPE;
DROP TYPE SEASON_TYPE;

CREATE TABLE SEASON(
    season_id NUMBER(1) PRIMARY KEY,
    season_name VARCHAR2(20) NOT NULL
);

CREATE TABLE TERM(
    term_id NUMBER(1) PRIMARY KEY,
    season_id NUMBER(1) REFERENCES SEASON(season_id) NOT NULL
);

CREATE TABLE EDUCATION(
    education_id NUMBER(1) PRIMARY KEY,
    education_name VARCHAR2(20) NOT NULL
);

CREATE TABLE COURSE(
    id VARCHAR2(11) PRIMARY KEY,
    term_id NUMBER(1) REFERENCES TERM(term_id) NOT NULL,
    education_id NUMBER(1) REFERENCES EDUCATION(education_id) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    description VARCHAR2(500) NOT NULL,
    class NUMBER(1) NOT NULL,
    lecture NUMBER(1) NOT NULL,
    homework NUMBER(1) NOT NULL
);

INSERT INTO SEASON VALUES (1, 'Fall');
INSERT INTO SEASON VALUES (2, 'Winter');

INSERT INTO TERM VALUES (1, 1);
INSERT INTO TERM VALUES (2, 2);
INSERT INTO TERM VALUES (3, 1);
INSERT INTO TERM VALUES (4, 2);
INSERT INTO TERM VALUES (5, 1);
INSERT INTO TERM VALUES (6, 2);

INSERT INTO EDUCATION VALUES (1, 'Concentration');
INSERT INTO EDUCATION VALUES (2, 'General');

INSERT INTO COURSE VALUES('420-110-DW', 1, 1, 'Programming 1',
                          'The course will introduce the student to the basic building blocks (sequential,selection and repetitive control structures) and modules (methods and classes)used to write a program. The student will use the Java programming language to implement the algorithms studied. The array data structure is introduced, and student will learn how to program with objects.',
                          3, 3, 3);

INSERT INTO COURSE VALUES ('420-210-DW', 2, 1, 'Programming 2',
                           'The course will introduce the student to basic object-oriented methodology inorder to design, implement, use and modify classes, to write programs in theJava language that perform interactive processing, array and string processing,and data validation. Object-oriented features such as encapsulation and inheritance will be explored.',
                           3, 3, 3);

CREATE OR REPLACE TYPE SEASON_TYPE AS OBJECT (
    season_id NUMBER(1),
    season_name VARCHAR2(20)
);

CREATE OR REPLACE TYPE TERM_TYPE AS OBJECT (
    term_id NUMBER(1),
    season SEASON_TYPE
);

CREATE OR REPLACE TYPE EDUCATION_TYPE AS OBJECT (
    education_id NUMBER(1),
    education_name VARCHAR2(20)
);

CREATE OR REPLACE TYPE COURSE_TYPE AS OBJECT (
    id VARCHAR2(11),
    term TERM_TYPE,
    education EDUCATION_TYPE,
    name VARCHAR2(30),
    description VARCHAR2(500),
    class NUMBER(1),
    lecture NUMBER(1),
    homework NUMBER(1)
);

CREATE OR REPLACE PROCEDURE ADD_SEASON(SEASON_IN IN SEASON_TYPE) AS
BEGIN
    INSERT INTO SEASON VALUES(SEASON_IN.season_id, SEASON_IN.season_name);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        UPDATE SEASON SET season_name = SEASON_IN.season_name WHERE season_id = SEASON_IN.season_id;
END;

CREATE OR REPLACE PROCEDURE ADD_TERM(TERM_IN IN TERM_TYPE) AS
BEGIN
    INSERT INTO TERM VALUES(TERM_IN.term_id, TERM_IN.season.season_id);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        UPDATE TERM SET season_id = TERM_IN.season.season_id WHERE term_id = TERM_IN.term_id;
END;

CREATE OR REPLACE PROCEDURE ADD_EDUCATION(EDUCATION_IN IN EDUCATION_TYPE) AS
BEGIN
    INSERT INTO EDUCATION VALUES(EDUCATION_IN.education_id, EDUCATION_IN.education_name);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        UPDATE EDUCATION SET education_name = EDUCATION_IN.education_name WHERE education_id = EDUCATION_IN.education_id;
END;

CREATE OR REPLACE PROCEDURE ADD_COURSE(COURSE_IN IN COURSE_TYPE) AS
BEGIN
    INSERT INTO COURSE VALUES(COURSE_IN.id, COURSE_IN.term.term_id, COURSE_IN.education.education_id, COURSE_IN.name, COURSE_IN.description, COURSE_IN.class, COURSE_IN.lecture, COURSE_IN.homework);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        UPDATE COURSE SET term_id = COURSE_IN.term.term_id, education_id = COURSE_IN.education.education_id, name = COURSE_IN.name, description = COURSE_IN.description, class = COURSE_IN.class, lecture = COURSE_IN.lecture, homework = COURSE_IN.homework WHERE id = COURSE_IN.id;
END;

COMMIT;
