package jdbn.Server;

import jdbn.DataBase.DataBaseConnector;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             )
        {
            String user_mail = in.next();
            out.print(dbs.userLogIn(user_mail));

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
