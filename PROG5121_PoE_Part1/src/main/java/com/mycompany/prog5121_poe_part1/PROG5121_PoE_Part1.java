package com.mycompany.prog5121_poe_part1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class PROG5121_PoE_Part1 {
    private static ArrayList<Message> messageDatabase = new ArrayList<>();
    private static int totalMessageAccumulator = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login authSystem = new Login();
        
        System.out.println("=========================================");
        System.out.println("       ACCOUNT REGISTRATION SYSTEM       ");
        System.out.println("=========================================");
        
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();
        
        authSystem.setUserNames(firstName, lastName);
        
        System.out.print("Create Username: ");
        String username = input.nextLine();
        System.out.print("Create Password: ");
        String password = input.nextLine();
        System.out.print("Enter Cell Number: ");
        String cellNumber = input.nextLine();
        
        System.out.println("\n--- Registration Status ---");
        String regMessage = authSystem.registerUser(username, password, cellNumber);
        System.out.println(regMessage);
        
        // Only proceed if registration was completely successful
        if (!regMessage.contains("successfully captured")) {
            System.out.println("Registration failed. Exiting system application.");
            return;
        }
        
        // --- PHASE 2: LOGIN SYSTEM VERIFICATION ---
        System.out.println("\n=========================================");
        System.out.println("               USER LOGIN                ");
        System.out.println("=========================================");
        
        System.out.print("Enter Username to Login: ");
        String loginUser = input.nextLine();
        System.out.print("Enter Password to Login: ");
        String loginPass = input.nextLine();
        
        boolean loggedIn = authSystem.loginUser(loginUser, loginPass);
        System.out.println(authSystem.returnLoginStatus(loggedIn));
        
        // Part 2 Rule: Users should only be able to send messages if they have logged in successfully
        if (!loggedIn) {
            System.out.println("Access Denied. Invalid credentials.");
            return;
        }

        // --- PHASE 3: QUICKCHAT APPLICATION LOOP ---
        System.out.println("\n=========================================");
        System.out.println("Welcome to QuickChat.");
        System.out.println("=========================================");

        int menuChoice = 0;
        
        do {
            System.out.println("\n----- QUICKCHAT MAIN MENU -----");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Select an option: ");
            
            try {
                menuChoice = input.nextInt();
                input.nextLine(); // Clear buffer channel
                
                switch (menuChoice) {
                    case 1:
                        // Rule: Users define how many messages they wish to enter when the application starts
                        System.out.print("Enter how many messages you wish to enter: ");
                        int maxMessages = input.nextInt();
                        input.nextLine(); // Clear buffer
                        
                        for (int i = 0; i < maxMessages; i++) {
                            System.out.println("\n--- New Message (" + (i + 1) + " of " + maxMessages + ") ---");
                            System.out.print("Enter Recipient Cell Number: ");
                            String recipient = input.nextLine().trim();
                            
                            String content = "";
                            boolean validContent = false;
                            
                            while (!validContent) {
                                System.out.print("Enter Message Content (Max 250 characters): ");
                                content = input.nextLine();
                                
                                if (content.length() > 250) {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                } else {
                                    System.out.println("Message ready to send.");
                                    validContent = true;
                                }
                            }
                            
                            totalMessageAccumulator++;
                            Message msg = new Message(totalMessageAccumulator, recipient, content);
                            
                            System.out.println(msg.checkRecipientCell());
                            System.out.println("Message ID generated: <" + msg.getMessageID() + ">");
                            
                            System.out.println("\nChoose Action:");
                            System.out.println("1. Send Message\n2. Disregard Message\n3. Store Message to send later");
                            System.out.print("Enter choice option: ");
                            int actionChoice = input.nextInt();
                            input.nextLine(); // Clear buffer
                            
                            System.out.println(msg.SentMessage(actionChoice));
                            
                            if (actionChoice == 1 || actionChoice == 3) {
                                messageDatabase.add(msg);
                            }
                            
                            // Rule: Display details in order: Message ID, Message Hash, Recipient, Message
                            System.out.println("\n--- Message Details Summary ---");
                            System.out.println(msg.printMessages());
                        }
                        
                        // Rule: Total number of messages accumulated displayed once all sent
                        System.out.println("\n=========================================");
                        System.out.println("Total messages processed in this batch: " + maxMessages);
                        System.out.println("Total accumulated session store size: " + messageDatabase.size());
                        System.out.println("=========================================");
                        break;
                        
                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                        
                    case 3:
                        System.out.println("\nExiting QuickChat workspace app. Goodbye!");
                        break;
                        
                    default:
                        System.out.println("Invalid selection. Choose an option between 1 and 3.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please provide a valid numerical selection index option.");
                input.next(); // Flush buffer
                menuChoice = 0;
            }
        } while (menuChoice != 3);
        
        input.close();
    }
}
