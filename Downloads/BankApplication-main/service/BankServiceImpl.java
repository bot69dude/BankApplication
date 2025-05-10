package service;

import model.Account;
import repository.AccountRepository;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;

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
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount);
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = getAccount(accountNumber);
        if (!account.withdraw(amount)) {
            throw new InsufficientFundsException("Insufficient funds in account: " + accountNumber);
        }
        accountRepository.save(account);
        System.out.println(Thread.currentThread().getName() + " withdrew: " + amount);
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
                        System.out.println("Transferred " + amount + " from " + fromAccNum + " to " + toAccNum);
                        return true;
                    } else {
                        System.out.println("Transfer failed due to insufficient funds.");
                        return false;
                    }
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.println("Transfer failed: " + e.getMessage());
            return false;
        }
    }
}
