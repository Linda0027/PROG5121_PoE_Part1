package com.mycompany.prog5121_poe_part1;

import java.util.Random;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private String status; // Sent, Disregard, or Stored

    public Message(int numMessagesSent, String recipientCell, String messageContent, String status) {
        this.numMessagesSent = numMessagesSent;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.status = status;
        this.messageID = autogenerateMessageID();
        this.messageHash = createMessageHash();
    }

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

    public String checkRecipientCell() {
        if ((recipientCell.startsWith("+27") && recipientCell.length() == 12) || 
            (recipientCell.startsWith("0") && recipientCell.length() == 10)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Generates first 2 digits of ID, colons, message number, and first/last words in ALL CAPS
    public String createMessageHash() {
        String firstTwoID = this.messageID.substring(0, 2);
        String cleanText = this.messageContent.trim();
        String[] words = cleanText.split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        
        this.messageHash = (firstTwoID + ":" + this.numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
               "Message Hash: " + this.messageHash + "\n" +
               "Recipient: " + this.recipientCell + "\n" +
               "Message: " + this.messageContent;
    }

    // Formats object properties into a valid JSON object string
    public String toJSON() {
        String safeContent = this.messageContent.replace("\"", "\\\"");
        return "  {\n" +
               "    \"messageID\": \"" + this.messageID + "\",\n" +
               "    \"recipient\": \"" + this.recipientCell + "\",\n" +
               "    \"message\": \"" + safeContent + "\",\n" +
               "    \"messageHash\": \"" + this.messageHash + "\",\n" +
               "    \"status\": \"" + this.status + "\"\n" +
               "  }";
    }

    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
}
