package studio.dreamys;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome, please enter your username and password to login.");
        String username = System.console().readLine("Username: ");
        String password = String.valueOf(System.console().readPassword("Password: "));

        while (true) {
            try {
                IJLSecurity security = new JLSecurity(username, password);

                System.out.println("Welcome, " + username + "!");
                System.out.println("Please select an option:");
                System.out.println("1. Create a new user");
                System.out.println("2. Login as a user");
                System.out.println("3. Exit");
                String option = System.console().readLine("Option: ");
                switch (option) {
                    case "1":
                        System.out.println("Please enter the username and password for the new user.");
                        String newUser = System.console().readLine("Username: ");
                        String newPassword = String.valueOf(System.console().readPassword("Password: "));
                        security.CreateUser(newUser, newPassword);
                        break;
                    case "2":
                        System.out.println("Please enter the username and password for the user you wish to login as.");
                        String login = System.console().readLine("Username: ");
                        String loginPassword = String.valueOf(System.console().readPassword("Password: "));
                        if (security.Login(login, loginPassword)) {
                            System.out.println("Successfully logged in as " + login + "!");
                        } else {
                            System.out.println("Failed to login as " + login + "!");
                        }
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
                break;
            } catch (SQLException e) {
                System.out.println("Invalid username or password, please try again.");
            }
        }
    }
}