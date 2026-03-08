import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    // Update these credentials to match your PostgreSQL setup
    private static final String URL = "jdbc:postgresql://localhost:5432/productmanagement";
    private static final String USER = "postgres";
    private static final String PASS = "123456"; // <--- CHANGE THIS

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}