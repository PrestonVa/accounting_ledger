package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class transactions {
    // Define Transaction class
    static class Transaction {
        private String dateTime;
        private String description;
        private String vendor;
        private double amount;

        public Transaction(String dateTime, String description, String vendor, double amount) {
            this.dateTime = dateTime;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        // Getters and setters
        public String getDateTime() {
            return dateTime;
        }

        public String getDescription() {
            return description;
        }

        public String getVendor() {
            return vendor;
        }

        public double getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "dateTime='" + dateTime + '\'' +
                    ", description='" + description + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

    // List to store transactions
    private static List<Transaction> transactions = new ArrayList<>();

    // Scanner for user input
    static Scanner scanner = new Scanner(System.in);

    // Other static variables...

    public static void main(String[] args) {
        // Call homeMenu method
        homeMenu();
    }

    // Other methods...

    // Method to add a transaction
    public static void addTransaction(String dateTime, String description, String vendor, double amount) {
        Transaction transaction = new Transaction(dateTime, description, vendor, amount);
        transactions.add(transaction);
    }

    // Method to write transactions to CSV file
    public static void writeTransactionsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getDateTime() + "|" + transaction.getDescription() + "|" +
                        transaction.getVendor() + "|" + transaction.getAmount() + "\n");
            }
            System.out.println("\nTransactions written to transactions.csv successfully.\n");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to the file: " + e.getMessage() + "\n");
        }
    }

    // Method to display all transactions
    public static void displayTransactions() {
        System.out.println("Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    // Method to display main menu and handle user choices
    public static void homeMenu() {
        // Display menu options
        System.out.println("1. Add Transaction");
        System.out.println("2. Display Transactions");
        System.out.println("3. Write Transactions to CSV");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        // Get user choice
        int choice = scanner.nextInt();

        // Handle user choice
        switch (choice) {
            case 1:
                addTransactionMenu();
                break;
            case 2:
                displayTransactions();
                break;
            case 3:
                writeTransactionsToCSV();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number from 1 to 4.");
        }
    }

    // Method to handle adding a transaction
    public static void addTransactionMenu() {
        // Get transaction details from user
        System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTime = scanner.next();

        System.out.print("Enter transaction description: ");
        String description = scanner.next();

        System.out.print("Enter transaction vendor: ");
        String vendor = scanner.next();

        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();

        // Add transaction to list
        addTransaction(dateTime, description, vendor, amount);

        System.out.println("Transaction added successfully.");
    }
}
