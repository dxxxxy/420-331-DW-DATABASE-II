package studio.dreamys.assignment8.object;

import java.sql.*;

public class Education implements SQLData {
    public static final String SQL_TYPE_NAME = "EDUCATION_TYPE";

    private int education_id;
    private String education_name;

    public Education() {

    }

    public Education(int education_id, String education_name) {
        this.education_id = education_id;
        this.education_name = education_name;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public String getEducation_name() {
        return education_name;
    }

    public void setEducation_name(String education_name) {
        this.education_name = education_name;
    }

    @Override
    public String toString() {
        return "Education{" +
                "education_id=" + education_id +
                ", education='" + education_name + '\'' +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return SQL_TYPE_NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        education_id = stream.readInt();
        education_name = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(education_id);
        stream.writeString(education_name);
    }

    public void addToDatabase(Connection conn) {
        try (CallableStatement cs = conn.prepareCall("call add_education(?)")) {
            cs.setObject(1, this);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
