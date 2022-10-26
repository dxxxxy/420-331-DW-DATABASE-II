package studio.dreamys.assignment8;

public class App {
    public static void main(String[] args) {
        System.out.println("Enter your username: ");
        String username = System.console().readLine();

        System.out.println("Enter your password: ");
        String password = String.valueOf(System.console().readPassword());

        //open connection
        CourseListServices courseListServices = new CourseListServices(username, password);

        //add course
        courseListServices.addCourse(
                "420-310-DW",
                2, "Fall",
                3,
                1,
                "Concentration",
                "Programming III",
                "The course will enhance the students’ knowledge of object-oriented programming and Java to produce stand-alone applications employing reusable objects, data structures and the Java collections framework. The concepts inheritance, polymorphism, data abstraction and programming to interfaces reused to design software. Students are introduced to software version controlled effective team collaboration.",
                3,
                3,
                3);

        System.out.println("Course added!");

        //close connection
        courseListServices.close();
    }
}