package studio.dreamys.assignment8.object;

import java.sql.*;

public class Term implements SQLData {
    public static final String SQL_TYPE_NAME = "TERM_TYPE";

    private int term_id;
    private Season season;

    public Term() {

    }

    public Term(int term_id, Season season) {
        this.term_id = term_id;
        this.season = season;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "Term{" +
                "term_id=" + term_id +
                ", season=" + season +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return SQL_TYPE_NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        term_id = stream.readInt();
        season = stream.readObject(Season.class);
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(term_id);
        stream.writeObject(season);
    }

    public void addToDatabase(Connection conn) {
        try (CallableStatement cs = conn.prepareCall("call add_term(?)")) {
            cs.setObject(1, this);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
