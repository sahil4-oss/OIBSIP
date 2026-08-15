import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();

        accounts.put("user1", new Account("user1", "1234", 10000));
        accounts.put("user2", new Account("user2", "5678", 5000));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}
