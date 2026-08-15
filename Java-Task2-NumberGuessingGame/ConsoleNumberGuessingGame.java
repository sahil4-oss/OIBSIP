import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based Number Guessing Game.
 * Provides interactive command-line interface with full game features.
 * Optimized for clarity and responsive user feedback.
 */
public class ConsoleNumberGuessingGame {
    private final GameEngine engine;
    private final Scanner scanner;
    private static final String SEPARATOR = "════════════════════════════════════════";

    public ConsoleNumberGuessingGame() {
        this.engine = new GameEngine();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main entry point for the console game.
     */
    public static void main(String[] args) {
        ConsoleNumberGuessingGame game = new ConsoleNumberGuessingGame();
        game.run();
    }

    /**
     * Main game loop - orchestrates the entire game flow.
     */
    public void run() {
        displayWelcomeScreen();
        
        boolean playAgain = true;
        while (playAgain) {
            GameDifficulty difficulty = selectDifficulty();
            playRound(difficulty);
            playAgain = askPlayAgain();
        }
        
        displaySessionSummary();
        scanner.close();
    }

    /**
     * Displays welcome banner and instructions.
     */
    private void displayWelcomeScreen() {
        clearConsole();
        System.out.println("\n" + SEPARATOR);
        System.out.println("     🎮  NUMBER GUESSING GAME  🎮");
        System.out.println(SEPARATOR);
        System.out.println("\nWelcome to the ultimate number guessing challenge!");
        System.out.println("Try to guess the computer's secret number.");
        System.out.println("You'll get hints if your guess is too high or too low.");
        System.out.println("\n" + SEPARATOR + "\n");
    }

    /**
     * Displays difficulty selection menu and handles user choice.
     * 
     * @return Selected GameDifficulty
     */
    private GameDifficulty selectDifficulty() {
        System.out.println("📊 SELECT DIFFICULTY LEVEL:\n");
        GameDifficulty[] difficulties = GameDifficulty.values();
        
        for (int i = 0; i < difficulties.length; i++) {
            System.out.printf("  %d. %s\n", i + 1, difficulties[i].getDescription());
        }
        
        int choice = getValidatedInput(1, difficulties.length);
        return difficulties[choice - 1];
    }

    /**
     * Executes a single game round.
     * Handles all guess processing and user feedback.
     * 
     * @param difficulty The difficulty level for this round
     */
    private void playRound(GameDifficulty difficulty) {
        engine.startNewRound(difficulty);
        clearConsole();
        
        System.out.println("\n" + SEPARATOR);
        System.out.printf("🎮 ROUND %d - %s MODE\n", engine.getRoundNumber(), 
                          difficulty.name());
        System.out.println(SEPARATOR);
        System.out.printf("\n🔢 Range: %d to %d | Attempts: %d\n\n",
                          difficulty.getMinRange(),
                          difficulty.getMaxRange(),
                          difficulty.getMaxAttempts());

        boolean roundActive = true;
        while (roundActive && engine.isGameActive()) {
            displayGameStatus();
            
            int guess = getGuessFromUser();
            GameEngine.GuessResult result = engine.processGuess(guess);
            
            roundActive = processGuessResult(result, guess);
        }
    }

    /**
     * Displays current game status including attempts used and remaining.
     */
    private void displayGameStatus() {
        int used = engine.getCurrentAttempt();
        int remaining = engine.getRemainingAttempts();
        int total = engine.getCurrentDifficulty().getMaxAttempts();
        
        System.out.printf("Attempt %d/%d | Remaining: %d\n", used + 1, total, remaining);
        System.out.print("Enter your guess: ");
    }

    /**
     * Gets validated user input for a guess.
     * Handles parsing and range validation.
     * 
     * @return Valid user guess
     */
    private int getGuessFromUser() {
        GameDifficulty difficulty = engine.getCurrentDifficulty();
        
        while (true) {
            try {
                int guess = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                // Pre-check range to provide immediate feedback
                if (guess < difficulty.getMinRange() || 
                    guess > difficulty.getMaxRange()) {
                    System.out.printf("❌ Please enter a number between %d and %d: ",
                                      difficulty.getMinRange(),
                                      difficulty.getMaxRange());
                    continue;
                }
                
                return guess;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input
                System.out.print("❌ Invalid input! Please enter a valid number: ");
            }
        }
    }

    /**
     * Processes the result of a guess and provides feedback.
     * 
     * @param result The GuessResult enum value
     * @param guess The player's guess
     * @return true if round should continue, false if round is over
     */
    private boolean processGuessResult(GameEngine.GuessResult result, int guess) {
        switch (result) {
            case TOO_LOW:
                System.out.printf("📈 %s (You guessed: %d)\n\n", 
                                 result.getMessage(), guess);
                return true;
                
            case TOO_HIGH:
                System.out.printf("📉 %s (You guessed: %d)\n\n", 
                                 result.getMessage(), guess);
                return true;
                
            case CORRECT:
                System.out.println(result.getMessage());
                GameResult lastResult = engine.getLastResult();
                if (lastResult != null) {
                    System.out.printf("\n✨ Final Score: %s\n\n",
                                     lastResult.getResultSummary());
                }
                return false;
                
            case GAME_OVER:
                System.out.println("\n❌ " + result.getMessage());
                GameResult gameOverResult = engine.getLastResult();
                if (gameOverResult != null) {
                    System.out.printf("\nThe secret number was: %d\n\n",
                                     gameOverResult.getSecretNumber());
                }
                return false;
                
            case INVALID_RANGE:
                System.out.printf("⚠️  %s\n", result.getMessage());
                return true;
                
            default:
                System.out.println("⚠️  " + result.getMessage());
                return false;
        }
    }

    /**
     * Prompts player to play another round.
     * 
     * @return true if player wants to continue, false otherwise
     */
    private boolean askPlayAgain() {
        System.out.print("Play another round? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    /**
     * Displays comprehensive session summary with statistics.
     */
    private void displaySessionSummary() {
        clearConsole();
        System.out.println("\n" + SEPARATOR);
        System.out.println("     📊 SESSION SUMMARY 📊");
        System.out.println(SEPARATOR + "\n");

        List<GameResult> history = engine.getGameHistory();
        
        if (history.isEmpty()) {
            System.out.println("No games played. Thanks for trying!");
            return;
        }

        System.out.printf("Total Rounds Played: %d\n", history.size());
        System.out.printf("Total Wins: %d\n", engine.getTotalWins());
        System.out.printf("Win Rate: %.1f%%\n\n", 
                         (engine.getTotalWins() * 100.0) / history.size());

        System.out.println("📋 Detailed Results:");
        System.out.println("-".repeat(50));
        
        for (GameResult result : history) {
            String status = result.isWon() ? "✅ WON" : "❌ LOST";
            System.out.printf("%s | Round %d (%s) | Attempts: %d/%d\n",
                            status,
                            result.getRoundNumber(),
                            result.getDifficulty().name(),
                            result.getAttemptsUsed(),
                            result.getMaxAttempts());
        }

        System.out.println("-".repeat(50));
        System.out.println("\n🎮 Thanks for playing! See you next time!\n");
    }

    /**
     * Gets validated integer input within specified range.
     * Utility method for menu selections.
     * 
     * @param min Minimum acceptable value
     * @param max Maximum acceptable value
     * @return Valid integer within range
     */
    private int getValidatedInput(int min, int max) {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.printf("❌ Please enter a number between %d and %d.\n", 
                                 min, max);
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Clears the console (works on most terminals).
     * Provides visual separation between rounds.
     */
    private void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: just print newlines
            System.out.println("\n".repeat(50));
        }
    }
}
