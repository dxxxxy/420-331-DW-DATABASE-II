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
        byte[] hash;
        byte[] salt;

        try {
            //create secure salt
            salt = new byte[16];
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);

            //create secure keyspec
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 5, 64 * 8);

            //create hash
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();

            //update db (add, if already added: update)
            UpdateDB(new User(user, salt, hash, 0));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("Error creating user");
        }
    }

    @Override
    public boolean Login(String user, String password) {
        IUser dbUser = getUser(user);

        byte[] salt = dbUser.getSalt();
        byte[] hash = dbUser.getHash();
        byte[] check_hash;

        //create secure keyspec
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 2, hash.length * 8);

        try {
            //create hash
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            check_hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("Error logging in");
            return false;
        }

        //check if hashes match
        return Arrays.equals(check_hash, hash);
    }

    private IUser getUser(String username) {
        IUser user = null;

        try (CallableStatement cs = conn.prepareCall("{ ? = call GET_USER(?) }")) {
            cs.registerOutParameter(1, Types.STRUCT);
            cs.setString(2, username);
            cs.execute();

            user = (IUser) cs.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting user");
        }

        return user;
    }

    private void UpdateDB(IUser user) {
        try (CallableStatement cs = conn.prepareCall("{ call ADD_USER(?)}")) {
            cs.setObject(1, user);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating user");
        }
    }
}
