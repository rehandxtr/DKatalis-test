package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        Logger.log("Welcome to the Banking System!");
        displayOptions();

        while (true) {
            System.out.print("\nEnter command: ");
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");

            if (parts.length == 0) continue;

            try {
                switch (parts[0].toLowerCase()) {
                    case "login" -> {
                        if (parts.length != 2) {
                            Logger.warn("Usage: login [name]");
                            continue;
                        }
                        bank.login(parts[1]);
                        displayOptions();
                    }
                    case "deposit" -> {
                        if (parts.length != 2) {
                            Logger.warn("Usage: deposit [amount]");
                            continue;
                        }
                        bank.deposit(Double.parseDouble(parts[1]));
                    }
                    case "withdraw" -> {
                        if (parts.length != 2) {
                            Logger.warn("Usage: withdraw [amount]");
                            continue;
                        }
                        bank.withdraw(Double.parseDouble(parts[1]));
                    }
                    case "transfer" -> {
                        if (parts.length != 3) {
                            Logger.warn("Usage: transfer [target] [amount]");
                            continue;
                        }
                        bank.transfer(parts[1], Double.parseDouble(parts[2]));
                    }
                    case "logout" -> {
                        bank.logout();
                        displayOptions();
                    }
                    case "exit" -> {
                        Logger.log("Exiting the Banking System. Goodbye!");
                        return;
                    }
                    default -> Logger.warn("Unknown command. Type 'exit' to quit or see options below.");
                }
            } catch (NumberFormatException e) {
                Logger.error("Invalid amount format. Please enter a valid number.");
            }
        }
    }

    private static void displayOptions() {
        System.out.println("\nAvailable Commands:");
        System.out.println("1. login [name]         - Log in as a user.");
        System.out.println("2. deposit [amount]     - Deposit an amount into your account.");
        System.out.println("3. withdraw [amount]    - Withdraw an amount from your account.");
        System.out.println("4. transfer [target] [amount] - Transfer an amount to another user.");
        System.out.println("5. logout               - Log out of the current account.");
        System.out.println("6. exit                 - Exit the banking system.");
    }
}