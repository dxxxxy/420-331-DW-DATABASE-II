package studio.dreamys.assignment7;

import studio.dreamys.assignment7.object.Course;
import studio.dreamys.assignment7.object.Education;
import studio.dreamys.assignment7.object.Season;
import studio.dreamys.assignment7.object.Term;

public class App {
    public static void main(String[] args) {
        System.out.println("Enter your username: ");
        String username = System.console().readLine();

        System.out.println("Enter your password: ");
        String password = String.valueOf(System.console().readPassword());

        //open connection
        CourseListServices courseListServices = new CourseListServices(username, password);

        Season season = new Season(1, "Fall"); //fall season
        Term term = new Term(3, 1); //3rd term
        Education education = new Education(1, "Concentration"); //class type
        Course course = new Course(
                "420-310-DW",
                3,
                1,
                "Programming III",
                "The course will enhance the students’ knowledge of object-oriented programming and Java to produce stand-alone applications employing reusable objects, data structures and the Java collections framework. The concepts inheritance, polymorphism, data abstraction and programming to interfaces reused to design software. Students are introduced to software version controlled effective team collaboration.",
                3,
                3,
                3,
                90); //course

        courseListServices.addCourse(season, term, education, course);

        //close connection
        courseListServices.close();
    }
}