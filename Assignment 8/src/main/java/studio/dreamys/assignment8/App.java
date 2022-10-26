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
                "420-510-DW",
                1, "Fall",
                5,
                1,
                "Concentration",
                "Programming IV",
                "The course will focus on the use of algorithms and data structures to simulate real-life phenomena using an appropriate gaming framework. Projects are implemented using an object-oriented language.",
                3,
                3,
                3);

        System.out.println("Course added!");

        //close connection
        courseListServices.close();
    }
}