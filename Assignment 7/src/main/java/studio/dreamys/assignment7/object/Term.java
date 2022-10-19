package studio.dreamys.assignment7.object;

import java.sql.*;

public class Term implements SQLData {
    private int term_id;
    private int season_id;

    public Term() {

    }

    public Term(int term_id, int season_id) {
        this.term_id = term_id;
        this.season_id = season_id;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    @Override
    public String toString() {
        return "Term{" +
                "term_id=" + term_id +
                ", season_id=" + season_id +
                '}';
    }


    @Override
    public String getSQLTypeName() throws SQLException {
        return "TERM_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.term_id = stream.readInt();
        this.season_id = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.term_id);
        stream.writeInt(this.season_id);
    }

    public void addToDatabase(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO TERM VALUES (?, ?)")) {
            stmt.setInt(1, this.term_id);
            stmt.setInt(2, this.season_id);
            stmt.executeUpdate();

            if (!stmt.isClosed()) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
