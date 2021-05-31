package jdbn.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import jdbn.Model.Note;

/**
 * Database connector is created to make a connection to database
 * and managing the data manufacturing methods.
 *
 */
public class DataBaseConnector implements Runnable
{
    // Fields
    private volatile boolean killProcess = false;
    private Connection connection;
    private final String URL = "jdbc:sqlite:F:\\Desktop\\JDBC\\chinook.db";

    /**
     * This method kills our database thread.
     *
     */
    public void terminateProcess()
    {
        this.killProcess = true;
    }

    /**
     * This method stores our note into database.
     * @param notes list of notes
     * @param email user email
     * @return insertion message
     */
    public String saveNotes(ArrayList<Note> notes, String email)
    {
        String query = "insert into NP_Notes values(?, ?, ?, ?);"; // Insertion query
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (Note note : notes) // Iteration and adding batches
            {
                statement.setString(1, email);
                statement.setString(2, note.getHeader());
                statement.setString(3, note.getContent());
                statement.setDate(4, (Date) note.getDate());
                statement.addBatch();
            }
            int[] batchRowsAffected = statement.executeBatch();
            System.out.println("> Database updated : successfully "
                    + " | Rows effected : " + Arrays.toString(batchRowsAffected));
            return "Complete";
        } catch (SQLException exception) {
            System.out.println("> Database error : " + exception.getMessage());
        } finally {
            if (statement != null)
            {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("> Disconnection error : " + ex.getMessage());
                }
            }
        }
        return "System failed";
    }

    /**
     * This method gets a user notes from database.
     * @param email user email
     * @return the list of stored notes
     */
    public ArrayList<Note> loadNotes(String email)
    {
        Statement statement = null;
        String query = "select * from NP_Notes where user_mail = '" + email + "';"; // Query for getting user notes
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Note> notes = new ArrayList<>();
            while (resultSet.next())
            {
                Note note = new Note(resultSet.getString(2), resultSet.getString(3));
                notes.add(note);
            }
            return notes;
        } catch (SQLException exception) {
            System.out.println("> Database error : " + exception.getMessage());
            return null;
        } finally {
            if (statement != null)
            {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("> Disconnection error : " + ex.getMessage());
                }
            }
        }
    }

    /**
     * This method checks the user register and logging in status
     * @param email user email
     * @return login message
     */
    public String userLogIn(String email)
    {
        Statement statement = null;
        String query = "select * from NP_Users where user_mail='" + email + "';"; // Query for getting the user
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
            {
                return "User Exists";
            } else {
                query = "insert into NP_Users values ('" + email + "');"; // Registering login
                int rowsAffected = statement.executeUpdate(query);
                return "User Created";
            }
        } catch (SQLException exception) {
            System.out.println("> Database error : " + exception.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("> Disconnection error : " + e.getMessage());
                }
            }
        }
        return "> Problem in system";
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("> Connection to database : successfully");
            while (!killProcess) {
                Thread.onSpinWait();
            }
            System.out.println("> Disconnected from database : successfully");
        } catch (SQLException exception) {
            System.out.println("> Database error : " + exception.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException exc) {
                System.out.println("> Error in disconnecting : " + exc.getMessage());
            }
        }
    }
}
