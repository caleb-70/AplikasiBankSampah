package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/db_bank_sampah";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver MySQL belum ditambahkan ke library project.", ex);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
