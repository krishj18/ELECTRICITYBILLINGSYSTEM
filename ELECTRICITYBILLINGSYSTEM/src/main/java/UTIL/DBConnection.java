package UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:3306/electricity_db";
    private static  final String USER="root";
    private static final String PASSWORD="mysqlxkrish2182";

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (Exception e) {
            System.out.println("Connection failed: "+ e.getMessage());
            return null;
        }
    }


}
