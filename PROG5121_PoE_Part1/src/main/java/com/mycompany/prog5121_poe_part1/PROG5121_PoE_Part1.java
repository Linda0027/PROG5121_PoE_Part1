package com.mycompany.prog5121_poe_part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PROG5121_PoE_Part1 {
    // Part 3 Requirement 1: Parallel arrays populated dynamically (no hard-coding)
    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();
    
    // Master list holding full Message objects for relational lookups
    static ArrayList<Message> masterCatalog = new ArrayList<>();
    private static int globalCounter = 0;
    private static final String JSON_FILE_PATH = "stored_messages.json";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login authSystem = new Login();
        
        System.out.println("=========================================");
        System.out.println("       ACCOUNT REGISTRATION SYSTEM       ");
        System.out.println("=========================================");
        
        System.out.print("Enter First Name: ");
        String first = input.nextLine();
        System.out.print("Enter Last Name:  ");
        String last = input.nextLine();
        authSystem.setUserNames(first, last);
        
        System.out.print("Create Username:  ");
        String user = input.nextLine();
        System.out.print("Create Password:  ");
        String pass = input.nextLine();
        System.out.print("Enter Cell Number: ");
        String cell = input.nextLine();
        
        String regStatus = authSystem.registerUser(user, pass, cell);
        System.out.println("\nStatus: " + regStatus);
        
        if (!regStatus.contains("successfully captured")) {
            System.out.println("Registration failed. Execution stopped.");
            return;
        }
        
        System.out.println("\n=========================================");
        System.out.println("               USER LOGIN                ");
        System.out.println("=========================================");
        
        System.out.print("Enter Username: ");
        String checkUser = input.nextLine();
        System.out.print("Enter Password: ");
        String checkPass = input.nextLine();
        
        boolean isLogged = authSystem.loginUser(checkUser, checkPass);
        System.out.println(authSystem.returnLoginStatus(isLogged));
        
        if (!isLogged) {
            System.out.println("Access Denied.");
            return;
        }

        // Load rubric test data (Messages 1-5) and sync JSON file
        initializeSystemData();

        System.out.println("\nWelcome to QuickChat.");

        int mainSelection = 0;
        while (mainSelection != 3) {
            System.out.println("\n----- QUICKCHAT MAIN MENU -----");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Select an option (1-4): ");
            
            try {
                mainSelection = input.nextInt();
                input.nextLine(); 
                
                switch (mainSelection) {
                    case 1: processMessageCreation(input); break;
                    case 2: System.out.println("\nComing Soon."); break;
                    case 3: System.out.println("\nExiting QuickChat. Goodbye!"); break;
                    case 4: executeStoredMessagesMenu(input); break;
                    default: System.out.println("Invalid selection. Please choose 1-4."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                input.next();
                mainSelection = 0;
            }
        }
        input.close();
    }

    // Populates the 5 test datasets from Page 19 and syncs the JSON file
    public static void initializeSystemData() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        masterCatalog.clear();
        globalCounter = 0;

        addMessageToSystem("+27834557896", "Did you get the cake?", "Sent");
        addMessageToSystem("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        addMessageToSystem("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        addMessageToSystem("0838884567", "It is dinner time !", "Sent");
        addMessageToSystem("+27838884567", "Ok, I am leaving without you.", "Stored");

        saveStoredMessagesToJSON();
        readJSONIntoStoredArray();
    }

    public static void addMessageToSystem(String cell, String content, String flag) {
        globalCounter++;
        Message msg = new Message(globalCounter, cell, content, flag);
        masterCatalog.add(msg);
        
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
        
        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(content);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(content);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessages.add(content);
            saveStoredMessagesToJSON();
        }
    }

    // Attribution: Standard Java NIO File I/O adapted from Oracle Java SE Documentation (Files.writeString)
    public static void saveStoredMessagesToJSON() {
        StringBuilder jsonBuilder = new StringBuilder("[\n");
        boolean first = true;
        for (Message m : masterCatalog) {
            if (m.getStatus().equalsIgnoreCase("Stored")) {
                if (!first) jsonBuilder.append(",\n");
                jsonBuilder.append(m.toJSON());
                first = false;
            }
        }
        jsonBuilder.append("\n]");

        try {
            Files.writeString(Paths.get(JSON_FILE_PATH), jsonBuilder.toString());
        } catch (IOException e) {
            System.out.println("Warning: Could not write to JSON file: " + e.getMessage());
        }
    }

    // Attribution: Regex JSON parsing adapted from Java Pattern/Matcher standard library guidelines
    public static void readJSONIntoStoredArray() {
        storedMessages.clear();
        try {
            if (!Files.exists(Paths.get(JSON_FILE_PATH))) return;
            String jsonContent = Files.readString(Paths.get(JSON_FILE_PATH));
            
            Pattern pattern = Pattern.compile("\"message\":\\s*\"(.*?)\"");
            Matcher matcher = pattern.matcher(jsonContent);
            while (matcher.find()) {
                storedMessages.add(matcher.group(1).replace("\\\"", "\""));
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not read JSON file: " + e.getMessage());
        }
    }

    private static void processMessageCreation(Scanner scan) {
        System.out.print("Enter how many messages you wish to enter: ");
        int batchLimit = scan.nextInt();
        scan.nextLine(); 

        for (int i = 0; i < batchLimit; i++) {
            System.out.println("\n--- Message [" + (i + 1) + " of " + batchLimit + "] ---");
            System.out.print("Enter Recipient Cell Number: ");
            String phone = scan.nextLine().trim();
            
            String content = "";
            while (true) {
                System.out.print("Enter Message (Max 250 characters): ");
                content = scan.nextLine();
                if (content.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters.");
                } else {
                    System.out.println("Message ready to send.");
                    break;
                }
            }

            System.out.println("\nChoose Action Option:");
            System.out.println("1. Send Message\n2. Disregard Message\n3. Store Message to send later");
            System.out.print("Enter choice: ");
            int flowAction = scan.nextInt();
            scan.nextLine(); 

            String flag = "Disregard";
            if (flowAction == 1) {
                flag = "Sent";
                System.out.println("Message successfully sent");
            } else if (flowAction == 3) {
                flag = "Stored";
                System.out.println("Message successfully stored");
            } else {
                System.out.println("Press 0 to delete the message");
            }

            addMessageToSystem(phone, content, flag);
            Message latest = masterCatalog.get(masterCatalog.size() - 1);
            System.out.println("\n" + latest.printMessages());
        }
        System.out.println("\nTotal messages processed: " + batchLimit);
    }

    // Part 3 Requirement 2 (a - f): Stored Messages Reporting Menu
    private static void executeStoredMessagesMenu(Scanner scan) {
        int subChoice = 0;
        while (subChoice != 7) {
            System.out.println("\n===== STORED MESSAGES & REPORTING MENU =====");
            System.out.println("1. Display sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message ID and display recipient and message");
            System.out.println("4. Search for all messages stored/sent for a particular recipient");
            System.out.println("5. Delete a message using the message hash");
            System.out.println("6. Display a report that lists full details of all sent messages");
            System.out.println("7. Return to Main Menu");
            System.out.print("Select option (1-7): ");
            
            try {
                subChoice = scan.nextInt();
                scan.nextLine(); 
                
                switch (subChoice) {
                    case 1:
                        System.out.println("\n--- Senders & Recipients (Stored Messages) ---");
                        System.out.println(getSendersAndRecipients());
                        break;
                    case 2:
                        System.out.println("\n--- Longest Stored Message ---");
                        System.out.println(getLongestMessage());
                        break;
                    case 3:
                        System.out.print("Enter Message ID to search: ");
                        String idQuery = scan.nextLine().trim();
                        System.out.println(searchByMessageID(idQuery));
                        break;
                    case 4:
                        System.out.print("Enter Recipient Cell Number: ");
                        String cellQuery = scan.nextLine().trim();
                        System.out.println(searchByRecipient(cellQuery));
                        break;
                    case 5:
                        System.out.print("Enter Message Hash to delete: ");
                        String hashQuery = scan.nextLine().trim();
                        System.out.println(deleteByMessageHash(hashQuery));
                        break;
                    case 6:
                        System.out.println("\n--- FULL MESSAGE REPORT ---");
                        System.out.println(generateFullReport());
                        break;
                    case 7:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid option. Choose 1-7.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Numbers only.");
                scan.next();
                subChoice = 0;
            }
        }
    }

    // 2a. Display sender and recipient of all stored messages
    public static String getSendersAndRecipients() {
        StringBuilder sb = new StringBuilder();
        for (Message m : masterCatalog) {
            if (m.getStatus().equalsIgnoreCase("Stored")) {
                sb.append("Sender: Registered User | Recipient: ").append(m.getRecipientCell()).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No stored messages found.";
    }

    // 2b. Display the longest message
    public static String getLongestMessage() {
        String longest = "";
        for (Message m : masterCatalog) {
            if (m.getMessageContent().length() > longest.length()) {
                longest = m.getMessageContent();
            }
        }
        return longest;
    }

    // 2c. Search for a message ID and return recipient + message
    public static String searchByMessageID(String id) {
        for (Message m : masterCatalog) {
            if (m.getMessageID().equals(id)) {
                return "Recipient: " + m.getRecipientCell() + " | Message: " + m.getMessageContent();
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search for all messages sent or stored regarding a particular recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : masterCatalog) {
            if (m.getRecipientCell().equals(recipient) && 
               (m.getStatus().equalsIgnoreCase("Sent") || m.getStatus().equalsIgnoreCase("Stored"))) {
                if (sb.length() > 0) sb.append("\n");
                sb.append(m.getMessageContent());
            }
        }
        return sb.length() > 0 ? sb.toString() : "No messages found for recipient.";
    }

    // 2e. Delete a message using a message hash
    public static String deleteByMessageHash(String hash) {
        for (int i = 0; i < masterCatalog.size(); i++) {
            Message m = masterCatalog.get(i);
            if (m.getMessageHash().equalsIgnoreCase(hash)) {
                String deletedText = m.getMessageContent();
                
                masterCatalog.remove(i);
                messageHashes.remove(m.getMessageHash());
                messageIDs.remove(m.getMessageID());
                sentMessages.remove(deletedText);
                disregardedMessages.remove(deletedText);
                storedMessages.remove(deletedText);
                saveStoredMessagesToJSON();
                
                return "Message \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message Hash not found.";
    }

    // 2f. Display report listing full details (Message Hash, Recipient, Message)
    public static String generateFullReport() {
        StringBuilder sb = new StringBuilder();
        for (Message m : masterCatalog) {
            if (m.getStatus().equalsIgnoreCase("Sent")) {
                sb.append("Message Hash: ").append(m.getMessageHash()).append("\n")
                  .append("Recipient:    ").append(m.getRecipientCell()).append("\n")
                  .append("Message:      ").append(m.getMessageContent()).append("\n")
                  .append("-----------------------------------------\n");
            }
        }
        return sb.toString().trim();
    }
}

