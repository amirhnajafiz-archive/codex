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

	        Statement statement = null;
	        String query = "select * from albums";

	        try {
	        	statement = connection.createStatement();
	        	ResultSet resultSet = statement.executeQuery(query);
	        	while (resultSet.next())
				{
					System.out.println(resultSet.getString("title"));
				}
			} catch (SQLException exc) {
	        	exc.printStackTrace();
			} finally {
	        	if (statement != null) statement.close();
			}

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
