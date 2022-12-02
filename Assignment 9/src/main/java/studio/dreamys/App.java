package studio.dreamys;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Welcome, please enter your username and password to login.");
        IJLSecurity security;
        while (true) {
            try {
                //sign in to database
                String username = System.console().readLine("\nUsername: ");
                String password = String.valueOf(System.console().readPassword("Password: "));
                clearConsole();
                security = new JLSecurity(username, password);
                System.out.println("Welcome, " + username + "!");
                break;
            } catch (SQLException e) {
                System.out.println("There was an error while connecting to the database. Please try again.");
            }
        }

        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Create a new user");
            System.out.println("2. Login as a user");
            System.out.println("3. Exit");
            String option = System.console().readLine("\nOption: ");

            clearConsole();

            switch (option) {
                case "1": //signup to app
                    System.out.println("Please enter the username and password for the new user.\n");
                    String newUser = System.console().readLine("Username: ");
                    String newPassword = String.valueOf(System.console().readPassword("Password: "));

                    clearConsole();

                    security.CreateUser(newUser, newPassword);
                    break;
                case "2": //sign in to app
                    System.out.println("Please enter the username and password for the user you wish to login as.\n");
                    String login = System.console().readLine("Username: ");
                    String loginPassword = String.valueOf(System.console().readPassword("Password: "));

                    clearConsole();

                    if (security.Login(login, loginPassword)) {
                        System.out.println("Successfully logged in as " + login + "!");
                    }
                    break;
                case "3": //exit
                    running = false;
                    break;
                default:
                    clearConsole();
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }


    /**
     * Clears the console screen. Windows & Linux compatible.
     * @see <a href="https://stackoverflow.com/a/64038023/13063334">StackOverflow</a>
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else System.out.print("\033\143");
        } catch (IOException | InterruptedException ignored) {}
    }
}