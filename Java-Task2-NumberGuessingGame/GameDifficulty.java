/**
 * Represents the available difficulty levels for the Number Guessing Game.
 */
public enum GameDifficulty {
    EASY(1, 50, 10, "Easy: Guess a number from 1 to 50 in 10 attempts"),
    MEDIUM(1, 100, 7, "Medium: Guess a number from 1 to 100 in 7 attempts"),
    HARD(1, 200, 5, "Hard: Guess a number from 1 to 200 in 5 attempts");

    private final int minRange;
    private final int maxRange;
    private final int maxAttempts;
    private final String description;

    GameDifficulty(int minRange, int maxRange, int maxAttempts, String description) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.maxAttempts = maxAttempts;
        this.description = description;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public String getDescription() {
        return description;
    }
}
