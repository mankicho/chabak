package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String MAIN_USER_ID = "dky";
    private static final String SUB_USER_ID = "jongseol";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    public static Connection get() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://211.222.234.14:3306/chabak?serverTimezone=Asia/Seoul", MAIN_USER_ID, PASSWORD);
            System.out.println("connection is completed");
        }
        return connection;
    }
}
