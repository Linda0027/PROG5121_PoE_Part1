package com.mycompany.prog5121_poe_part1;

import java.util.regex.Pattern;

public class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellNumber;
    private String firstName;
    private String lastName;

    public void setUserNames(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Page 6: Ensures username contains an underscore and is <= 5 characters
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Page 6: Verifies password complexity
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.contains(String.valueOf(c))) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    // Page 5 & 6: Regular expression-based cell phone checker (+27 followed by 9 digits)
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) return false;
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cellNumber);
    }

    // Page 6: Returns the exact registration messaging strings
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        
        // Save values locally upon verification confirmation
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellNumber = cellNumber;
        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }

    public boolean loginUser(String username, String password) {
        return username != null && password != null && 
               username.equals(this.registeredUsername) && 
               password.equals(this.registeredPassword);
    }

    // Page 5 & 6: Exact greeting message adjustment
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
