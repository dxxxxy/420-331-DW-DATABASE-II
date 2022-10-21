package studio.dreamys.assignment7.object;

import java.sql.*;

public class Education implements SQLData {
    private int education_id;
    private String education;

    public Education() {

    }

    public Education(int education_id, String education) {
        this.education_id = education_id;
        this.education = education;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return "Education{" +
                "education_id=" + education_id +
                ", education='" + education + '\'' +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return "EDUCATION_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        education_id = stream.readInt();
        education = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(education_id);
        stream.writeString(education);
    }

    public void addToDatabase(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO EDUCATION VALUES (?, ?)")) {
            stmt.setInt(1, education_id);
            stmt.setString(2, education);
            stmt.executeUpdate();

            System.out.println(this + " added to the database.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(this + " already exists in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
