package model;

public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
