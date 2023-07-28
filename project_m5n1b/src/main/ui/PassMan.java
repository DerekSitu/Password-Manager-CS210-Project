package ui;

import model.Login;
import model.LoginCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Password manager application
public class PassMan {
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private LoginCollection collection;

    // EFFECTS: runs the password manager application
    public PassMan() {
        confirmIdentity();
    }

    // EFFECTS: continues to main application if "password" is inputted
    private void confirmIdentity() {
        boolean confirmedIdentity = false;
        String attempt;
        input = new Scanner(System.in);

        while (!confirmedIdentity) {
            System.out.println("Please enter the master password. Hint: try 'password'.");
            attempt = input.next();
            if (attempt.equals("password")) {
                confirmedIdentity = true;
                runPassMan();
            } else {
                confirmIdentity();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // Based on TellerApp from class.
    private void runPassMan() {
        boolean keepGoing = true;
        String command;
        collection = new LoginCollection();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Session ended.");
    }

    // EFFECTS: displays menu of options to user
    // From TellerApp.
    private void displayMenu() {
        System.out.println("\nSelect from");
        System.out.println("\tv -> view logins");
        System.out.println("\ta -> add a login");
        System.out.println("\tc -> change a password");
        System.out.println("\td -> delete a login");
        System.out.println("\ts -> save logins");
        System.out.println("\tl -> load saved logins");
        System.out.println("\tq -> close the application");
    }

    // EFFECTS: displays all logins.
    private void displayLogins() {
        System.out.println("Here are your logins:");
        for (int i = 0; i < collection.size(); i++) {
            Login l = collection.getLogin(i);
            System.out.println(" ");
            System.out.println("Site: " + l.siteName);
            System.out.println("User: " + l.userName);
            System.out.println("Encrypted Password: " + l.encryptedPassword);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // From TellerApp.
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("v")) {
            displayLogins();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("c")) {
            doChange();
        } else if (command.equals("s")) {
            saveCollection();
        } else if (command.equals("l")) {
            loadCollection();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a login
    private void doDelete() {
        System.out.println("Enter the index of the login to be deleted (index starts at 0):");
        displayLogins();
        int index = input.nextInt();
        try {
            collection.deleteLogin(index);
            System.out.println("Login at index " + index + " deleted.");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid index. Try again.");
            doDelete();
        }
    }

    // MODIFIES: this
    // EFFECTS: changes a password
    private void doChange() {
        System.out.println("Enter the index of the login to be changed (index starts at 0):");
        displayLogins();
        int index = input.nextInt();
        if ((0 <= index) && (index < collection.size())) {
            System.out.println("Enter your new password:");
            String pw = input.next();
            (collection.getLogin(index)).setPassword(pw);
            System.out.println("Password changed.");
        } else {
            System.out.println("Invalid index. Try again.");
            doChange();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a login to collection
    private void doAdd() {
        System.out.println("Enter the site name of the new login:");
        String sn = input.next();
        System.out.println("Enter your username on the site:");
        String un = input.next();
        System.out.println("Enter your password on the site:");
        String pw = input.next();
        collection.addLogin(sn, un, pw);
        System.out.println("Login added.");
    }

    // EFFECTS: saves the workroom to file
    // From JsonSerializationDemo.
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved collection to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    // From JsonSerializationDemo.
    private void loadCollection() {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded collection from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
