package service;

import model.Account;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;

public interface BankService {
    Account createAccount(String accountNumber);
    Account getAccount(String accountNumber) throws AccountNotFoundException;
    void deposit(String accountNumber, double amount) throws AccountNotFoundException;
    void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException;
    boolean transfer(String fromAccNum, String toAccNum, double amount);
}
