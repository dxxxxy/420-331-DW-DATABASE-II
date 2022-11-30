package studio.dreamys;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class User implements IUser, SQLData {
    public static final String SQL_TYPE_NAME = "JLUSER_TYPE";

    private String userID;
    private byte[] salt;
    private byte[] hash;
    private long failedLoginCount;

    public User(String userID, byte[] salt, byte[] hash, long failedLoginCount) {
        this.userID = userID;
        this.salt = salt;
        this.hash = hash;
        this.failedLoginCount = failedLoginCount;
    }

    @Override
    public String getUser() {
        return userID;
    }

    @Override
    public byte[] getSalt() {
        return salt;
    }

    @Override
    public byte[] getHash() {
        return hash;
    }

    @Override
    public long getFailedLoginCount() {
        return failedLoginCount;
    }

    @Override
    public void setFailedLoginCount(long count) {
        failedLoginCount = count;
    }

    @Override
    public String getSQLTypeName() {
        return SQL_TYPE_NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        userID = stream.readString();
        salt = stream.readBytes();
        hash = stream.readBytes();
        failedLoginCount = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(userID);
        stream.writeBytes(salt);
        stream.writeBytes(hash);
        stream.writeInt((int) failedLoginCount);
    }
}
