package ContactFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

public class contactGreate {
    private static String contactsDirectory = "src/ContactFile/contacts";
    private static String contactTxt = "contacts.txt";

    private static List<contact> contacts;

    public static void main(String[] args) throws IOException {
        initializeContactsFile();
        contacts = loadContacts();

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while(!exit) {
            int choice = displayMenu(scanner);
            switch (choice){
                case 1:
                    viewContacts();
                    break;
                case 2:
                    addNewContact(scanner);
                    break;
                case 3:
                    searchContactByName(scanner);
                    break;
                case 4:
                    deleteExistingContact(scanner);
                    break;
                case 5:
                    saveContacts();
                    exit = true;
                    break;
                default:
                    System.out.println("Please try again!!");
            }
        }

    }

    private static void initializeContactsFile() throws IOException {
        Path pathToDirectory = Path.of(contactsDirectory);
        if (Files.notExists(pathToDirectory)) {
            Files.createDirectories(pathToDirectory);
        }
        Path directoryAndFile = Path.of(contactsDirectory, contactTxt);
        if (Files.notExists(directoryAndFile)) {
            Files.createFile(directoryAndFile);
        }

    }

    private static List<contact> loadContacts() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(contactsDirectory, contactTxt));
        return lines.stream()
                .map(line -> line.split(","))
                .map(parts -> new contact(parts[0], parts[1]))
                .collect(Collectors.toList());
    }

    private static int displayMenu (Scanner scanner) {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4 or 5): ");
        return scanner.nextInt();
    }
}
