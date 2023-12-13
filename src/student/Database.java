package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String JDBC_CONNECTION = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
    private static final String DATABASE_USER = "COMP228_M23_sy_69";
    private static final String DATABASE_PASSWORD = "password";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(JDBC_CONNECTION, DATABASE_USER, DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeResources(ResultSet rs, PreparedStatement pst, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
