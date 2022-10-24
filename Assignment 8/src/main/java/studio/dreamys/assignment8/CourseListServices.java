package studio.dreamys.assignment8;

import studio.dreamys.assignment8.object.Course;
import studio.dreamys.assignment8.object.Education;
import studio.dreamys.assignment8.object.Season;
import studio.dreamys.assignment8.object.Term;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseListServices {
    private final Connection conn;

    public CourseListServices(String username, String password) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca", username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCourse(int season_id, String season, int term_id, int education_id, String education, String id, String name, String description, int clazz, int lecture, int homework) {
        new Season(season_id, season).addToDatabase(conn);
        new Term(term_id, season_id).addToDatabase(conn);
        new Education(education_id, education).addToDatabase(conn);
        new Course(id, term_id, education_id, name, description, clazz, lecture, homework).addToDatabase(conn);
    }
}
