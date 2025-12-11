# HighSuit Card Game - Java Implementation

**Author:** Noman  
**Repository:** https://github.com/nomancsediu/HighSuit  
**Language:** Java  
**Assignment:** Individual Development Exercise (60%)

A complete Java implementation of the HighSuit card game supporting all 9 required levels of functionality.

## Features Implemented

### Level 1: Basic Setup
- Player selection (1 or 2 players)
- Player naming with Computer AI option
- Deal 5 cards from shuffled deck
- Display hands in console

### Level 2: Bonus Suit Selection
- Display maximum achievable single-suit score
- Player selects bonus suit for the round

### Level 3: Card Replacement
- Players can swap up to 4 cards
- Replacements dealt from remaining deck
- Updated hands displayed

### Level 4: Scoring System
- Calculate final scores with proper card values
- 5-point bonus for matching bonus suit
- Display round scores

### Level 5: Multiple Rounds
- Support 1-3 rounds per game
- Track total scores across rounds
- Display final rankings

### Level 6: High Score Table
- Persistent high score storage (CSV file)
- Top 5 scores based on average per round
- Optional viewing after each game

### Level 7: Computer Player
- AI player with optimal strategy
- Automatically selects best suit as bonus
- Replaces cards not matching best suit
- Clearly documented decision logic

### Level 8: Game Replay
- Complete replay of all rounds
- Shows initial hands, bonus suits, replacements, final hands, and scores
- Optional viewing after game completion

### Level 9: Enhanced Features
- Clean console interface with Unicode card symbols (♠ ♥ ♦ ♣)
- Input validation and error handling
- Persistent data storage
- Play multiple games in session

## How to Compile and Run

```bash
# Compile all Java files
javac *.java

# Run the main game (Windows)
run_java.bat

# Run the main game (Linux/Mac)
java -Dfile.encoding=UTF-8 HighSuitGame

# Run tests
java -Dfile.encoding=UTF-8 TestGame

# Run demo
java -Dfile.encoding=UTF-8 DemoGame
```

### For Windows Users:
To see Unicode card symbols (♠ ♥ ♦ ♣) properly:
1. Open Command Prompt
2. Run: `chcp 65001`
3. Then run the game using `run_java.bat`

## Computer Player Logic

The computer player uses the following strategy:
1. **Bonus Suit Selection**: Always chooses the suit with the highest current total
2. **Card Replacement**: Keeps all cards of the best suit, replaces up to 4 cards of other suits
3. **Optimization**: Maximizes probability of achieving highest score with bonus

This strategy is optimal because:
- It focuses on the suit most likely to yield the highest score
- It maximizes the chance of getting the 5-point bonus
- It replaces the least valuable cards first

## File Structure

- `Card.java` - Represents individual playing cards
- `Deck.java` - Manages 52-card deck with shuffling
- `Player.java` - Human player with hand management
- `ComputerPlayer.java` - AI player extending Player
- `GameReplay.java` - Data structures for replay functionality
- `HighScoreManager.java` - Persistent score storage
- `HighSuitGame.java` - Main game controller
- `TestGame.java` - Unit tests
- `DemoGame.java` - Demonstration
- `run_java.bat` - Windows batch file for easy execution
- `high_scores.txt` - Persistent high score storage (created automatically)

## Game Rules Summary

- Standard 52-card deck
- 5 cards per player per round
- Card values: 2-10 = face value, J/Q/K = 10, A = 11
- Score = highest single suit total + 5 bonus if matches chosen bonus suit
- Winner has highest total score across all rounds

## Example Game Flow

```
=== HighSuit Card Game ===
Enter number of players (1 or 2): 2
Enter name for Player 1: Alice
Enter name for Player 2: Computer

=== Round 1 ===
Alice's hand: 4♦ 7♦ 2♠ 8♦ 9♠
Maximum single suit score: 19
Choose bonus suit: Diamonds
Replace cards: 2♠, 9♠
Final hand: 4♦ 7♦ 10♠ 8♦ Q♦
Score: 29 + 5 = 34

Computer chooses Spades as bonus suit
Computer replaces 2 cards
Final score: 28

Winner: Alice!
```