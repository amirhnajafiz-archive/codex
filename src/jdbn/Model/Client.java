package jdbn.Model;

import java.util.ArrayList;

/**
 * The client class where we store the user information.
 *
 */
public class Client {
    // Fields
    private String email;
    private ArrayList<Note> notes;
    private ArrayList<Note> newNotes;

    /**
     * Constructor of client.
     *
     * @param email Each user has a unique ID
     */
    public Client(String email)
    {
        this.email = email;
        newNotes = new ArrayList<>();
    }

    /**
     * Getter for user email.
     *
     * @return users email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * A method for adding a new note.
     *
     * @param note gets a new Note class
     */
    public void addNote(Note note)
    {
        this.newNotes.add(note);
        this.notes.add(note);
    }

    /**
     * Get the list of users new notes.
     *
     * @return list of new notes that are not in database
     */
    public ArrayList<Note> getNotes() {
        return newNotes;
    }

    /**
     * Get the list of all new and old users notes.
     *
     * @return list of all notes
     */
    public ArrayList<Note> getAllNotes() {
        return notes;
    }
}
