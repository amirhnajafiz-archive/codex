package jdbn.DataBase;

import java.sql.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class DataBaseManager
{
	public static String mainTable = "NP_Users";
	public static String dataTable = "NP_Notes";

	private static String users_query = "select * from '" + DataBaseManager.mainTable + "'";
	private static String data_query = "select * from '" + DataBaseManager.dataTable + "'";

	private static void helpMenu()
	{
		System.out.println(
						"\nGuid:\n" +
						"init 			: initialize the tables of JDBNote\n" +
						"insert users   : insert manual users to database\n" +
						"insert note    : insert new notes to database\n" +
						"delete user    : remove users from database\n" +
						"delete note    : remove notes from database\n" +
						"users 			: see all users\n" +
						"notes 			: see all notes\n" +
						"tables 		: see all database tables\n" +
						"terminate      : exit from JDBNote database manager\n"
		);
	}

	private static Vector<String> getInput(Scanner scanner)
    {
        Vector<String> vector = new Vector<>();
        String temp;
        boolean flag = true;
        while (flag)
        {
            System.out.print("> (END for exit) ");
            temp = scanner.next();
            if (temp.equals("END"))
                flag = false;
            else
                vector.add(temp);
        }
        return vector;
    }

    private static Vector<String[]> getNotesInput(Scanner scanner)
    {
        Vector<String[]> vector = new Vector<>();
        String temp;
        boolean flag = true;
        while (flag)
        {
            System.out.print("> ( email,title,content | END for exit ) ");
            temp = scanner.nextLine();
            if (temp.equals("END"))
                flag = false;
            else {
                String[] array = temp.split(",");
                if (array.length == 3)
                    vector.add(array);
                else
                    System.out.println(">>> Not valid data");
            }
        }
        return vector;
    }

	private static void user_interface(Connection connection)
	{
		Scanner scanner = new Scanner(System.in);
		boolean flag = true;
		String order;

		helpMenu();
		System.out.println(">> Welcome to JDBNote database manager:");
		while (flag)
		{
			try {
				System.out.print("> ");
				order = scanner.nextLine();
				switch (order) {
					case "init":
						initializeTables(connection);
						break;
					case "insert users":
						insertManualUser(connection, getInput(scanner));
						break;
					case "insert note":
						insertManualNote(connection, getNotesInput(scanner));
						break;
					case "delete user":
						removeManualUser(connection, getInput(scanner));
						break;
					case "delete note":
						removeManualNote(connection, getInput(scanner));
						break;
					case "users":
						getUsers(connection);
						break;
					case "notes":
						getNotes(connection);
						break;
					case "tables":
						dataBaseTables(connection);
						break;
					case "help":
						helpMenu();
						break;
					case "terminate":
						flag = false;
						break;
					default:
						System.out.println(">>> No such command.");
				}
			} catch (SQLException e) {
				System.out.println(">>> " + e.getMessage());
			} finally {
				System.out.println("------");
				System.out.println("------\n");
			}
		}
	}

	private static void initializeTables(Connection connection)
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

	private static void insertManualUser(Connection connection, Vector<String> newList) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement("insert into '" + mainTable + "'" + "values(?);");
		for (String s: newList)
		{
			preparedStatement.setString(1, s);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

	private static void removeManualUser(Connection connection, Vector<String> newList) throws SQLException
	{
		Statement statement = connection.createStatement();
		for (String s : newList)
		{
			String query = "delete from '" + mainTable + "'" + "where user_mail = '" + s + "';";
			statement.executeUpdate(query);
			removeManualNote(connection, new Vector<>(Collections.singletonList(s)));
		}
	}

	private static void removeManualNote(Connection connection, Vector<String> newList) throws SQLException
	{
		Statement statement = connection.createStatement();
		for (String s : newList)
		{
			String query = "delete from '" + dataTable + "'" + "where user_mail = '" + s + "';";
			statement.executeUpdate(query);
		}
	}

	private static void insertManualNote(Connection connection, Vector<String[]> newList) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement("insert into '" + dataTable + "'" + "values(?, ?, ?, ?);");
		for (String[] strings : newList)
		{
			for (int i = 1; i < 4; i++)
			{
				preparedStatement.setString(i, strings[i-1]);
			}
			preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

	private static void dataBaseTables(Connection connection) throws SQLException
	{
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
		System.out.println("------\n");
	}

	private static void getUsers(Connection connection) throws SQLException
	{
		// Get information about users
		Statement statement = connection.createStatement();
		ResultSet data = statement.executeQuery(DataBaseManager.users_query);
		System.out.println("Printing \"Users\"");
		System.out.println("----------------------------------");
		while (data.next())
		{
			System.out.println("User: " + data.getString(1));
		}
		System.out.println("------");
		System.out.println("------\n");
		statement.close();
		data.close();
	}

	private static void getNotes(Connection connection) throws SQLException
	{
		Statement statement = connection.createStatement();
		ResultSet data = statement.executeQuery(DataBaseManager.data_query);
		System.out.println("Printing \"Notes\"");
		System.out.println("----------------------------------");
		while (data.next())
		{
			System.out.print("User: " + data.getString(1) + "| ");
			System.out.print("Title: " + data.getString(2) + "\n");
			System.out.print("Content:\n" + data.getString(3) + "\n");
			System.out.print("Last modified: " + data.getDate(4) + "\n");
			System.out.println("---");
		}
		System.out.println("------");
	}

    public static void main(String[] args)
    {
	    Connection connection = null;
	    try {
	        String URL = "jdbc:sqlite:F:\\Desktop\\JDBC\\chinook.db";
	        connection = DriverManager.getConnection(URL);

			user_interface(connection);

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
