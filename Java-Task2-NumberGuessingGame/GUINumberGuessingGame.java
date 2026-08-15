import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Professional GUI implementation of the Number Guessing Game using Swing.
 * Features a polished, user-friendly interface with real-time feedback.
 * Optimized for responsive interaction and visual clarity.
 */
public class GUINumberGuessingGame extends JFrame {
    private final GameEngine engine;
    private GameDifficulty selectedDifficulty;
    
    // Main panels
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Screens
    private JPanel welcomeScreen;
    private JPanel difficultyScreen;
    private JPanel gameScreen;
    private JPanel resultScreen;
    
    // Game screen components
    private JLabel roundLabel;
    private JLabel difficultyLabel;
    private JLabel rangeLabel;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JTextField guessInput;
    private JButton guessButton;
    private JProgressBar attemptBar;
    
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(52, 73, 94);

    public GUINumberGuessingGame() {
        this.engine = new GameEngine();
        initializeWindow();
        setupUI();
    }

    /**
     * Configures the JFrame window properties.
     */
    private void initializeWindow() {
        setTitle("🎮 Number Guessing Game - Professional Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon().getImage());
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Sets up the main UI structure with CardLayout for screen switching.
     */
    private void setupUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);

        welcomeScreen = createWelcomeScreen();
        difficultyScreen = createDifficultyScreen();
        gameScreen = createGameScreen();
        resultScreen = createResultScreen();

        mainPanel.add(welcomeScreen, "WELCOME");
        mainPanel.add(difficultyScreen, "DIFFICULTY");
        mainPanel.add(gameScreen, "GAME");
        mainPanel.add(resultScreen, "RESULT");

        add(mainPanel);
        cardLayout.show(mainPanel, "WELCOME");
    }

    /**
     * Creates the welcome screen with game introduction.
     */
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Title
        JLabel titleLabel = new JLabel("🎮 Number Guessing Game");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Description
        JTextArea descriptionArea = new JTextArea(
            "Welcome to the ultimate number guessing challenge!\n\n" +
            "🎯 How to Play:\n" +
            "• The computer generates a secret number\n" +
            "• You have a limited number of attempts to guess it\n" +
            "• After each guess, you'll receive feedback:\n" +
            "  - \"Too Low\" means guess higher\n" +
            "  - \"Too High\" means guess lower\n" +
            "  - Correct number wins the round!\n\n" +
            "🎲 Features:\n" +
            "• 3 difficulty levels (Easy, Medium, Hard)\n" +
            "• Attempt tracking and scoring\n" +
            "• Session statistics\n" +
            "• Play multiple rounds"
        );
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        descriptionArea.setForeground(TEXT_COLOR);

        // Start button
        JButton startButton = createStyledButton("START GAME", PRIMARY_COLOR);
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "DIFFICULTY"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.add(startButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descriptionArea, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the difficulty selection screen.
     */
    private JPanel createDifficultyScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Select Difficulty Level");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, gbc);

        gbc.insets = new Insets(20, 10, 10, 10);

        // Difficulty buttons
        GameDifficulty[] difficulties = GameDifficulty.values();
        for (GameDifficulty difficulty : difficulties) {
            JButton button = createDifficultyButton(difficulty);
            panel.add(button, gbc);
        }

        // Add glue for spacing
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    /**
     * Creates a styled difficulty button.
     */
    private JButton createDifficultyButton(GameDifficulty difficulty) {
        JButton button = new JButton(
            String.format("<html>%s<br/><small>%s</small></html>",
                          difficulty.name(),
                          difficulty.getDescription())
        );
        
        button.setPreferredSize(new Dimension(500, 80));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 237, 252));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        button.addActionListener(e -> startGameWithDifficulty(difficulty));
        return button;
    }

    /**
     * Starts a new game with selected difficulty.
     */
    private void startGameWithDifficulty(GameDifficulty difficulty) {
        selectedDifficulty = difficulty;
        engine.startNewRound(difficulty);
        updateGameScreen();
        cardLayout.show(mainPanel, "GAME");
        guessInput.requestFocus();
    }

    /**
     * Creates the main game screen with input and feedback.
     */
    private JPanel createGameScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top panel: Round and difficulty info
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        topPanel.setBackground(BACKGROUND_COLOR);
        
        roundLabel = createInfoLabel("Round: 1", 18, PRIMARY_COLOR);
        difficultyLabel = createInfoLabel("Difficulty: Medium", 16, TEXT_COLOR);
        rangeLabel = createInfoLabel("Range: 1-100", 14, TEXT_COLOR);
        
        topPanel.add(roundLabel);
        topPanel.add(difficultyLabel);
        topPanel.add(rangeLabel);

        // Center panel: Main game area
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        // Current attempt display
        attemptsLabel = createInfoLabel("Attempt 1 of 7", 16, TEXT_COLOR);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(attemptsLabel, gbc);

        // Progress bar
        attemptBar = new JProgressBar(0, 7);
        attemptBar.setPreferredSize(new Dimension(400, 25));
        attemptBar.setForeground(SUCCESS_COLOR);
        attemptBar.setStringPainted(true);
        centerPanel.add(attemptBar, gbc);

        // Input area
        JLabel inputLabel = createInfoLabel("Enter your guess:", 14, TEXT_COLOR);
        centerPanel.add(inputLabel, gbc);

        guessInput = new JTextField(15);
        guessInput.setFont(new Font("Segoe UI", Font.BOLD, 20));
        guessInput.setHorizontalAlignment(JTextField.CENTER);
        guessInput.addActionListener(e -> submitGuess());
        centerPanel.add(guessInput, gbc);

        // Guess button
        guessButton = createStyledButton("SUBMIT GUESS", PRIMARY_COLOR);
        guessButton.addActionListener(e -> submitGuess());
        centerPanel.add(guessButton, gbc);

        // Feedback label
        gbc.insets = new Insets(20, 10, 10, 10);
        feedbackLabel = new JLabel("Make your first guess!");
        feedbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        feedbackLabel.setForeground(PRIMARY_COLOR);
        feedbackLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(feedbackLabel, gbc);

        // Add glue
        gbc.weighty = 1.0;
        centerPanel.add(Box.createVerticalGlue(), gbc);

        // Bottom panel: Quit button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BACKGROUND_COLOR);
        JButton quitButton = createStyledButton("QUIT GAME", ERROR_COLOR);
        quitButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to quit?", 
                "Quit Game", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                cardLayout.show(mainPanel, "WELCOME");
            }
        });
        bottomPanel.add(quitButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Processes player's guess and updates UI.
     */
    private void submitGuess() {
        String input = guessInput.getText().trim();
        
        if (input.isEmpty()) {
            showFeedback("Please enter a number!", ERROR_COLOR);
            return;
        }

        try {
            int guess = Integer.parseInt(input);
            GameEngine.GuessResult result = engine.processGuess(guess);
            handleGuessResult(result, guess);
        } catch (NumberFormatException e) {
            showFeedback("Invalid input! Please enter a valid number.", ERROR_COLOR);
        }

        guessInput.setText("");
        guessInput.requestFocus();
    }

    /**
     * Handles the result of a guess.
     */
    private void handleGuessResult(GameEngine.GuessResult result, int guess) {
        switch (result) {
            case TOO_LOW:
                showFeedback("📈 Too Low! Try a higher number.", WARNING_COLOR);
                updateGameScreen();
                break;
                
            case TOO_HIGH:
                showFeedback("📉 Too High! Try a lower number.", WARNING_COLOR);
                updateGameScreen();
                break;
                
            case CORRECT:
                showFeedback("🎉 Correct! You won!", SUCCESS_COLOR);
                guessButton.setEnabled(false);
                guessInput.setEnabled(false);
                scheduleResultScreen(true);
                break;
                
            case GAME_OVER:
                showFeedback("❌ Game Over! Maximum attempts reached.", ERROR_COLOR);
                guessButton.setEnabled(false);
                guessInput.setEnabled(false);
                scheduleResultScreen(false);
                break;
                
            case INVALID_RANGE:
                showFeedback("Invalid number range!", ERROR_COLOR);
                break;
                
            default:
                showFeedback("An error occurred.", ERROR_COLOR);
        }
    }

    /**
     * Updates game screen display elements.
     */
    private void updateGameScreen() {
        roundLabel.setText(String.format("Round: %d", engine.getRoundNumber()));
        difficultyLabel.setText(String.format("Difficulty: %s", 
                                             engine.getCurrentDifficulty().name()));
        GameDifficulty difficulty = engine.getCurrentDifficulty();
        rangeLabel.setText(String.format("Range: %d - %d", 
                                         difficulty.getMinRange(),
                                         difficulty.getMaxRange()));
        
        int current = engine.getCurrentAttempt();
        int max = engine.getCurrentDifficulty().getMaxAttempts();
        attemptsLabel.setText(String.format("Attempt %d of %d", current + 1, max));
        
        attemptBar.setMaximum(max);
        attemptBar.setValue(current);
        
        if (current >= max - 2) {
            attemptBar.setForeground(ERROR_COLOR);
        } else if (current >= max / 2) {
            attemptBar.setForeground(WARNING_COLOR);
        } else {
            attemptBar.setForeground(SUCCESS_COLOR);
        }
    }

    /**
     * Creates the result screen showing round summary.
     */
    private JPanel createResultScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Result content (dynamic)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setName("RESULT_CONTENT");

        JButton playAgainButton = createStyledButton("PLAY AGAIN", PRIMARY_COLOR);
        playAgainButton.addActionListener(e -> cardLayout.show(mainPanel, "DIFFICULTY"));

        JButton viewStatsButton = createStyledButton("VIEW STATISTICS", PRIMARY_COLOR);
        viewStatsButton.addActionListener(e -> showSessionStats());

        JButton mainMenuButton = createStyledButton("MAIN MENU", WARNING_COLOR);
        mainMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(viewStatsButton);
        buttonPanel.add(mainMenuButton);

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Schedules transition to result screen after a delay.
     */
    private void scheduleResultScreen(boolean won) {
        Timer timer = new Timer(1500, e -> {
            displayResultScreen(won);
            cardLayout.show(mainPanel, "RESULT");
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Displays result content on result screen.
     */
    private void displayResultScreen(boolean won) {
        JPanel resultContent = (JPanel) resultScreen.getComponent(0);
        resultContent.removeAll();

        GameResult lastResult = engine.getLastResult();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        if (lastResult != null) {
            String title = won ? "🎉 ROUND WON!" : "❌ ROUND LOST!";
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            titleLabel.setForeground(won ? SUCCESS_COLOR : ERROR_COLOR);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            resultContent.add(titleLabel, gbc);

            gbc.insets = new Insets(20, 10, 5, 10);
            
            JLabel detailLabel = new JLabel(lastResult.getResultSummary());
            detailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            detailLabel.setForeground(TEXT_COLOR);
            detailLabel.setHorizontalAlignment(JLabel.CENTER);
            resultContent.add(detailLabel, gbc);

            if (!won) {
                JLabel secretLabel = new JLabel("The secret number was: " + lastResult.getSecretNumber());
                secretLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                secretLabel.setForeground(TEXT_COLOR);
                secretLabel.setHorizontalAlignment(JLabel.CENTER);
                resultContent.add(secretLabel, gbc);
            }
        }

        resultContent.revalidate();
        resultContent.repaint();
    }

    /**
     * Shows session statistics in a dialog.
     */
    private void showSessionStats() {
        List<GameResult> history = engine.getGameHistory();
        
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No games played yet!");
            return;
        }

        StringBuilder stats = new StringBuilder();
        stats.append("SESSION STATISTICS\n\n");
        stats.append(String.format("Total Rounds: %d\n", history.size()));
        stats.append(String.format("Total Wins: %d\n", engine.getTotalWins()));
        stats.append(String.format("Win Rate: %.1f%%\n\n", 
                                   (engine.getTotalWins() * 100.0) / history.size()));
        
        stats.append("Detailed Results:\n");
        stats.append("-".repeat(50)).append("\n");
        
        for (GameResult result : history) {
            String status = result.isWon() ? "✅ WON" : "❌ LOST";
            stats.append(String.format("%s | Round %d (%s) | %d/%d attempts\n",
                                      status,
                                      result.getRoundNumber(),
                                      result.getDifficulty().name(),
                                      result.getAttemptsUsed(),
                                      result.getMaxAttempts()));
        }

        JTextArea textArea = new JTextArea(stats.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Session Statistics", 
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates a styled button with consistent appearance.
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }

    /**
     * Creates an info label with specified properties.
     */
    private JLabel createInfoLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    /**
     * Displays feedback message to user.
     */
    private void showFeedback(String message, Color color) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(color);
    }

    /**
     * Entry point for GUI application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUINumberGuessingGame game = new GUINumberGuessingGame();
            game.setVisible(true);
        });
    }
}
