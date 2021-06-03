package jdbn.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Scanner;


public class NoteServer
{
    public static boolean serverOn = true;

    public static void main(String[] args)
    {
        try (var listener = new ServerSocket(59090)) {
            System.out.println("The date server is running...");

            Thread terminate = new Thread(new Terminator(listener));
            terminate.start();

            while (serverOn) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                }
            }
        } catch (IOException e) {
            System.out.println("> Server error : " + e.getMessage());
        }
    }
}

class Terminator implements Runnable {
    private ServerSocket listener;

    public Terminator(ServerSocket listener)
    {
        this.listener = listener;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> Enter terminate to shutdown the server.");
        while (true)
        {
            String cmd = scanner.next();
            if (cmd.equals("terminate"))
            {
                NoteServer.serverOn = false;
                try {
                    listener.close();
                } catch (IOException e) {
                    System.out.println("> Server error : " + e.getMessage());
                }
                return;
            } else {
                System.out.println(">> Enter terminate to shutdown the server.");
            }
        }
    }
}
