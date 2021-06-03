package jdbn.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable
{
    private Socket socket;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        System.out.println(">> Connected: " + socket);
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (in.hasNextLine()) {
                out.println(in.nextLine().toUpperCase());
            }
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
