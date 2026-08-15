import java.util.ArrayList;

public class Account {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        transactions = new ArrayList<>();
    }

    public String getUserId() { return userId; }

    public boolean checkPin(String pin) { return this.pin.equals(pin); }

    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }

    public void addTransaction(Transaction transaction) { transactions.add(transaction); }

    public ArrayList<Transaction> getTransactions() { return transactions; }
}
