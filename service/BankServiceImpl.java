package service;

import model.Account;
import repository.AccountRepository;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import util.ConsoleUtils;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String accountNumber) {
        Account account = new Account(accountNumber);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    @Override
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        accountRepository.save(account);
        ConsoleUtils.printInfo(Thread.currentThread().getName() + " deposited: $" + amount);
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = getAccount(accountNumber);
        if (!account.withdraw(amount)) {
            throw new InsufficientFundsException("Insufficient funds in account: " + accountNumber);
        }
        accountRepository.save(account);
        ConsoleUtils.printInfo(Thread.currentThread().getName() + " withdrew: $" + amount);
    }

    @Override
    public boolean transfer(String fromAccNum, String toAccNum, double amount) {
        try {
            Account from = getAccount(fromAccNum);
            Account to = getAccount(toAccNum);

            synchronized (from) {
                synchronized (to) {
                    if (from.withdraw(amount)) {
                        to.deposit(amount);
                        accountRepository.save(from);
                        accountRepository.save(to);
                        ConsoleUtils.printInfo("Transferred $" + amount + " from " + fromAccNum + " to " + toAccNum);
                        return true;
                    } else {
                        ConsoleUtils.printError("Transfer failed due to insufficient funds.");
                        return false;
                    }
                }
            }
        } catch (AccountNotFoundException e) {
            ConsoleUtils.printError("Transfer failed: " + e.getMessage());
            return false;
        }
    }

    public boolean applyForLoan(Account account, double loanAmount) {
        // try {
        //     Account account = getAccount(accountNumber);
            // Simple eligibility check: must have positive balance and loanAmount > 0
            if (account.getBalance() > 0 && loanAmount > 0) {
                // For demonstration, just print approval and return true
                //ConsoleUtils.printInfo("Loan of $" + loanAmount + " approved for account: " + accountNumber);
                return true;
            } else {
                //ConsoleUtils.printError("Loan application denied for account: " + accountNumber);
                return false;
            }
        // } catch (AccountNotFoundException e) {
        //     ConsoleUtils.printError("Loan application failed: " + e.getMessage());
        //     return false;
        // }
    }
}
