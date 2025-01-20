package org.example;

public class BankTest {
    public static void main(String[] args) {
        Bank bank = new Bank();

        System.out.println("Running Bank System Test Cases...\n");

        // Test 1: Login functionality
        System.out.println("Test 1: Login Functionality");
        bank.login("Rehan");
        assert bank.currentCustomer != null && bank.currentCustomer.getName().equals("Rehan") : "Login failed for Rehan";
        bank.logout();

        // Test 2: Prevent multiple logins
        System.out.println("\nTest 2: Prevent Multiple Logins");
        bank.login("Rehan");
        bank.login("Ahad"); // Attempt to log in Ahad while Rehan is logged in
        assert bank.currentCustomer.getName().equals("Rehan") : "Another login happened without logout";
        bank.logout();

        // Test 3: Logout functionality
        System.out.println("\nTest 3: Logout Functionality");
        bank.login("Rehan");
        bank.logout();
        assert bank.currentCustomer == null : "Logout failed, user is still logged in";

        // Test 4: Deposit functionality
        System.out.println("\nTest 4: Deposit Functionality");
        bank.login("Rehan");
        bank.deposit(100.0); // Rehan deposits $100
        assert bank.currentCustomer.getBalance() == 100.0 : "Deposit failed for Rehan";
        bank.logout();

        // Test 5: Withdraw functionality
        System.out.println("\nTest 5: Withdraw Functionality");
        bank.login("Rehan");
        bank.withdraw(50.0); // Rehan withdraws $50
        assert bank.currentCustomer.getBalance() == 50.0 : "Withdraw failed for Rehan";
        bank.logout();

        // Test 6: Withdraw with insufficient funds
        System.out.println("\nTest 6: Withdraw with Insufficient Funds");
        bank.login("Rehan");
        bank.withdraw(100.0); // Attempt to withdraw $100 when balance is $50
        assert bank.currentCustomer.getBalance() == 50.0 : "Withdraw should have failed due to insufficient funds";
        bank.logout();

        // Test 7: Transfer functionality
        System.out.println("\nTest 7: Transfer Functionality");
        bank.login("Rehan");
        bank.deposit(200.0); // Rehan deposits $200
        bank.transfer("Ahad", 100.0); // Rehan transfers $100 to Ahad
        assert bank.currentCustomer.getBalance() == 100.0 : "Transfer failed to deduct from Rehan's account";
        bank.logout();

        bank.login("Ahad");
        assert bank.currentCustomer.getBalance() == 100.0 : "Transfer failed to credit Ahad's account";
        bank.logout();

        // Test 8: Transfer with insufficient funds
        System.out.println("\nTest 8: Transfer with Insufficient Funds");
        bank.login("Rehan");
        bank.transfer("Ahad", 200.0); // Rehan (balance $100) tries to transfer $200 to Ahad
        assert bank.currentCustomer.getBalance() == 0.0 : "Transfer failed to deduct available balance from Rehan";
        bank.logout();

        bank.login("Ahad");
        assert bank.currentCustomer.getBalance() == 200.0 : "Transfer failed to credit Ahad with available funds";
        assert bank.currentCustomer.getDebts().isEmpty() : "Ahad should not have any debts";
        bank.logout();

        // Test 9: Record debt for insufficient transfer
        System.out.println("\nTest 9: Record Debt for Insufficient Transfer");
        bank.login("Rehan");
        bank.transfer("Ahad", 300.0); // Rehan (balance $0) tries to transfer $300 to Ahad
        assert bank.currentCustomer.getBalance() == 0.0 : "Rehan's balance should remain $0";
        assert bank.currentCustomer.getDebts().get("Ahad") == 300.0 : "Debt record for Rehan is incorrect";
        bank.logout();

        bank.login("Ahad");
        assert bank.currentCustomer.getCredits().get("Rehan") == 300.0 : "Credit record for Ahad is incorrect";
        bank.logout();

        // Test 10: Debt settlement
        System.out.println("\nTest 10: Debt Settlement");
        bank.login("Rehan");
        bank.deposit(150.0); // Rehan deposits $150 to partially settle his $300 debt to Ahad
        assert bank.currentCustomer.getDebts().get("Ahad") == 150.0 : "Debt settlement failed for Rehan";
        bank.logout();

        bank.login("Ahad");
        assert bank.currentCustomer.getCredits().get("Rehan") == 150.0 : "Credit settlement failed for Ahad";
        bank.logout();

        // Test 11: Full debt settlement
        System.out.println("\nTest 11: Full Debt Settlement");
        bank.login("Rehan");
        bank.deposit(200.0); // Rehan deposits $200 (enough to fully settle remaining $150 debt)
        assert bank.currentCustomer.getDebts().isEmpty() : "Debt settlement should clear all debts for Rehan";
        bank.logout();

        bank.login("Ahad");
        assert bank.currentCustomer.getCredits().isEmpty() : "Credit settlement should clear all credits for Ahad";
        bank.logout();

        // Test 12: Handle no logged-in user
        System.out.println("\nTest 12: Handle No Logged-in User");
        bank.deposit(100.0); // Attempt to deposit without a logged-in user
        assert bank.currentCustomer == null : "Deposit should not work without a logged-in user";

        bank.withdraw(50.0); // Attempt to withdraw without a logged-in user
        assert bank.currentCustomer == null : "Withdraw should not work without a logged-in user";

        bank.transfer("Ahad", 50.0); // Attempt to transfer without a logged-in user
        assert bank.currentCustomer == null : "Transfer should not work without a logged-in user";

        System.out.println("\nAll Test Cases Passed Successfully!");
    }
}