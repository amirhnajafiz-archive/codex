package jdbn.Model;

import java.util.Date;

/**
 * The note class where we store the data of a single
 * line note for a user.
 *
 */
public class Note {
    // Fields
    private String header;
    private String content;
    private Date date;

    /**
     * Constructor
     * @param header Note title
     * @param content Note body
     */
    public Note(String header, String content)
    {
        this.header = header;
        this.content = content;
        this.date = new Date();
    }

    public String getHeader()
    {
        return header;
    }

    public String getContent()
    {
        return content;
    }

    public Date getDate()
    {
        return date;
    }
}
