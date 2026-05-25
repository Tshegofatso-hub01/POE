/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Tshegofatso
 */
public class POEclass {
    // ============================
    // PART 1: Registration & Login
    // ============================
    private String userName;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public POEclass(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setCellPhoneNumber(String cellPhoneNumber) { this.cellPhoneNumber = cellPhoneNumber; }

    public boolean checkUserName() {
        if (userName.contains("_") && userName.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }
    }

    public boolean checkPasswordComplexity() {
        if (password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }
    }

    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber.matches("^\\+27\\d{9}$")) {
            System.out.println("Cell phone number successfully added.");
            return true;
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        }
    }

    public String registerUser() {
        if (!checkUserName()) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity()) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber()) return "Cell phone number incorrectly formatted or does not contain international code.";
        return "User registered successfully.";
    }

    public boolean loginUser(String enteredUserName, String enteredPassword) {
        return this.userName.equals(enteredUserName) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // ============================
    // PART 2: QuickChat Messaging (STATIC METHOD)
    // ============================
    public static void runQuickChat() {
        Scanner input = new Scanner(System.in);
        ArrayList<Message> sentMessages = new ArrayList<>();
        int totalMessagesSent = 0;

        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("How many messages do you want to send? ");
                    int numMessages = input.nextInt();
                    input.nextLine();

                    for (int i = 1; i <= numMessages; i++) {
                        System.out.print("Enter recipient number (+27...): ");
                        String recipient = input.nextLine();

                        System.out.print("Enter message content: ");
                        String content = input.nextLine();

                        Message msg = new Message(i, recipient, content);

                        if (!msg.checkMessageLength()) {
                            System.out.println("Message exceeds 250 characters by " +
                                (msg.getContent().length() - 250) + "; please reduce the size.");
                            continue;
                        }

                        if (!msg.checkRecipientCell()) {
                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            continue;
                        }

                        System.out.println("Choose action: ");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");
                        int action = input.nextInt();
                        input.nextLine();

                        if (action == 1) {
                            sentMessages.add(msg);
                            totalMessagesSent++;
                            System.out.println(msg.sendMessage());
                            msg.printMessage();
                        } else if (action == 2) {
                            System.out.println(msg.discardMessage());
                        } else if (action == 3) {
                            System.out.println(msg.storeMessage());
                        }
                    }
                    break;

                case 2:
                    if (sentMessages.isEmpty()) {
                        System.out.println("No messages sent yet.");
                    } else {
                        System.out.println("\n=== Recently Sent Messages ===");
                        for (int i = Math.max(0, sentMessages.size() - 5); i < sentMessages.size(); i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");
                            sentMessages.get(i).printMessage();
                        }
                    }
                    break;

                case 3:
                    running = false;
                    System.out.println("Exiting QuickChat...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("Total messages sent: " + totalMessagesSent);
        input.close();
    }

    // ============================
    // Inner Message Class (Part 2)
    // ============================
    public static class Message {
        private String messageID;
        private int messageNumber;
        private String recipient;
        private String content;
        private String messageHash;

        public Message(int messageNumber, String recipient, String content) {
            this.messageID = generateMessageID();
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = createMessageHash();
        }

        private String generateMessageID() {
            long id = (long)(Math.random() * 1_000_000_0000L);
            return String.format("%010d", id);
        }

        private String createMessageHash() {
            String[] words = content.split(" ");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            return messageID.substring(0, 2) + ":" + messageNumber + ":" +
                   firstWord.toUpperCase() + lastWord.toUpperCase();
        }

        public boolean checkMessageLength() { return content.length() <= 250; }
        public boolean checkRecipientCell() { return recipient.matches("^\\+27\\d{9}$"); }

        public String sendMessage() { return "Message successfully sent."; }
        public String discardMessage() { return "Press 0 to delete the message."; }
        public String storeMessage() { return "Message successfully stored."; }

        public void printMessage() {
            System.out.println("Message ID: " + messageID);
            System.out.println("Message Hash: " + messageHash);
            System.out.println("Recipient: " + recipient);
            System.out.println("Message: " + content);
        }

        public String getMessageID() { return messageID; }
        public String getRecipient() { return recipient; }
        public String getContent() { return content; }
        public String getMessageHash() { return messageHash; }
    }
}