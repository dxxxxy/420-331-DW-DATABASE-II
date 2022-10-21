package studio.dreamys.assignment7.object;

import java.sql.*;

public class Course implements SQLData {
    private String id;
    private int term_id;
    private int education_id;
    private String name;
    private String description;
    private int clazz;
    private int lecture;
    private int homework;
    private int total;

    public Course() {

    }

    public Course(String id, int term_id, int education_id, String name, String description, int clazz, int lecture, int homework, int total) {
        this.id = id;
        this.term_id = term_id;
        this.education_id = education_id;
        this.name = name;
        this.description = description;
        this.clazz = clazz;
        this.lecture = lecture;
        this.homework = homework;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", term_id=" + term_id +
                ", education_id=" + education_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", clazz=" + clazz +
                ", lecture=" + lecture +
                ", homework=" + homework +
                ", total=" + total +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return "COURSE_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        id = stream.readString();
        term_id = stream.readInt();
        education_id = stream.readInt();
        name = stream.readString();
        description = stream.readString();
        clazz = stream.readInt();
        lecture = stream.readInt();
        homework = stream.readInt();
        total = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(id);
        stream.writeInt(term_id);
        stream.writeInt(education_id);
        stream.writeString(name);
        stream.writeString(description);
        stream.writeInt(clazz);
        stream.writeInt(lecture);
        stream.writeInt(homework);
        stream.writeInt(total);
    }

    public void addToDatabase(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO COURSE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, id);
            stmt.setInt(2, term_id);
            stmt.setInt(3, education_id);
            stmt.setString(4, name);
            stmt.setString(5, description);
            stmt.setInt(6, clazz);
            stmt.setInt(7, lecture);
            stmt.setInt(8, homework);
            stmt.setInt(9, total);
            stmt.executeUpdate();

            System.out.println(this + " added to the database.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(this + " already exists in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
