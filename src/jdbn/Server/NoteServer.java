package jdbn.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NoteServer
{
    public static boolean serverOn = true;

    public static void main(String[] args)
    {
        try (var listener = new ServerSocket(59090)) {
            System.out.println("The JDB server is running ...");

            Thread terminate = new Thread(new Terminator(listener));
            terminate.start();

            ExecutorService service = Executors.newCachedThreadPool();

            while (serverOn)
            {
                service.execute(new ClientHandler(listener.accept()));
            }
        } catch (IOException e) {
            System.out.println("> Server error : " + e.getMessage());
        }
    }
}

class Terminator implements Runnable
{
    private ServerSocket listener;

    public Terminator(ServerSocket listener)
    {
        this.listener = listener;
    }

    @Override
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">> Enter terminate to shutdown the server.");
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
