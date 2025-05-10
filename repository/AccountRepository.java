package repository;

import model.Account;
import java.util.List;

public interface AccountRepository {
    Account save(Account account);
    Account findByAccountNumber(String accountNumber);
    List<Account> findAll();
    void delete(String accountNumber);
}
