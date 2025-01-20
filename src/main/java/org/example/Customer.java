package org.example;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private final String name;
    private double balance;
    private final Map<String, Double> debts;
    private final Map<String, Double> credits;

    public Customer(String name) {
        this.name = name;
        this.balance = 0;
        this.debts = new HashMap<>();
        this.credits = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        Logger.log("Deposited $" + amount + " for " + name);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Logger.log("Withdrew $" + amount + " for " + name);
            return true;
        }
        Logger.warn("Insufficient funds for withdrawal by " + name);
        return false;
    }

    public Map<String, Double> getDebts() {
        return debts;
    }

    public Map<String, Double> getCredits() {
        return credits;
    }

    public void addDebt(String creditor, double amount) {
        debts.put(creditor, debts.getOrDefault(creditor, 0.0) + amount);
        Logger.log(name + " owes $" + amount + " to " + creditor);
    }

    public void addCredit(String debtor, double amount) {
        credits.put(debtor, credits.getOrDefault(debtor, 0.0) + amount);
        Logger.log(debtor + " owes $" + amount + " to " + name);
    }

    public void reduceDebt(String creditor, double amount) {
        debts.computeIfPresent(creditor, (k, v) -> (v <= amount) ? null : v - amount);
    }

    public void reduceCredit(String debtor, double amount) {
        credits.computeIfPresent(debtor, (k, v) -> (v <= amount) ? null : v - amount);
    }
}