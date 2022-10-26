package studio.dreamys.assignment8;

import studio.dreamys.assignment8.object.Course;
import studio.dreamys.assignment8.object.Education;
import studio.dreamys.assignment8.object.Season;
import studio.dreamys.assignment8.object.Term;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class CourseListServices {
    private final Connection conn;

    public CourseListServices(String username, String password) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca", username, password);
            Map<String, Class<?>> map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put(Course.SQL_TYPE_NAME, Course.class);
            map.put(Education.SQL_TYPE_NAME, Education.class);
            map.put(Season.SQL_TYPE_NAME, Season.class);
            map.put(Term.SQL_TYPE_NAME, Term.class);
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

    public void addCourse(String id, int season_id, String season_name, int term_id, int education_id, String education, String name, String description, int clazz, int lecture, int homework) {
        Season season_ = new Season(season_id, season_name);
        Term term = new Term(term_id, season_);
        Education education_ = new Education(education_id, education);

        season_.addToDatabase(conn);
        term.addToDatabase(conn);
        education_.addToDatabase(conn);

        Course course = new Course(id, term, education_, name, description, clazz, lecture, homework);

        course.addToDatabase(conn);
    }
}
