package jdbn.Server;

import jdbn.DataBase.DataBaseConnector;
import jdbn.Model.Note;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private DataBaseConnector dbs;

    public ClientHandler(Socket socket, DataBaseConnector dbs)
    {
        this.socket = socket;
        this.dbs = dbs;
    }

    @Override
    public void run()
    {
        System.out.println(">> Connected: " + socket);
        try (
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
             )
        {
            String user_mail = in.next();
            String msg = dbs.userLogIn(user_mail);
            out.print(msg);

            ArrayList<Note> user_notes = dbs.loadNotes(user_mail);

            out.print("SOF");
            for (Note note : user_notes)
            {
                out.print(note.getHeader());
                out.print(note.getContent());
            }
            out.print("EOF");

            user_notes.clear();
            while (!in.next().equals("EOF"))
            {
                String title = in.next();
                String content = in.next();
                Note note = new Note(title, content);
                user_notes.add(note);
            }

            dbs.saveNotes(user_notes, user_mail);

        } catch (Exception e) {
            System.out.println(">> Client error:" + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("> Socket Client error : " + e.getMessage());
            }
            System.out.println(">> Closed: " + socket);
        }
    }
}
