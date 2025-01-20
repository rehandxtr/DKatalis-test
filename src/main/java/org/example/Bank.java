package org.example;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Customer> customers;
    Customer currentCustomer;

    public Bank() {
        this.customers = new HashMap<>();
        this.currentCustomer = null;
    }

    public void login(String name) {
        if (currentCustomer != null) {
            Logger.warn("Another user (" + currentCustomer.getName() + ") is already logged in. Please log out first.");
            return;
        }

        currentCustomer = customers.computeIfAbsent(name, Customer::new);
        Logger.log("Hello, " + name + "!");
        printBalance();
    }

    public void logout() {
        if (currentCustomer != null) {
            Logger.log("Goodbye, " + currentCustomer.getName() + "!");
            currentCustomer = null;
        } else {
            Logger.warn("No customer is currently logged in.");
        }
    }

    public void deposit(double amount) {
        if (ensureLoggedIn()) {
            currentCustomer.deposit(amount);
            settleDebts();
            Logger.log("Deposited $" + amount);
            printBalance();
        }
    }

    public void withdraw(double amount) {
        if (ensureLoggedIn()) {
            if (currentCustomer.withdraw(amount)) {
                Logger.log("Withdrew $" + amount);
                printBalance();
            } else {
                Logger.warn("Insufficient funds.");
            }
        }
    }

    public void transfer(String targetName, double amount) {
        if (!ensureLoggedIn()) {
            return;
        }

        Customer target = customers.get(targetName);
        if (target == null) {
            Logger.warn("Target customer does not exist.");
            return;
        }

        double availableAmount = currentCustomer.getBalance();
        double transferAmount = Math.min(amount, availableAmount);

        if (transferAmount > 0) {
            currentCustomer.withdraw(transferAmount);
            target.deposit(transferAmount);
            Logger.log("Transferred $" + transferAmount + " to " + targetName);
        }

        if (transferAmount < amount) {
            double remainingAmount = amount - transferAmount;
            currentCustomer.addDebt(targetName, remainingAmount);
            target.addCredit(currentCustomer.getName(), remainingAmount);
            Logger.warn("Insufficient funds. Added remaining $" + remainingAmount + " as debt.");
        }

        printBalance();
    }

    private void settleDebts() {
        if (currentCustomer == null) return;

        Map<String, Double> debts = new HashMap<>(currentCustomer.getDebts());
        for (Map.Entry<String, Double> debt : debts.entrySet()) {
            String creditorName = debt.getKey();
            double debtAmount = debt.getValue();
            double availableAmount = currentCustomer.getBalance();

            if (availableAmount > 0) {
                double settlementAmount = Math.min(availableAmount, debtAmount);
                currentCustomer.withdraw(settlementAmount);
                Customer creditor = customers.get(creditorName);
                creditor.deposit(settlementAmount);
                currentCustomer.reduceDebt(creditorName, settlementAmount);
                creditor.reduceCredit(currentCustomer.getName(), settlementAmount);
                Logger.log("Settled $" + settlementAmount + " debt with " + creditorName);
            }
        }
    }

    private void printBalance() {
        if (currentCustomer != null) {
            Logger.log("Your balance is $" + currentCustomer.getBalance());

            currentCustomer.getDebts().forEach((key, value) ->
                    Logger.log("Owed $" + value + " to " + key)
            );

            currentCustomer.getCredits().forEach((key, value) ->
                    Logger.log("Owed $" + value + " from " + key)
            );
        }
    }

    private boolean ensureLoggedIn() {
        if (currentCustomer == null) {
            Logger.warn("No customer logged in.");
            return false;
        }
        return true;
    }
}