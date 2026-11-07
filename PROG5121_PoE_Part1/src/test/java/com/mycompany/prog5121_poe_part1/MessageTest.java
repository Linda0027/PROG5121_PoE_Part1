package com.mycompany.prog5121_poe_part1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        PROG5121_PoE_Part1.initializeSystemData();
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        assertTrue(PROG5121_PoE_Part1.sentMessages.contains("Did you get the cake?"));
        assertTrue(PROG5121_PoE_Part1.sentMessages.contains("It is dinner time !"));
        assertEquals(2, PROG5121_PoE_Part1.sentMessages.size());
    }

    @Test
    public void testDisplayLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, PROG5121_PoE_Part1.getLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        Message msg4 = PROG5121_PoE_Part1.masterCatalog.get(3);
        String expected = "Recipient: 0838884567 | Message: It is dinner time !";
        assertEquals(expected, PROG5121_PoE_Part1.searchByMessageID(msg4.getMessageID()));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        String expected = "Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you.";
        assertEquals(expected, PROG5121_PoE_Part1.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        Message msg2 = PROG5121_PoE_Part1.masterCatalog.get(1);
        String expected = "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, PROG5121_PoE_Part1.deleteByMessageHash(msg2.getMessageHash()));
    }

    @Test
    public void testDisplayReport() {
        String report = PROG5121_PoE_Part1.generateFullReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient:    +27834557896"));
        assertTrue(report.contains("Message:      Did you get the cake?"));
        assertTrue(report.contains("Message:      It is dinner time !"));
    }
}
