package jdbn.Model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {

    private Socket socket;

    public Application(Socket socket)
    {
        this.socket = socket;
    }

    private String getMenu()
    {
        return "1. ADD NOTE\n" + "2. VIEW NOTES\n" + "3. SAVE\n" + "4. EXIT";
    }

    private Note createNote()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> (Title) ");
        String title = scanner.nextLine();
        System.out.print(">> (Content) ");
        String body = scanner.nextLine();
        return new Note(title, body);
    }

    public void run() {
        try (
                PrintWriter osr = new PrintWriter(socket.getOutputStream(), true);
                Scanner isr = new Scanner(socket.getInputStream())
        ) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("> Process interrupt : " + e.getMessage());
            }

            osr.flush();

            Scanner scanner = new Scanner(System.in);
            System.out.print(">> (Enter your email) ");
            String mail = scanner.next();

            osr.print(mail);
            System.out.println(isr.next());

            Client client = new Client(mail);

            System.out.println("Client '" + client.getEmail() + "' logged in.");

            while (!isr.next().equals("EOF")) {
                String title = isr.next();
                String content = isr.next();
                Note note = new Note(title, content);
                client.addNote(note);
            }

            boolean flag = true;
            while (flag) {
                System.out.println(getMenu());
                String command = scanner.next();

                switch (command) {
                    case "1":
                        client.addNote(createNote());
                        break;
                    case "2":
                        System.out.println(">> Notes:");
                        for (Note note : client.getAllNotes())
                            System.out.println(note);
                        System.out.println(">> End.");
                        break;
                    case "3":
                        osr.print("SOF");
                        for (Note note : client.getNotes()) {
                            osr.print(note.getHeader());
                            osr.print(note.getContent());
                        }
                        osr.print("EOF");
                        flag = false;
                        break;
                    case "4":
                        flag = false;
                        break;
                    default:
                        System.out.println(">> Wrong input !");
                }
            }

            System.out.println("Client '" + client.getEmail() + "' logged out.");

        } catch (IOException | NoSuchElementException e) {
            System.out.println("> Connection error : " + e.getMessage());
        } finally {
            System.out.println("> Program closed");
        }
    }
}
