package contactsFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Optional;

public class ContactsCreate {
    private static String contactsDirectory = "src/contactsFile/contacts";
    private static String contactsTXT = "contacts.txt";
    private static List<Contact> contacts;
    public static void main(String[] args) throws IOException{
        initializeContactsFile();
        contacts = loadContacts();

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit){
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
                    System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }
    private static void initializeContactsFile() throws IOException {
        Path pathToDirectory = Path.of(contactsDirectory);
        if(Files.notExists(pathToDirectory)){
            Files.createDirectories(pathToDirectory);
        }
        Path directoryAndFile = Path.of(contactsDirectory, contactsTXT);
        if(Files.notExists(directoryAndFile)){
            Files.createFile(directoryAndFile);
        }
    }
    private static List<Contact> loadContacts() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(contactsDirectory, contactsTXT));
        return lines.stream()
                .map(line -> line.split(","))
                .map(parts -> new Contact(parts[0], parts[1]))
                .collect(Collectors.toList());
    }
    private static int displayMenu(Scanner scanner) {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4 or 5): ");
        return scanner.nextInt();
    }
    private static void viewContacts() {
        System.out.println("\nName       | Phone number");
        System.out.println("--------------------------");
        for (Contact contact : contacts){
            formatPrint(contact);
        }
    }
    private static void formatPrint(Contact contact){
        String formattedPhoneNumber;
        if(contact.phoneNumber.length() > 7) {
            formattedPhoneNumber = contact.phoneNumber.substring(0, 3) + "-" + contact.phoneNumber.substring(3, 6)
                    + "-" + contact.phoneNumber.substring(6);
        } else {
            formattedPhoneNumber = contact.phoneNumber.substring(0, 3) + "-" + contact.phoneNumber.substring(3);
        }
        System.out.printf("%-11s| %s%n", contact.name,formattedPhoneNumber);
    }
    private static void addNewContact(Scanner scanner) {
        System.out.print("Enter contact name: ");
        String name = scanner.next();
        if(contacts.stream().anyMatch(contact -> contact.name.equalsIgnoreCase(name))) {
            System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)\n", name);
            String overwrite = scanner.next();
            if(overwrite.equalsIgnoreCase("yes") || overwrite.equalsIgnoreCase("y")){
                deleteOldContact(name);
                System.out.print("Enter new contact phone number: ");
                String phoneNumber = scanner.next();
                contacts.add(new Contact(name, phoneNumber));
                System.out.println("Contact was overwrite successfully.\n");
            } else {
                System.out.println("Please enter the contact information again.\n");
            }
        } else {
            System.out.print("Enter contact phone number: ");
            String phoneNumber = scanner.next();
            contacts.add(new Contact(name, phoneNumber));
            System.out.println("Contact added successfully.\n");
        }
    }
    private static void deleteOldContact(String name){
        Contact contactToDelete = null;
        for(Contact contact : contacts){
            if (contact.name.equalsIgnoreCase(name)){
                contactToDelete = contact;
                break;
            }
        }
        if(contactToDelete != null) {
            contacts.remove(contactToDelete);
        }
    }
    private static void searchContactByName(Scanner scanner){
        scanner.nextLine();
        System.out.print("Enter contact name: ");
        String name = scanner.next();
        Optional<Contact> foundContact = contacts.stream()
                .filter(contact -> contact.name.equalsIgnoreCase(name))
                .findFirst();

        if (foundContact.isPresent()) {
            System.out.println("\nName       | Phone number");
            System.out.println("--------------------------");
            formatPrint(foundContact.get());
        } else {
            System.out.println("Contact not found.\n");
        }
    }
    private static void deleteExistingContact(Scanner scanner){
        scanner.nextLine();
        System.out.print("Enter contact name: ");
        String name = scanner.nextLine();
        Contact contactToDelete = null;
        for(Contact contact : contacts){
            if (contact.name.equalsIgnoreCase(name)){
                contactToDelete = contact;
                break;
            }
        }
        if(contactToDelete != null) {
            contacts.remove(contactToDelete);
            System.out.println("Contact deleted successfully.\n");
        } else {
            System.out.println("Contact not found.\n");
        }
    }
    private static void saveContacts() throws IOException {
        List<String> contactsToSave = contacts.stream()
                .map(contact -> contact.name + "," + contact.phoneNumber)
                .collect(Collectors.toList());
        Files.write(Path.of(contactsDirectory, contactsTXT), contactsToSave);
        System.out.println("Contacts saved. Exiting...");
    }
}
