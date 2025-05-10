package controller;

import model.Account;
import service.BankService;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
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
            System.out.println("\n--- Bank Menu ---");
            System.out.println("1. Create Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Balance\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(reader.readLine().trim());

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> checkBalance();
                case 6 -> {
                    executor.shutdown();
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createAccount() throws IOException {
        System.out.print("Enter account number: ");
        String accNum = reader.readLine().trim();
        bankService.createAccount(accNum);
        System.out.println("Account created.");
    }

    private void deposit() throws IOException {
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> {
            try {
                bankService.deposit(acc, amt);
            } catch (AccountNotFoundException e) {
                System.out.println("Account not found.");
            }
        });
    }

    private void withdraw() throws IOException {
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> {
            try {
                bankService.withdraw(acc, amt);
            } catch (AccountNotFoundException e) {
                System.out.println("Account not found.");
            } catch (InsufficientFundsException e) {
                System.out.println("Insufficient funds.");
            }
        });
    }

    private void transfer() throws IOException {
        System.out.print("From Account: ");
        String from = reader.readLine().trim();
        System.out.print("To Account: ");
        String to = reader.readLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(reader.readLine().trim());
        executor.submit(() -> bankService.transfer(from, to, amt));
    }

    private void checkBalance() throws IOException {
        System.out.print("Account number: ");
        String acc = reader.readLine().trim();
        try {
            Account account = bankService.getAccount(acc);
            System.out.println("Balance: " + account.getBalance());
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found.");
        }
    }
}
