package jdbn;

import java.math.BigDecimal;
import java.sql.*;

public class Main
{

    public static void main(String[] args)
    {
	    Connection connection = null;

	    try {
	        String URL = "jdbc:sqlite:F:\\Desktop\\JDBC\\chinook.db";
	        connection = DriverManager.getConnection(URL);

	        // Get metadata about tables
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSeting = metaData.getTables(null, null, null, new String[]{"TABLE"});
			System.out.println("Printing TABLE_TYPE \"TABLE\" ");
			System.out.println("----------------------------------");
			while(resultSeting.next())
			{
				System.out.println(resultSeting.getString("TABLE_NAME"));
			}

			System.out.println("------");

			// Get metadata about a Special table ( Albums )
			ResultSet columns = metaData.getColumns(null,null, "albums", null);
			while(columns.next())
			{
				String columnName = columns.getString("COLUMN_NAME");
				String datatype = columns.getString("DATA_TYPE");
				String columnsize = columns.getString("COLUMN_SIZE");
				String decimaldigits = columns.getString("DECIMAL_DIGITS");
				String isNullable = columns.getString("IS_NULLABLE");
				String is_autoIncrment = columns.getString("IS_AUTOINCREMENT");
				//Printing results
				System.out.println(columnName + "---" + datatype + "---" + columnsize + "---" + decimaldigits + "---" + isNullable + "---" + is_autoIncrment);
			}

			System.out.println("------");

	        // Simple statement test
	        Statement statement = null;
	        String query = "select * from albums";

	        try {
	        	statement = connection.createStatement();
	        	ResultSet resultSet = statement.executeQuery(query);

	        	while (resultSet.next())
				{
					System.out.println(resultSet.getString(2));
				}
			} catch (SQLException exc) {
	        	exc.printStackTrace();
			} finally {
	        	if (statement != null) statement.close();
			}

	        // Prepared statement test
			String prepState = "insert into albums values (?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(prepState);

			preparedStatement.setInt(1, 400);
			preparedStatement.setString(2, "Concert for aliens");
			preparedStatement.setInt(3, 10);

			int rowsAffected = preparedStatement.executeUpdate();

			System.out.println("Added successfully with rows affected: " + rowsAffected);

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
