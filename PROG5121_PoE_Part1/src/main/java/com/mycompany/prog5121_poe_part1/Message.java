package com.mycompany.prog5121_poe_part1;

import java.util.Random;

public class Message {
    
    // Core object tracking attributes
    private String messageID;
    private int numMessagesSent;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private String status; // Sent, Disregarded, Stored

    // Constructor to initialize an internal tracking instance
    public Message(int numMessagesSent, String recipientCell, String messageContent) {
        this.numMessagesSent = numMessagesSent;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        
        // Autogenerate unique fields during construction
        this.messageID = autogenerateMessageID();
        this.messageHash = createMessageHash();
        this.status = "Pending";
    }

    // Method: Generates a random 10-digit tracking key
    private String autogenerateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Method: Verifies recipient phone layout constraints
    public String checkRecipientCell() {
        if (recipientCell.startsWith("+27") && recipientCell.length() == 12) {
            return "Cell phone number successfully captured.";
        } else if (recipientCell.startsWith("0") && recipientCell.length() == 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Method: Computes specific cryptographic layout string hashes
    public String createMessageHash() {
        String firstTwoID = this.messageID.substring(0, 2);
        
        String cleanText = this.messageContent.trim();
        String[] words = cleanText.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String hashResult = firstTwoID + ":" + this.numMessagesSent + ":" + firstWord + lastWord;
        this.messageHash = hashResult.toUpperCase(); // Requirement: ALL CAPS
        return this.messageHash;
    }

    // Method: Handles operational workflow routing switches
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.status = "Sent";
                return "Message successfully sent";
            case 2:
                this.status = "Disregarded";
                return "Press 0 to delete the message";
            case 3:
                this.status = "Stored";
                return "Message successfully stored";
            default:
                this.status = "Disregarded";
                return "Press 0 to delete the message";
        }
    }

    // Method: Returns formatted metadata profiles
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
               "Message Hash: " + this.messageHash + "\n" +
               "Recipient: " + this.recipientCell + "\n" +
               "Message: " + this.messageContent;
    }

    public int returnTotalMessagess() {
        return this.numMessagesSent;
    }

    public String storeMessage() {
        return "{\n" +
               "  \"messageID\": \"" + this.messageID + "\",\n" +
               "  \"status\": \"" + this.status + "\"\n" +
               "}";
    }

    public String getMessageID() { return messageID; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
}
