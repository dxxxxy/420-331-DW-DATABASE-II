package studio.dreamys.assignment7;

import studio.dreamys.assignment7.object.Course;

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

    public void addCourse(String id, int term, int education, String name, String description, int clazz, int lecture, int homework, int total) {
        System.out.println("Enter the course id: ");
        String id = sc.nextLine();

        System.out.println("What term is this course in?");
        int term = Integer.parseInt(sc.nextLine());
        if (term < 1 || term > 6) {
            System.out.println("Invalid term");
            return;
        }

        System.out.println("Is this course concentration [1] or general [2] education?");
        int education = Integer.parseInt(sc.nextLine());
        if (education < 1 || education > 2) {
            System.out.println("Invalid education type");
            return;
        }

        System.out.println("What is the name of the course?");
        String name = sc.nextLine();

        System.out.println("What is the description of the course?");
        String description = sc.nextLine();

        System.out.println("How many hours of CLASS work per WEEK are there?");
        int clazz = Integer.parseInt(sc.nextLine());

        System.out.println("How many hours of LECTURE work per WEEK are there?");
        int lecture = Integer.parseInt(sc.nextLine());

        System.out.println("How many hours of HOMEWORK work per WEEK are there?");
        int homework = Integer.parseInt(sc.nextLine());

        System.out.println("How many hours of TOTAL work for the WHOLE SEMESTER are there?");
        int total = Integer.parseInt(sc.nextLine());

        /* finished collecting data */

        Course course = new Course(id, term, education, name, description, clazz, lecture, homework, total);
        course.addToDatabase(conn);
    }
}
