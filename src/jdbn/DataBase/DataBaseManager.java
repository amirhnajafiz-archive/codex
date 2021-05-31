package jdbn.DataBase;

import java.sql.*;

public class DataBaseManager
{
	public static String mainTable = "NP_Users";
	public static String dataTable = "NP_Notes";

	public static void initializeTables(Connection connection)
	{
		try {
			Statement statement = connection.createStatement();
			String mainTableQuery = "create table " + mainTable + "("
					+ "user_mail varchar(50),"
					+ "primary key(user_mail)"
					+ ");";
			String dataTableQuery = "create table " + dataTable + "("
					+ "user_mail varchar(50),"
					+ "note_title varchar(50),"
					+ "note_body varchar(200),"
					+ "note_date date,"
					+ "primary key (user_mail, note_title)"
					+ ");";
			statement.executeUpdate(mainTableQuery);
			statement.executeUpdate(dataTableQuery);
		} catch (SQLException ex) {
			System.out.println("> Initialize error : " + ex.getMessage());
		}
	}

    public static void main(String[] args)
    {
	    Connection connection = null;
	    try {
	        String URL = "jdbc:sqlite:F:\\Desktop\\JDBC\\chinook.db";
	        connection = DriverManager.getConnection(URL);
	        // Get information about tables in our database
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSetting = metaData.getTables(null, null, null, new String[]{"TABLE"});
			System.out.println("Printing TABLE_TYPE \"TABLE\" ");
			System.out.println("----------------------------------");
			while(resultSetting.next())
			{
				System.out.println(resultSetting.getString("TABLE_NAME"));
			}
			System.out.println("------");
			// Creating my table
			initializeTables(connection);
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
