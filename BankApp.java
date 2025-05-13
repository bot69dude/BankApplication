import controller.BankController;
import repository.AccountRepository;
import repository.InMemoryAccountRepository;
import service.BankService;
import service.BankServiceImpl;
import util.ConsoleUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankApp {
    public static void main(String[] args) {
        ConsoleUtils.printWelcomeBanner("Bank Application");
        
        AccountRepository accountRepository = new InMemoryAccountRepository();
        BankService bankService = new BankServiceImpl(accountRepository);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            BankController controller = new BankController(bankService, executor, reader);
            controller.start();
        } catch (IOException e) {
            ConsoleUtils.printError("Error reading input: " + e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                ConsoleUtils.printError("Error closing reader: " + e.getMessage());
            }
        }
    }
}