package studio.dreamys;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class JLSecurity implements IJLSecurity {
    private final Connection conn;

    public JLSecurity(String username, String password) throws SQLException {
        conn = DriverManager.getConnection("jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca", username, password);
        Map<String, Class<?>> typeMap = conn.getTypeMap();
        typeMap.put(User.SQL_TYPE_NAME, User.class);
        conn.setTypeMap(typeMap);
    }

    @Override
    public void CreateUser(String user, String password) {
        //init
        byte[] hash;
        byte[] salt = new byte[16];

        //check if user already exists
        IUser dbUser = getUser(user);
        if (dbUser != null) {
            System.out.println("This user already exists. Please log in.");
            return;
        }

        //register non-existing user
        try {
            //create secure salt
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);

            //create secure keyspec
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 5, 64 * 8);

            //create hash
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();

            //update db (add new user)
            UpdateDB(new User(user, salt, hash, 0));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("There was an error while hashing your password. Please try again.");
        }
    }

    @Override
    public boolean Login(String user, String password) {
        //check if user exists
        IUser dbUser = getUser(user);
        if (dbUser == null) {
            System.out.println("This user was not found in the database. Please create a new user.");
            return false;
        }

        //check if failedLoginCount > 5
        if (dbUser.getFailedLoginCount() > 4) {
            System.out.println("This user has been locked out due to too many failed login attempts.");
            return false;
        }

        //init
        byte[] salt = dbUser.getSalt();
        byte[] hash = dbUser.getHash();
        byte[] check_hash;

        //create secure keyspec
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 5, hash.length * 8);

        try {
            //create hash
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            check_hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("There was an error while hashing your password. Please try again.");
            return false;
        }

        //check if hashes match
        boolean same = Arrays.equals(hash, check_hash);

        if (!same) {
            //update existing user
            System.out.println("Incorrect password. Please try again.");
            dbUser.setFailedLoginCount(dbUser.getFailedLoginCount() + 1);
            System.out.println("Failed login count: " + dbUser.getFailedLoginCount());
            UpdateDB(dbUser);
        }

        return same;
    }

    private IUser getUser(String username) {
        IUser user = null;

        try (CallableStatement cs = conn.prepareCall("{ ? = call GET_USER(?) }")) {
            cs.registerOutParameter(1, Types.STRUCT, User.SQL_TYPE_NAME);
            cs.setString(2, username);
            cs.execute();

            user = (IUser) cs.getObject(1);
        } catch (SQLException ignored) {}

        return user;
    }

    private void UpdateDB(IUser user) {
        try (CallableStatement cs = conn.prepareCall("{ call ADD_USER(?) }")) {
            cs.setObject(1, user);
            cs.execute();

            System.out.println("The database was successfully updated.");
        } catch (SQLException e) {
            System.out.println("There was an error while updating the database. Please try again.");
        }
    }
}
