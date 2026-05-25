package com.mycompany.poe;  // Changed from TestingCode to match your main class

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Tshegofatso
 */
public class JUnitTest {

    // ============================
    // PART 1: Registration & Login
    // ============================

    @Test
    public void testCorrectUsername() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setUserName("kyl_1");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testIncorrectUsername() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setUserName("kyle!");
        assertFalse(user.checkUserName());
    }

    @Test
    public void testCorrectPassword() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setPassword("Ch&&sec@ke99!");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testIncorrectPassword() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setPassword("password");
        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCorrectCellPhone() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setCellPhoneNumber("+27838968976");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testIncorrectCellPhone() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setCellPhoneNumber("08966553");
        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulLogin() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setUserName("kyl_1");
        user.setPassword("Ch&&sec@ke99!");
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testFailedLogin() {
        POEclass user = new POEclass("Kyle", "Smith");
        user.setUserName("kyl_1");
        user.setPassword("Ch&&sec@ke99!");
        assertFalse(user.loginUser("wrong", "password"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        POEclass user = new POEclass("Kyle", "Smith");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                     user.returnLoginStatus(true));
    }

    @Test
    public void testReturnLoginStatusFail() {
        POEclass user = new POEclass("Kyle", "Smith");
        assertEquals("Username or password incorrect, please try again.",
                     user.returnLoginStatus(false));
    }

    // ============================
    // PART 2: QuickChat Messaging
    // ============================

    @Test
    public void testMessageLengthValid() {
        POEclass.Message msg = new POEclass.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthInvalid() {
        String longMessage = "A".repeat(260);
        POEclass.Message msg = new POEclass.Message(1, "+27718693002", longMessage);
        assertFalse(msg.checkMessageLength());
    }

    @Test
    public void testRecipientValid() {
        POEclass.Message msg = new POEclass.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        POEclass.Message msg = new POEclass.Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        POEclass.Message msg = new POEclass.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains("HI") && hash.contains("TONIGHT"), 
                   "Hash should contain uppercase first and last words. Got: " + hash);
    }

    @Test
    public void testSendMessage() {
        POEclass.Message msg = new POEclass.Message(1, "+27718693002", "Test message");
        assertEquals("Message successfully sent.", msg.sendMessage());
    }

    @Test
    public void testDiscardMessage() {
        POEclass.Message msg = new POEclass.Message(2, "+27718693002", "Test message");
        assertEquals("Press 0 to delete the message.", msg.discardMessage());
    }

    @Test
    public void testStoreMessage() {
        POEclass.Message msg = new POEclass.Message(3, "+27718693002", "Test message");
        assertEquals("Message successfully stored.", msg.storeMessage());
    }
}