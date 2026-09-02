package com.mycompany.prog5121_poe_part1;

import java.util.Scanner;

public class PROG5121_PoE_Part1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login authSystem = new Login();

        System.out.println("=========================================");
        System.out.println("      ACCOUNT REGISTRATION SYSTEM        ");
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

        System.out.print("Enter Cell Number (e.g., +27838968976): ");
        String cellNumber = input.nextLine();

        System.out.println("\n--- Registration Status ---");
        String regMessage = authSystem.registerUser(username, password, cellNumber);
        System.out.println(regMessage);

        // Check if registration process completed successfully
        if (regMessage.contains("Username successfully captured.")) {
            System.out.println("\n=========================================");
            System.out.println("               USER LOGIN                ");
            System.out.println("=========================================");
            
            System.out.print("Enter Username: ");
            String loginUser = input.nextLine();

            System.out.print("Enter Password: ");
            String loginPass = input.nextLine();

            boolean isSuccessful = authSystem.loginUser(loginUser, loginPass);
            String loginStatusText = authSystem.returnLoginStatus(isSuccessful);
            
            System.out.println("\n--- Login Status ---");
            System.out.println(loginStatusText);
        } else {
            System.out.println("\nRegistration failed. Please restart the app and try again.");
        }

        input.close();
    }
}


