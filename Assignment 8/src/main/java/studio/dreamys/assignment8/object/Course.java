package studio.dreamys.assignment8.object;

import java.sql.*;

public class Course implements SQLData {
    public static final String SQL_TYPE_NAME = "COURSE_TYPE";

    private String id;
    private Term term;
    private Education education;
    private String name;
    private String description;
    private int clazz;
    private int lecture;
    private int homework;

    public Course() {

    }

    public Course(String id, Term term, Education education, String name, String description, int clazz, int lecture, int homework) {
        this.id = id;
        this.term = term;
        this.education = education;
        this.name = name;
        this.description = description;
        this.clazz = clazz;
        this.lecture = lecture;
        this.homework = homework;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClazz() {
        return clazz;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    public int getLecture() {
        return lecture;
    }

    public void setLecture(int lecture) {
        this.lecture = lecture;
    }

    public int getHomework() {
        return homework;
    }

    public void setHomework(int homework) {
        this.homework = homework;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", term=" + term +
                ", education=" + education +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", clazz=" + clazz +
                ", lecture=" + lecture +
                ", homework=" + homework +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return SQL_TYPE_NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        id = stream.readString();
        term = stream.readObject(Term.class);
        education = stream.readObject(Education.class);
        name = stream.readString();
        description = stream.readString();
        clazz = stream.readInt();
        lecture = stream.readInt();
        homework = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(id);
        stream.writeObject(term);
        stream.writeObject(education);
        stream.writeString(name);
        stream.writeString(description);
        stream.writeInt(clazz);
        stream.writeInt(lecture);
        stream.writeInt(homework);
    }

    public void addToDatabase(Connection conn) {
        try (CallableStatement cs = conn.prepareCall("call add_course(?)")) {
            cs.setObject(1, this);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
