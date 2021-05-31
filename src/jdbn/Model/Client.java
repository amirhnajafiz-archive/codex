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

    /**
     * Constructor
     * @param email Each user has a unique ID
     */
    public Client(String email)
    {
        this.email = email;
        this.notes = new ArrayList<>();
    }

    public String getEmail()
    {
        return email;
    }

    public void addNote(Note note)
    {
        this.notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }
}
