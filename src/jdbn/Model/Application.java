package jdbn.Model;

import java.util.Scanner;

public class Application {

    public static String getMenu()
    {
        return "1. ADD NOTE\n" + "2. VIEW NOTES\n" + "3. EXIT";
    }

    public static Note createNote()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> (Title) ");
        String title = scanner.nextLine();
        System.out.print(">> (Content) ");
        String body = scanner.nextLine();
        return new Note(title, body);
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> (Enter your email) ");
        String mail = scanner.next();

        Client client = new Client(mail);

        System.out.println("Client '" + client.getEmail() + "' logged in.");

        boolean flag = true;
        while (flag)
        {
            System.out.println(getMenu());
            String command = scanner.next();

            switch (command)
            {
                case "1":
                    client.addNote(createNote());
                    break;
                case "2":
                    System.out.println(">> Notes:");
                    for(Note note : client.getNotes())
                        System.out.println(note);
                    System.out.println(">> End.");
                    break;
                case "3":
                    flag = false;
                    break;
                default:
                    System.out.println(">> Wrong input !");
            }
        }

        System.out.println("Client '" + client.getEmail() + "' logged out.");
    }
}
