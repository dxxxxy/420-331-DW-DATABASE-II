package studio.dreamys.assignment8.object;

import java.sql.*;

public class Season implements SQLData {
    private int season_id;
    private String season;

    public Season() {

    }

    public Season(int season_id, String season) {
        this.season_id = season_id;
        this.season = season;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "Season{" +
                "season_id=" + season_id +
                ", season='" + season + '\'' +
                '}';
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return "SEASON_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        season_id = stream.readInt();
        season = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(season_id);
        stream.writeString(season);
    }

    public void addToDatabase(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO SEASON VALUES (?, ?)")) {
            stmt.setInt(1, season_id);
            stmt.setString(2, season);
            stmt.executeUpdate();

            System.out.println(this + " added to the database.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(this + " already exists in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
