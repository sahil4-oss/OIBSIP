import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional Java Swing frontend for the OSIS ATM internship project.
 * Uses the existing Bank, Account and Transaction classes for business logic.
 */
public class ATMFrame extends JFrame {
    private static final Color NAVY = new Color(18, 32, 58);
    private static final Color BLUE = new Color(35, 91, 168);
    private static final Color LIGHT = new Color(245, 247, 250);
    private static final Color TEXT = new Color(35, 43, 55);
    private static final Color MUTED = new Color(105, 115, 130);

    private final Bank bank;
    private Account currentAccount;
    private int loginAttempts;

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);
    private final JLabel balanceLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel();
    private final JLabel accountLabel = new JLabel();
    private final JLabel loginMessage = new JLabel(" ");
    private final JTextField userIdField = new JTextField();
    private final JPasswordField pinField = new JPasswordField();

    public ATMFrame(Bank bank) {
        this.bank = bank;
        setTitle("ATM Banking System | OSIS Internship Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setResizable(false);

        root.setBackground(LIGHT);
        root.add(buildLoginPanel(), "login");
        root.add(buildDashboard(), "dashboard");
        setContentPane(root);
        cards.show(root, "login");
    }

    private JPanel buildLoginPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(NAVY);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(450, 500));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(34, 46, 30, 46));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("ATM");
        logo.setOpaque(true);
        logo.setBackground(BLUE);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setPreferredSize(new Dimension(70, 58));
        logo.setMaximumSize(new Dimension(70, 58));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Securely access your bank account");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(logo);
        card.add(Box.createVerticalStrut(18));
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        card.add(fieldLabel("USER ID"));
        styleField(userIdField);
        card.add(userIdField);
        card.add(Box.createVerticalStrut(15));

        card.add(fieldLabel("PIN"));
        styleField(pinField);
        card.add(pinField);
        card.add(Box.createVerticalStrut(22));

        JButton loginButton = primaryButton("LOGIN TO ATM");
        loginButton.addActionListener(e -> login());
        card.add(loginButton);

        loginMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginMessage.setHorizontalAlignment(SwingConstants.CENTER);
        loginMessage.setForeground(new Color(190, 50, 50));
        loginMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        card.add(Box.createVerticalStrut(12));
        card.add(loginMessage);
        card.add(Box.createVerticalGlue());

        JLabel demo = new JLabel("Demo account: user1 / 1234");
        demo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        demo.setForeground(MUTED);
        demo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(demo);

        JLabel footer = new JLabel("OSIS Internship • Java Swing");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(new Color(150, 155, 165));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(6));
        card.add(footer);

        userIdField.addActionListener(e -> pinField.requestFocusInWindow());
        pinField.addActionListener(e -> login());
        outer.add(card);
        return outer;
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(350, 42));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 228)),
                new EmptyBorder(8, 12, 8, 12)));
    }

    private JPanel buildDashboard() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(LIGHT);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel brandTitle = new JLabel("ATM BANKING");
        brandTitle.setForeground(Color.WHITE);
        brandTitle.setFont(new Font("SansSerif", Font.BOLD, 21));
        JLabel brandSub = new JLabel("OSIS Internship Project");
        brandSub.setForeground(new Color(190, 202, 220));
        brandSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        brand.add(brandTitle);
        brand.add(Box.createVerticalStrut(3));
        brand.add(brandSub);

        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        accountLabel.setForeground(new Color(190, 202, 220));
        accountLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        accountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        userInfo.add(welcomeLabel);
        userInfo.add(Box.createVerticalStrut(3));
        userInfo.add(accountLabel);

        header.add(brand, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);
        page.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(LIGHT);
        content.setBorder(new EmptyBorder(25, 28, 20, 28));

        JPanel balanceCard = new JPanel(new BorderLayout());
        balanceCard.setBackground(BLUE);
        balanceCard.setBorder(new EmptyBorder(22, 25, 22, 25));

        JPanel balanceText = new JPanel();
        balanceText.setOpaque(false);
        balanceText.setLayout(new BoxLayout(balanceText, BoxLayout.Y_AXIS));
        JLabel balanceTitle = new JLabel("AVAILABLE BALANCE");
        balanceTitle.setForeground(new Color(220, 230, 245));
        balanceTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        balanceText.add(balanceTitle);
        balanceText.add(Box.createVerticalStrut(7));
        balanceText.add(balanceLabel);
        balanceCard.add(balanceText, BorderLayout.WEST);

        JLabel cardMark = new JLabel("DEBIT");
        cardMark.setForeground(Color.WHITE);
        cardMark.setFont(new Font("SansSerif", Font.BOLD, 14));
        balanceCard.add(cardMark, BorderLayout.EAST);
        content.add(balanceCard, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridLayout(2, 3, 15, 15));
        actions.setOpaque(false);
        actions.add(actionButton("Transaction History", "View recent transactions", e -> showTransactions()));
        actions.add(actionButton("Withdraw Cash", "Take money from your account", e -> withdraw()));
        actions.add(actionButton("Deposit Money", "Add money to your account", e -> deposit()));
        actions.add(actionButton("Transfer Money", "Send money to another account", e -> transfer()));
        actions.add(actionButton("Refresh Balance", "Update the balance shown above", e -> refreshDashboard()));
        actions.add(actionButton("Logout", "Securely end this session", e -> logout()));
        content.add(actions, BorderLayout.CENTER);
        page.add(content, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(12, 28, 12, 28));
        JLabel status = new JLabel("●  Secure session active");
        status.setForeground(new Color(48, 130, 82));
        status.setFont(new Font("SansSerif", Font.BOLD, 11));
        JLabel tech = new JLabel("Java OOP  •  Java Swing  •  HashMap  •  ArrayList");
        tech.setForeground(MUTED);
        tech.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.add(status, BorderLayout.WEST);
        footer.add(tech, BorderLayout.EAST);
        page.add(footer, BorderLayout.SOUTH);
        return page;
    }

    private JButton actionButton(String title, String subtitle, java.awt.event.ActionListener listener) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 234)),
                new EmptyBorder(15, 18, 15, 18)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 15));
        t.setForeground(TEXT);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 11));
        s.setForeground(MUTED);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(t);
        text.add(Box.createVerticalStrut(6));
        text.add(s);
        button.add(text, BorderLayout.CENTER);
        button.addActionListener(listener);
        return button;
    }

    private JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void login() {
        String userId = userIdField.getText().trim();
        String pin = new String(pinField.getPassword());

        if (userId.isEmpty() || pin.isEmpty()) {
            loginMessage.setText("Please enter both User ID and PIN.");
            return;
        }

        Account account = bank.getAccount(userId);
        if (account != null && account.checkPin(pin)) {
            currentAccount = account;
            loginAttempts = 0;
            loginMessage.setText(" ");
            userIdField.setText("");
            pinField.setText("");
            refreshDashboard();
            cards.show(root, "dashboard");
            return;
        }

        loginAttempts++;
        int remaining = 3 - loginAttempts;
        if (remaining <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Access denied. Too many incorrect attempts.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        loginMessage.setText("Invalid credentials. Attempts remaining: " + remaining);
        pinField.selectAll();
    }

    private void refreshDashboard() {
        if (currentAccount == null) return;
        welcomeLabel.setText("Welcome, " + currentAccount.getUserId());
        accountLabel.setText("Account: " + currentAccount.getUserId());
        balanceLabel.setText(String.format("₹ %.2f", currentAccount.getBalance()));
    }

    private void showTransactions() {
        if (currentAccount == null) return;
        JTextArea area = new JTextArea(16, 58);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setForeground(TEXT);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setBorder(new EmptyBorder(10, 10, 10, 10));

        StringBuilder text = new StringBuilder();
        text.append("TRANSACTION HISTORY\n");
        text.append("===============================================\n");
        if (currentAccount.getTransactions().isEmpty()) {
            text.append("No transactions yet.\n");
        } else {
            for (Transaction transaction : currentAccount.getTransactions()) {
                text.append(transaction).append('\n');
            }
        }
        text.append("\nAvailable Balance: ₹").append(String.format("%.2f", currentAccount.getBalance()));
        area.setText(text.toString());
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:", "Withdraw Cash", JOptionPane.QUESTION_MESSAGE);
        Double amount = parseAmount(input);
        if (amount == null) return;
        if (!currentAccount.withdraw(amount)) {
            showError("Insufficient funds.\nAvailable balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
            return;
        }
        currentAccount.addTransaction(new Transaction("WITHDRAW", amount, "Cash withdrawn"));
        refreshDashboard();
        showSuccess(String.format("Withdrawal successful.\nRemaining balance: ₹%.2f", currentAccount.getBalance()));
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:", "Deposit Money", JOptionPane.QUESTION_MESSAGE);
        Double amount = parseAmount(input);
        if (amount == null) return;
        currentAccount.deposit(amount);
        currentAccount.addTransaction(new Transaction("DEPOSIT", amount, "Cash deposited"));
        refreshDashboard();
        showSuccess(String.format("Deposit successful.\nCurrent balance: ₹%.2f", currentAccount.getBalance()));
    }

    private void transfer() {
        JTextField recipientField = new JTextField();
        JTextField amountField = new JTextField();
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 12));
        form.setBorder(new EmptyBorder(10, 5, 5, 5));
        form.add(new JLabel("Recipient User ID:"));
        form.add(recipientField);
        form.add(new JLabel("Amount:"));
        form.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, form, "Transfer Money", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String recipientId = recipientField.getText().trim();
        Account recipient = bank.getAccount(recipientId);
        if (recipient == null) {
            showError("Recipient account not found.");
            return;
        }
        if (recipient == currentAccount) {
            showError("You cannot transfer money to your own account.");
            return;
        }

        Double amount = parseAmount(amountField.getText());
        if (amount == null) return;
        if (!currentAccount.withdraw(amount)) {
            showError("Insufficient funds.");
            return;
        }

        recipient.deposit(amount);
        currentAccount.addTransaction(new Transaction("TRANSFER", amount, "Transferred to " + recipientId));
        recipient.addTransaction(new Transaction("RECEIVED", amount, "Received from " + currentAccount.getUserId()));
        refreshDashboard();
        showSuccess(String.format("Transfer successful.\nRemaining balance: ₹%.2f", currentAccount.getBalance()));
    }

    private Double parseAmount(String input) {
        if (input == null) return null;
        try {
            double amount = Double.parseDouble(input.trim());
            if (!Double.isFinite(amount) || amount <= 0) throw new NumberFormatException();
            return amount;
        } catch (NumberFormatException ex) {
            showError("Please enter a valid amount greater than 0.");
            return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Transaction Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) return;
        currentAccount = null;
        loginAttempts = 0;
        loginMessage.setText(" ");
        cards.show(root, "login");
    }

    public static void launch(Bank bank) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }
            new ATMFrame(bank).setVisible(true);
        });
    }
}
