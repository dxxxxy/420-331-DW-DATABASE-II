package studio.dreamys.assignment7;

import studio.dreamys.assignment7.object.Course;
import studio.dreamys.assignment7.object.Education;
import studio.dreamys.assignment7.object.Season;
import studio.dreamys.assignment7.object.Term;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseListServices {
    private Connection conn;
    private Scanner sc = new Scanner(System.in);

    public CourseListServices(String username, String password) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca", username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCourse(Season season, Term term, Education education, Course course) {
        season.addToDatabase(conn);
        term.addToDatabase(conn);
        education.addToDatabase(conn);

        try {
            course.addToDatabase(conn);

            System.out.println("Course added successfully!");
            System.out.println(course);
        } catch (Exception e) {
            System.out.println("Could not add course!");
        }
    }
}
