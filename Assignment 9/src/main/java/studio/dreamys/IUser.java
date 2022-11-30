import java.util.Date;

public interface IUser {
    /**
     * Gets the salt
     * @return
     */
    byte[] getSalt();
    /**
     * Gets the username
     * @return
     */
    String getUser();
    /**
     * Gets the hash
     * @return
     */
    byte[] getHash();

    /**
     * Gets the failed login count
     * @return
     */
    long getFailedLoginCount();

    /**
     * Sets the current failed login count
     * @param count
     */
    void setFailedLoginCount(long count);
}
