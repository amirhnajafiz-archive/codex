package jdbn.Model;

import java.sql.Date;

/**
 * The note class where we store the data of a single
 * line note for a user.
 *
 */
public class Note {
    // Fields
    private String header;
    private String content;
    private java.sql.Date date;

    /**
     * Constructor of Note class.
     *
     * @param header Note title
     * @param content Note body
     */
    public Note(String header, String content)
    {
        this.header = header;
        this.content = content;
        this.date = new Date(System.currentTimeMillis());
    }

    /**
     * Header getter method.
     *
     * @return note header
     */
    public String getHeader()
    {
        return header;
    }

    /**
     * Content getter method.
     *
     * @return note content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Date getter method.
     *
     * @return note last modified date
     */
    public java.sql.Date getDate()
    {
        return date;
    }

    @Override
    public String toString() {
        return "Note { " +
                "header = '" + header + '\'' +
                ", content = '" + content + '\'' +
                ", date = " + date +
                " }";
    }
}
