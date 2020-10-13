package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chabak?serverTimezone=Asia/Seoul", "chabak", "1234");
        }
        return connection;
    }
}
