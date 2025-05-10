package repository;

import model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void delete(String accountNumber) {
        accounts.remove(accountNumber);
    }
}
