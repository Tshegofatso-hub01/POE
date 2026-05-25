/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Tshegofatso
 */

public class POE {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ============================
        // PART 1: Registration & Login
        // ============================        
        // Collect first and last name for login message
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        POEclass userObj = new POEclass(firstName, lastName);

        // Registration
        System.out.print("Enter Username: ");
        userObj.setUserName(input.nextLine());

        System.out.print("Enter Password: ");
        userObj.setPassword(input.nextLine());

        System.out.print("Enter Cell Phone Number (with international code): ");
        userObj.setCellPhoneNumber(input.nextLine());

        System.out.println("\n--- Registration Status ---");
        System.out.println(userObj.registerUser());

        // Login
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String loginUserName = input.nextLine();
        System.out.print("Enter Password: ");
        String loginPassword = input.nextLine();

        boolean loginStatus = userObj.loginUser(loginUserName, loginPassword);
        System.out.println(userObj.returnLoginStatus(loginStatus));

        // ============================
        // PART 2: QuickChat Messaging
        // ============================
        if (loginStatus) {
            ArrayList<POEclass.Message> sentMessages = new ArrayList<>();
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

                            POEclass.Message msg = new POEclass.Message(i, recipient, content);

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
                            int start = Math.max(0, sentMessages.size() - 5);
                            for (int i = start; i < sentMessages.size(); i++) {
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
        } else {
            System.out.println("Cannot access QuickChat due to failed login.");
        }

        input.close();
    }
}