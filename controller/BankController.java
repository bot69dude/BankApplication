package controller;

import model.Account;
import service.BankService;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import util.ConsoleUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class BankController {
    private final BankService bankService;
    private final ExecutorService executor;
    private final BufferedReader reader;

    public BankController(BankService bankService, ExecutorService executor, BufferedReader reader) {
        this.bankService = bankService;
        this.executor = executor;
        this.reader = reader;
    }

    public void start() throws IOException {
        while (true) {
            String[] options = {
                "Create Account", 
                "Deposit", 
                "Withdraw", 
                "Transfer", 
                "Balance", 
                "Exit"
            };
            ConsoleUtils.printMenu("Bank Menu", options);
            int choice = Integer.parseInt(reader.readLine().trim());

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> checkBalance();
                case 6 -> {
                    executor.shutdown();
                    ConsoleUtils.printInfo("Thank you for using our service!");
                    return;
                }
                default -> ConsoleUtils.printError("Invalid choice.");
            }
        }
    }

    private void createAccount() throws IOException {
        ConsoleUtils.printHeader("Create Account");
        System.out.print("Enter account number: ");
        String accNum = reader.readLine().trim();
        bankService.createAccount(accNum);
        ConsoleUtils.printSuccess("Account created successfully");
    }

    private void deposit() throws IOException {
        ConsoleUtils.printHeader("Deposit Funds");
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> {
            try {
                bankService.deposit(acc, amt);
                ConsoleUtils.printSuccess("Deposit successful");
            } catch (AccountNotFoundException e) {
                ConsoleUtils.printError("Account not found.");
            }
        });
    }

    private void withdraw() throws IOException {
        ConsoleUtils.printHeader("Withdraw Funds");
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> {
            try {
                bankService.withdraw(acc, amt);
                ConsoleUtils.printSuccess("Withdrawal successful");
            } catch (AccountNotFoundException e) {
                ConsoleUtils.printError("Account not found.");
            } catch (InsufficientFundsException e) {
                ConsoleUtils.printError("Insufficient funds.");
            }
        });
    }

    private void transfer() throws IOException {
        ConsoleUtils.printHeader("Transfer Funds");
        System.out.print("From Account: ");
        String from = reader.readLine().trim();
        System.out.print("To Account: ");
        String to = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> {
            boolean result = bankService.transfer(from, to, amt);
            if (result) {
                ConsoleUtils.printSuccess("Transfer successful");
            } else {
                ConsoleUtils.printError("Transfer failed");
            }
        });
    }

    private void checkBalance() throws IOException {
        ConsoleUtils.printHeader("Check Balance");
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        try {
            Account account = bankService.getAccount(acc);
            ConsoleUtils.printBox("Balance: $" + account.getBalance());
        } catch (AccountNotFoundException e) {
            ConsoleUtils.printError("Account not found.");
        }
    }
}
