package jdbn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main
{

    public static void main(String[] args)
    {
	    Connection connection = null;

	    try {
	        String URL = "jdbc:sqlite:F:\\Desktop\\JDBC\\chinook.db";
	        connection = DriverManager.getConnection(URL);

	        System.out.println(">> Connected");
        } catch (SQLException e) {
	        e.printStackTrace();
        } finally {
	        try {
	            if (connection != null)
	                connection.close();
            } catch (SQLException ex) {
	            ex.printStackTrace();
            }
        }
    }
}
