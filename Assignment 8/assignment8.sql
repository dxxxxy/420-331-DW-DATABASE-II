DROP TABLE SEASON CASCADE CONSTRAINTS;
DROP TABLE TERM CASCADE CONSTRAINTS;
DROP TABLE EDUCATION CASCADE CONSTRAINTS;
DROP TABLE COURSE CASCADE CONSTRAINTS;

CREATE TABLE SEASON(
    season_id NUMBER(1) PRIMARY KEY,
    season VARCHAR2(20) NOT NULL
);

CREATE TABLE TERM(
    term_id NUMBER(1) PRIMARY KEY,
    season_id NUMBER(1) REFERENCES SEASON(season_id) NOT NULL
);

CREATE TABLE EDUCATION(
    education_id NUMBER(1) PRIMARY KEY,
    education VARCHAR2(20) NOT NULL
);

CREATE TABLE COURSE(
    id VARCHAR2(11) PRIMARY KEY,
    term_id NUMBER(1) REFERENCES TERM(term_id) NOT NULL,
    education_id NUMBER(1) REFERENCES EDUCATION(education_id) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    description VARCHAR2(500) NOT NULL,
    class NUMBER(1) NOT NULL,
    lecture NUMBER(1) NOT NULL,
    homework NUMBER(1) NOT NULL,
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

CREATE OR REPLACE TYPE COURSE_TYPE AS OBJECT (
    id VARCHAR2(11),
    term_id NUMBER(1),
    education_id VARCHAR2(30),
    name VARCHAR2(30),
    description VARCHAR2(200),
    class NUMBER(1),
    lecture NUMBER(1),
    homework NUMBER(1),
);

CREATE OR REPLACE TYPE TERM_TYPE AS OBJECT (
    term_id NUMBER(1),
    season_id NUMBER(1)
);

CREATE OR REPLACE TYPE EDUCATION_TYPE AS OBJECT (
    education_id NUMBER(1),
    education VARCHAR2(20)
);

CREATE OR REPLACE TYPE SEASON_TYPE AS OBJECT (
    season_id NUMBER(1),
    season VARCHAR2(20)
);

COMMIT;