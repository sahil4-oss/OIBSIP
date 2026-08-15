import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Core game engine for the Number Guessing Game.
 * Handles game logic, state management, and round tracking.
 */
public class GameEngine {
    private final Random random;
    private final List<GameResult> gameHistory;

    private GameDifficulty currentDifficulty;
    private int secretNumber;
    private int currentAttempt;
    private int roundNumber;
    private boolean gameActive;

    public GameEngine() {
        this.random = new Random();
        this.gameHistory = new ArrayList<>();
        this.currentDifficulty = GameDifficulty.MEDIUM;
        this.roundNumber = 0;
        this.gameActive = false;
    }

    public void startNewRound(GameDifficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.roundNumber++;
        this.currentAttempt = 0;
        this.gameActive = true;

        this.secretNumber = random.nextInt(
                difficulty.getMaxRange() - difficulty.getMinRange() + 1)
                + difficulty.getMinRange();
    }

    public GuessResult processGuess(int guess) {
        if (!gameActive) {
            return GuessResult.GAME_INACTIVE;
        }

        if (guess < currentDifficulty.getMinRange()
                || guess > currentDifficulty.getMaxRange()) {
            return GuessResult.INVALID_RANGE;
        }

        currentAttempt++;

        if (guess == secretNumber) {
            gameActive = false;
            recordResult(true);
            return GuessResult.CORRECT;
        }

        if (currentAttempt >= currentDifficulty.getMaxAttempts()) {
            gameActive = false;
            recordResult(false);
            return GuessResult.GAME_OVER;
        }

        return guess < secretNumber
                ? GuessResult.TOO_LOW
                : GuessResult.TOO_HIGH;
    }

    private void recordResult(boolean won) {
        gameHistory.add(new GameResult(
                roundNumber,
                secretNumber,
                currentAttempt,
                currentDifficulty.getMaxAttempts(),
                won,
                currentDifficulty));
    }

    public double getLastRoundEfficiency() {
        if (gameHistory.isEmpty()) {
            return 0.0;
        }

        GameResult lastResult = gameHistory.get(gameHistory.size() - 1);

        if (!lastResult.isWon()) {
            return -1.0;
        }

        return (double) lastResult.getAttemptsUsed()
                / lastResult.getMaxAttempts();
    }

    public int getTotalWins() {
        return (int) gameHistory.stream()
                .filter(GameResult::isWon)
                .count();
    }

    public List<GameResult> getGameHistory() {
        return new ArrayList<>(gameHistory);
    }

    public GameResult getLastResult() {
        return gameHistory.isEmpty()
                ? null
                : gameHistory.get(gameHistory.size() - 1);
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getRemainingAttempts() {
        return Math.max(
                0,
                currentDifficulty.getMaxAttempts() - currentAttempt);
    }

    public GameDifficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public int getTotalRounds() {
        return gameHistory.size();
    }

    public enum GuessResult {
        TOO_LOW("Too Low! Try a higher number."),
        TOO_HIGH("Too High! Try a lower number."),
        CORRECT("Correct! You won!"),
        GAME_OVER("Game Over! Maximum attempts reached."),
        INVALID_RANGE("Invalid! Number must be in the specified range."),
        GAME_INACTIVE("Game is not active. Start a new round.");

        private final String message;

        GuessResult(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
