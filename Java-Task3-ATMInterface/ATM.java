import java.util.Scanner;

public class ATM {
    private Bank bank;
    private Scanner scanner;
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("===== ATM MACHINE =====");

        boolean loggedIn = false;

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account account = bank.getAccount(userId);

            if (account != null && account.checkPin(pin)) {
                currentAccount = account;
                loggedIn = true;
                System.out.println("\nLogin Successful!\n");
                break;
            } else {
                System.out.println("Invalid User ID or PIN.");

                if (attempt < 3) {
                    System.out.println("Attempts remaining: " + (3 - attempt));
                }
            }
        }

        if (!loggedIn) {
            System.out.println("\nAccess Denied. Too many incorrect attempts.");
            return;
        }

        menu();
    }

    private void menu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("\nThank you for using the ATM.");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void showTransactionHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (currentAccount.getTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction transaction : currentAccount.getTransactions()) {
                System.out.println(transaction);
            }
        }

        System.out.println("Current Balance: ₹" + currentAccount.getBalance());
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (!currentAccount.withdraw(amount)) {
            System.out.println("Insufficient Funds.");
        } else {
            currentAccount.addTransaction(
                new Transaction("WITHDRAW", amount, "Cash withdrawn")
            );

            System.out.println("Withdrawal successful.");
            System.out.println("Remaining Balance: ₹" + currentAccount.getBalance());
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        currentAccount.deposit(amount);

        currentAccount.addTransaction(
            new Transaction("DEPOSIT", amount, "Cash deposited")
        );

        System.out.println("Deposit successful.");
        System.out.println("Current Balance: ₹" + currentAccount.getBalance());
    }

    private void transfer() {
        scanner.nextLine();

        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine();

        Account recipient = bank.getAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient == currentAccount) {
            System.out.println("Cannot transfer to your own account.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (!currentAccount.withdraw(amount)) {
            System.out.println("Insufficient Funds.");
            return;
        }

        recipient.deposit(amount);

        currentAccount.addTransaction(
            new Transaction("TRANSFER", amount, "Transferred to " + recipientId)
        );

        recipient.addTransaction(
            new Transaction("RECEIVED", amount,
                "Received from " + currentAccount.getUserId())
        );

        System.out.println("Transfer successful.");
        System.out.println("Remaining Balance: ₹" + currentAccount.getBalance());
    }
}
