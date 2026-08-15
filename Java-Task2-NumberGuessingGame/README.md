# Number Guessing Game

## OASIS INFOBYTE - Java Development - Task 2

A Java Number Guessing Game implemented with both a console interface and a Java Swing graphical interface.

## Features

- Random number generation
- Easy, Medium, and Hard difficulty levels
- Limited attempts for each difficulty
- Too High / Too Low feedback
- Input validation
- Multiple rounds
- Win/loss tracking
- Session statistics
- Swing GUI

## Difficulty Levels

| Level | Range | Attempts |
|---|---:|---:|
| Easy | 1-50 | 10 |
| Medium | 1-100 | 7 |
| Hard | 1-200 | 5 |

## Files

- `GameDifficulty.java` - Difficulty configuration
- `GameResult.java` - Stores completed round results
- `GameEngine.java` - Core game logic and session tracking
- `GUINumberGuessingGame.java` - Swing GUI
- `ConsoleNumberGuessingGame.java` - Console interface

## Run the GUI

```bash
javac -encoding UTF-8 *.java
java GUINumberGuessingGame
