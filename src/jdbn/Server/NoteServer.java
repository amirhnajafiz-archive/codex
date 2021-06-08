package jdbn.Server;

import jdbn.DataBase.DataBaseConnector;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Note server is our application multi-thread server which connects to
 * a mySQL database and handles the clients.
 *
 */
public class NoteServer
{
    public static boolean serverOn = true;

    /**
     * Server starter method.
     *
     * @param args list of input arguments
     */
    public static void main(String[] args)
    {
        DataBaseConnector dbc = null;
        ExecutorService service = null;

        try (var listener = new ServerSocket(59090)) {
            System.out.println("The JDB server is running ...");

            Thread terminate = new Thread(new Terminator(listener));
            terminate.start();

            dbc = new DataBaseConnector();
            Thread runner = new Thread(dbc);
            runner.start();

            Runtime runtime = Runtime.getRuntime();
            DataBaseConnector finalDbc = dbc;
            runtime.addShutdownHook(new Thread() {
                @Override
                public void run()
                {
                    finalDbc.terminateProcess();
                }
            });

            service = Executors.newCachedThreadPool();

            while (serverOn)
            {
                service.execute(new ClientHandler(listener.accept(), dbc));
            }

        } catch (IOException e) {
            System.out.println("> Server error : " + e.getMessage());
        } finally {
            if (dbc != null)
            {
                dbc.terminateProcess();
            }
            if (service != null)
            {
                service.shutdownNow();
            }
        }
    }
}

/**
 * Terminator is a thread that allows us to shutdown the server
 * at anytime we want, In case of anything goes wrong.
 *
 */
class Terminator implements Runnable
{
    private ServerSocket listener;

    /**
     * Terminator constructor.
     *
     * @param listener Server socket
     */
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
