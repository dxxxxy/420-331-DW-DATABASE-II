import java.sql.Connection;

public interface IJLSecurity {
/**
 * Creates a new user in the database with the provided Username and password.
 * @param user The user name to be used when accessing data.
 * @param password The users password.
 * @implNote The password will not be stored as plain text.
 */
    void CreateUser(String user, String password);

    /**
     * Logs the user is with a user name and password
     * @param user The user name to be used when accessing data
     * @param password THe users password
     * @return Returns true if successfully authenticated, false if otherwise.
     * @implNote After 5 unsucessful attempts in 2 minutes, the user will be locked out.
     */
    boolean Login(String user, String password);
}