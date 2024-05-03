package com.pluralsight;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountingLedger {
    // Initialize the scanner.
    static Scanner scanner = new Scanner(System.in);

    // Create the variables.
    static int currentMonth;
    static int currentYear;
    static int lastYear;
    static int lastMonth;
    static String ledgerInput;
    static String depositDescription;
    static String depositVendor;
    static String finalDateTime;
    static String searchVendor;
    static String vendor;
    static String paymentDescription;
    static String paymentVendor;
    static boolean found = false;
    static LocalDate currentDate;
    static LocalDate lastMonthDate;
    static LocalDate lastYearDate;
    static LocalDate transactionDate;
    static LocalDateTime currentTime;
    static DateTimeFormatter formatDateTime;

    public static void main(String[] args) {
        // Print welcome message.
        System.out.println("Welcome to Accounting Ledger");

        // Call homeMenu method.
        homeMenu();
    }

    // Create the homeMenu method.
    public static void homeMenu() {
        // Print the menu options.
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");

        // Ask what they want to do.
        System.out.print("Enter your choice: ");
        String userInput = scanner.next();

        // If D is chosen.
        if (userInput.equalsIgnoreCase("d")) {
            // Call addDeposit method.
            addDeposit();
        } // If P is chosen.
        else if (userInput.equalsIgnoreCase("p")) {
            // Call makePayment method.
            makePayment();
        } // IF L is chosen.
        else if (userInput.equalsIgnoreCase("l")) {
            // Call Ledger method.
            ledger();
        } // If X is chosen.
        else if (userInput.equalsIgnoreCase("x")) {
            // Quit
            System.exit(0);
        } // If wrong input is entered.
        else {
            System.out.println("Invalid choice. Please try a different option: ");
            userInput = scanner.nextLine();
            homeMenu();;
        }
    }

    // Create the addDeposit method.
    public static void addDeposit() {
        // Ask user to enter their deposit information.
        System.out.println("\nPlease enter the deposit information:");
        scanner.nextLine();
        scanner.nextLine();

        // Ask to enter the deposit information.
        System.out.println("Enter deposit description: ");
        depositDescription = scanner.nextLine();
        scanner.nextLine();

        // Ask to enter deposit vendor.
        System.out.print("Enter deposit vendor: ");
        depositVendor = scanner.nextLine();

        // Ask to enter deposit amount.
        System.out.print("Enter deposit amount: ");
        double depositAmount = scanner.nextDouble();
        scanner.nextLine();

        // Get the current date and time.
        currentTime = LocalDateTime.now();

        // Set the format for the date and time.
        formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

        // Format the date and time.
        finalDateTime = currentTime.format(formatDateTime);

        // Write the deposit information into the csv.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write("\n" + finalDateTime + "|" + depositDescription + "|" + depositVendor + "|" + depositAmount);

            // Success message.
            System.out.println("\nDeposit information added to transactions.csv successfully.\n");
            // If an error occurred, print error.
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to the file: " + e.getMessage() + "\n");
        }

        // Go back to home menu.
        homeMenu();
    }


    // Create the makePayment method.
    public static void makePayment() {
        // Ask for the payment information.
        System.out.println("\nPlease enter the payment information:");
        scanner.nextLine();
        scanner.nextLine();

        // Ask to enter the payment information.
        System.out.print("Enter payment description: ");
        paymentDescription = scanner.nextLine();
        scanner.nextLine();

        // Ask to enter payment vendor.
        System.out.print("Enter payment vendor: ");
        paymentVendor = scanner.nextLine();
        scanner.nextLine();

        // Ask to enter payment amount.
        System.out.print("Enter the payment amount (as negative): ");
        // Read the input as a String.
        String paymentInput = scanner.nextLine();
        // Convert the input String to a double.
        double paymentAmount = Double.parseDouble(paymentInput);
        scanner.nextLine();

        // Get the current date and time.
        currentTime = LocalDateTime.now();

        // Set the format for the date and time.
        formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

        // Format the date and time.
        finalDateTime = currentTime.format(formatDateTime);

        // Write the payment information into the CSV.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write("\n" + finalDateTime + "|" + paymentDescription + "|" + paymentVendor + "|" + paymentAmount);

            // Successful message.
            System.out.println("\nPayment information added to transactions.csv successfully.\n");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to the file: " + e.getMessage() + "\n");
        }

        // Go back to home menu.
        homeMenu();
        scanner.nextLine();
    }
    // Create the ledger method.
    public static void ledger() {
        // Print the ledger menu options.
        System.out.println("\nA All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");

        // Ask the user what they want to do.
        System.out.print("Please select an option: ");
        ledgerInput = scanner.nextLine();
        scanner.nextLine();

        // If option A is chosen.
        if (ledgerInput.equalsIgnoreCase("a")) {
            // Call ledgerAll method.
            ledgerAll();
            // If option D.
        } else if (ledgerInput.equalsIgnoreCase("d")) {
            // Call ledgerDeposits method.
            ledgerDeposits();
            // If option P is chosen.
        } else if (ledgerInput.equalsIgnoreCase("p")) {
            // Call ledgerPayments method.
            ledgerPayments();
            // If user chose R.
        } else if (ledgerInput.equalsIgnoreCase("r")) {
            // Call ledgerReports method.
            ledgerReports();
            // If user chose H.
        } else if (ledgerInput.equalsIgnoreCase("h")) {
            // Return to home.
            homeMenu();
            // If user entered a wrong input.
        } else {
            System.out.print("Invalid input. Please try again: ");
            ledgerInput = scanner.nextLine();
        }
    }

    // Create ledgerAll method.
    public static void ledgerAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            String line = reader.readLine();

            // If CSV is empty, print message.
            if (line == null) {
                System.out.println("No transactions found.");
                return;
            }

            // Print title.
            System.out.println("\nAll Transactions:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Print all transactions.
                System.out.println(line);
            }
            // If an error occurred, print error.
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger menu.
        ledger();
    }


    // Create the ledgerDeposits method.
    public static void ledgerDeposits() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line = reader.readLine();

            // Skip the first line.
            reader.readLine();

            // If CSV is empty, print message.
            if (line == null) {
                System.out.println("No transactions found.");
                return;
            }

            // Print the title.
            System.out.println("\nAll Deposit Transactions:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");

                // Set the amount.
                double amount;
                amount = Double.parseDouble(parts[4]);

                // Print all the deposits.
                if (amount > 0) {
                    System.out.println(line);
                }
            }
            // If an error occurred, print error.
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger menu.
        ledger();
    }

    // Create the ledgerPayments method.
    public static void ledgerPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            String line = reader.readLine();

            // If CSV is empty, print message.
            if (line == null) {
                System.out.println("No transactions found.");
                return;
            }

            // Print title.
            System.out.println("\nAll Payment Transactions:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");

                // Set the amount.
                double amount;
                amount = Double.parseDouble(parts[4]);

                // Print all the payments.
                if (amount < 0) {
                    System.out.println(line);

                    // Go back to ledger menu when done.
                    ledger();
                }
            }
            // If an error occurred, print error.
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger menu.
        ledger();
    }

    // Create the ledgerReports method.
    public static void ledgerReports() {
        // Create menu for reports.
        System.out.println("\n1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back");

        // Ask user to choose an option.
        System.out.print("Choose an option: ");
        int reportInput = scanner.nextInt();
        scanner.nextLine();

        // If user chose 1.
        if (reportInput == 1) {
            // Call monthToDate method.
            monthToDate();
            // If user chose 2.
        } else if (reportInput == 2) {
            // Call previousMonth method.
            previousMonth();
            // If user chose 3.
        } else if (reportInput == 3) {
            // Call yearToDate method.
            yearToDate();
            // If user chose 4.
        } else if (reportInput == 4) {
            // Call previousYear method.
            previousYear();
            // If user chose 5.
        } else if (reportInput == 5) {
            // Call searchByVendor method.
            searchByVendor();
            // If user chose 6.
        }
        // If user chose 0.
        else if (reportInput == 0) {
            // Call ledgerReports method.
            ledgerReports();
            // If user entered a wrong input.
        } else {
            System.out.print("Invalid input. Please try again: ");
            reportInput = scanner.nextInt();
        }
    }


    // Create monthToDate method.
    public static void monthToDate() {
        // Get the current month and year.
        currentDate = LocalDate.now();
        currentMonth = currentDate.getMonthValue();
        currentYear = currentDate.getYear();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            reader.readLine();

            // Set and initialize the variables.
            String line;
            found = false;

            // Print title.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");
                transactionDate = LocalDate.parse(parts[0].split("\\|")[0]);

                // Check if the date is in the current month and year.
                if (transactionDate.getMonthValue() == currentMonth && transactionDate.getYear() == currentYear) {
                    System.out.println(line);
                    found = true;
                }
            }

            // If no transactions were found, print a message.
            if (!found) {
                System.out.println("No transactions were found for the current month.");
            }
        } // If an error happened, print an error message.
        catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger reports menu.
        ledgerReports();
    }

    // Create previousMonth method.
    public static void previousMonth() {
        // Get the date for the last month.
        currentDate = LocalDate.now();
        lastMonthDate = currentDate.minusMonths(1);
        lastMonth = lastMonthDate.getMonthValue();
        lastYear = lastMonthDate.getYear();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            reader.readLine();

            // Set and initialize the variables.
            String line;
            found = false;

            // Print title.
            System.out.println("\nPrevious Month Results:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");
                transactionDate = LocalDate.parse(parts[0].split("\\|")[0]);

                // Check if the date is in the last month.
                if (transactionDate.getMonthValue() == lastMonth && transactionDate.getYear() == lastYear) {
                    System.out.println(line);
                    found = true;
                }
            }

            // If no transactions found, print message.
            if (!found) {
                System.out.println("No transactions found for the last month.");
            }
            // If an error occurred, print error.
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger reports menu.
        ledgerReports();
    }


    // Create yearToDate method.
    public static void yearToDate() {
        // Get the current year.
        currentYear = LocalDate.now().getYear();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            reader.readLine();

            // Set and initialize the variables.
            String line;
            found = false;

            // Print title.
            System.out.println("\nYear to Date Results:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");
                transactionDate = LocalDate.parse(parts[0].split("\\|")[0]);

                // Check if the date is in the current year.
                if (transactionDate.getYear() == currentYear) {
                    System.out.println(line);
                    found = true;
                }
            }

            // If no transactions were found, print the message.
            if (!found) {
                System.out.println("No transactions found for the current year.");
            }
        } // If an error happened, print a message.
        catch (IOException e) {
            System.out.println("An error has occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger reports menu.
        ledgerReports();
    }

    // Create previousYear method.
    public static void previousYear() {
        // Get the date for the previous year.
        currentDate = LocalDate.now();
        lastYearDate = currentDate.minusYears(1);
        lastYear = lastYearDate.getYear();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            reader.readLine();

            // Set and initialize the variables.
            String line;
            found = false;

            // Print the title.
            System.out.println("\nPrevious Year Results:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");
                LocalDate transactionDate = LocalDate.parse(parts[0].split("\\|")[0]);

                // Check if the date is in the previous year.
                if (transactionDate.getYear() == lastYear) {
                    System.out.println(line);
                    found = true;
                }
            }

            // If no transactions were found, print a message.
            if (!found) {
                System.out.println("No transactions were found for the previous year.");
            }
        } // If an error happened, print the error message.
        catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger reports menu.
        ledgerReports();
    }

    // Create searchByVendor method.
    public static void searchByVendor() {
        // Ask the user to enter the vendor to search for.
        System.out.println("Enter the vendor name to search for: ");
        searchVendor = scanner.nextLine().trim().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            // Skip the first line.
            reader.readLine();

            // Set and initialize the variables.
            String line;
            found = false;

            // Print the title.
            System.out.println("\nSearch by Vendor Results:");

            // Read the lines in the CSV.
            while ((line = reader.readLine()) != null) {
                // Split the information into parts.
                String[] parts = line.split("\\|");
                vendor = parts[3].trim().toLowerCase();

                // Check if the vendor matches the user's input.
                if (depositVendor.equalsIgnoreCase(searchVendor)) {
                    System.out.println(line);
                    found = true;
                }
            }

            // If the transactions aren't found for the vendor, print a message.
            if (!found) {
                System.out.println("No transactions were found for the vendor: " + searchVendor);
            }
        } // If an error happened, print an error message.
        catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Go back to ledger reports menu.
        ledgerReports();
    }
}
