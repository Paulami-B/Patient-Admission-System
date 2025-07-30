package patientAdmission;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/patientadmission";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}