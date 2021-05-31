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
     * Constructor
     * @param email Each user has a unique ID
     */
    public Client(String email)
    {
        this.email = email;
        newNotes = new ArrayList<>();
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes == null ? new ArrayList<>() : notes;
    }

    public String getEmail()
    {
        return email;
    }

    public void addNote(Note note)
    {
        this.newNotes.add(note);
        this.notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return newNotes;
    }

    public ArrayList<Note> getAllNotes() {
        return notes;
    }
}
