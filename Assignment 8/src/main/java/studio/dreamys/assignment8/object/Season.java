package studio.dreamys.assignment8.object;

import java.sql.*;

public class Season implements SQLData {
    public static final String SQL_TYPE_NAME = "SEASON_TYPE";

    private int season_id;
    private String season_name;

    public Season() {

    }

    public Season(int season_id, String season_name) {
        this.season_id = season_id;
        this.season_name = season_name;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    @Override
    public String toString() {
        return "Season{" +
                "season_id=" + season_id +
                ", season='" + season_name + '\'' +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return "SEASON_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        season_id = stream.readInt();
        season_name = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(season_id);
        stream.writeString(season_name);
    }

    public void addToDatabase(Connection conn) {
        try (CallableStatement cs = conn.prepareCall("call add_season(?)")) {
            cs.setObject(1, this);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
