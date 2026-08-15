/**
 * Stores the result of one completed Number Guessing Game round.
 */
public class GameResult {
    private final int roundNumber;
    private final int secretNumber;
    private final int attemptsUsed;
    private final int maxAttempts;
    private final boolean won;
    private final GameDifficulty difficulty;

    public GameResult(
            int roundNumber,
            int secretNumber,
            int attemptsUsed,
            int maxAttempts,
            boolean won,
            GameDifficulty difficulty) {
        this.roundNumber = roundNumber;
        this.secretNumber = secretNumber;
        this.attemptsUsed = attemptsUsed;
        this.maxAttempts = maxAttempts;
        this.won = won;
        this.difficulty = difficulty;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public boolean isWon() {
        return won;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public String getResultSummary() {
        if (won) {
            return String.format(
                    "Round %d | %s | Won in %d/%d attempts",
                    roundNumber,
                    difficulty.name(),
                    attemptsUsed,
                    maxAttempts);
        }

        return String.format(
                "Round %d | %s | Lost after %d/%d attempts",
                roundNumber,
                difficulty.name(),
                attemptsUsed,
                maxAttempts);
    }
}
