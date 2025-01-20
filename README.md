# Bank System

A simple command-line banking system that allows users to manage their accounts. Users can log in, deposit money, withdraw funds, transfer money to other users, and handle debts if funds are insufficient. The system ensures that only one user can be logged in at a time.

---

## Features

- **Login/Logout**: Easily log in or out of your account.
- **Deposit Money**: Add money to your account balance.
- **Withdraw Money**: Withdraw funds from your account (if you have enough balance).
- **Transfer Money**: Send money to another user. If your balance is insufficient, the remaining amount is recorded as debt.
- **Debt Settlement**: Automatically settle debts when you deposit money.
- **Single Active User**: Only one user can be logged in at a time.

---

## How It Works

1. When you start the program, you'll see a list of commands.
2. Use the `login` command to log in as a user. If the user doesn't exist, the system will create it for you.
3. Perform actions like depositing, withdrawing, or transferring money.
4. Log out when you're done so another user can log in.

---

## Commands

Here’s a list of commands you can use in the program:

| Command                           | Description                                                      |
|-----------------------------------|------------------------------------------------------------------|
| `login [name]`                    | Log in as a user with the specified name.                       |
| `deposit [amount]`                | Deposit the specified amount into your account.                 |
| `withdraw [amount]`               | Withdraw the specified amount from your account.                |
| `transfer [target] [amount]`      | Transfer the specified amount to another user by name.          |
| `logout`                          | Log out of your account.                                        |
| `exit`                            | Exit the program.                                               |

---

## Example Usage

```plaintext
Welcome to the Banking System!

Available Commands:
1. login [name]         - Log in as a user.
2. deposit [amount]     - Deposit an amount into your account.
3. withdraw [amount]    - Withdraw an amount from your account.
4. transfer [target] [amount] - Transfer an amount to another user.
5. logout               - Log out of the current account.
6. exit                 - Exit the banking system.

Enter command: login Rehan
[INFO] Hello, Rehan!
Your balance is $0.0

Enter command: deposit 500
[INFO] Deposited $500.0
Your balance is $500.0

Enter command: transfer Ahad 300
[INFO] Transferred $300.0 to Ahad
Your balance is $200.0

Enter command: logout
[INFO] Goodbye, Rehan!

Enter command: login Ahad
[INFO] Hello, Ahad!
Your balance is $300.0

Enter command: withdraw 100
[INFO] Withdrew $100.0
Your balance is $200.0

Enter command: logout
[INFO] Goodbye, Ahad!

Enter command: exit
[INFO] Exiting the Banking System. Goodbye!
```

---

## Test Cases

The following scenarios are tested to ensure the system works as expected:

1. Logging in as a user.
2. Preventing login when another user is already logged in.
3. Logging out successfully.
4. Depositing money into an account.
5. Withdrawing money when enough balance is available.
6. Handling withdrawal attempts with insufficient funds.
7. Transferring money between users.
8. Recording debts for transfers when the balance is insufficient.
9. Settling debts automatically when funds are deposited.
10. Handling operations when no user is logged in.

All test cases pass successfully.

---

## How to Run the Tests

1. Compile the project:
   ```bash
   javac -d out src/org/example/*.java
   ```
2. Run the main program:
   ```bash
   java -cp out org.example.Main
   ```

3. To run the test cases:
   ```bash
   java -cp out org.example.BankTest
   ```

---

## Notes

- The system is designed for simplicity, so it doesn’t save data after the program exits.
- Debts and credits are automatically tracked and managed between users.

---

Enjoy using the Bank System! 😊
