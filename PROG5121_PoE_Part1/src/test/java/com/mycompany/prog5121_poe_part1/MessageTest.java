package com.mycompany.prog5121_poe_part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Test Data Case 1 parameters mapped out on Page 15 of task rules
    @Test
    public void testCaseOneMessageVerification() {
        String cell = "+27718693002"; 
        String content = "Hi Mike, can you join us for dinner tonight?"; 
        
        Message msg1 = new Message(1, cell, content);
        
        // Assertions validation checks
        assertTrue(msg1.getMessageContent().length() <= 250, "Message ready to send."); 
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell()); 
        assertNotNull(msg1.getMessageID());
        assertTrue(msg1.checkMessageID());
    }

    // Test Data Case 2 parameters mapped out on Page 15/16 of task rules
    @Test
    public void testCaseTwoMessageDisregardVerification() {
        String cell = "0857597588"; 
        String content = "Hi Keegan, did you receive the payment?"; 
        
        Message msg2 = new Message(2, cell, content);
        
        assertEquals("Cell phone number successfully captured.", msg2.checkRecipientCell()); 
        assertEquals("Press 0 to delete the message", msg2.SentMessage(2)); 
    }
}
