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
- Clean console interface with card suit symbols (S H D C)
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
```

### Card Display:
- Hearts: H (e.g., AH, KH)
- Diamonds: D (e.g., AD, KD) 
- Clubs: C (e.g., AC, KC)
- Spades: S (e.g., AS, KS)

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
- `run_java.bat` - Windows batch file for easy execution
- `high_scores.txt` - Persistent high score storage (created automatically)

## Game Rules Summary

- Standard 52-card deck
- 5 cards per player per round
- Card values: 2-10 = face value, J/Q/K = 10, A = 11
- Score = highest single suit total + 5 bonus if matches chosen bonus suit
- Winner has highest total score across all rounds

## Example Game Flow

### Round 1
**Player 1**
- Initial Hand: 4♦, 7♦, 2♠, 8♦, 9♠
- Chosen Bonus Suit: ♦
- Action: Drops 2♠ and 9♠
- New Hand: 4♦, 7♦, 10♠, 8♦, Q♦
- Scoring: ♦ totals 29 points
- Since ♦ = bonus suit, +5 bonus
- **Total Score: 34**

**Player 2**
- Initial Hand: A♥, 3♥, 3♠, 6♦, K♠
- Chosen Bonus Suit: ♥
- Action: Drops 6♦
- New Hand: A♥, 3♥, 3♠, 6♠, K♠
- Scoring: ♠ totals 19 points
- Highest suit (♠) ≠ bonus suit (♥) → no bonus
- **Total Score: 19**

### Round 2
**Player 1**
- Initial Hand: J♦, 8♠, Q♠, K♥, 9♥
- Chosen Bonus Suit: ♠
- Action: Drops J♦, K♥, 9♥
- New Hand: J♣, A♠, 5♠, 10♦, Q♣
- Scoring: ♣ totals 20 points
- Highest suit (♣) ≠ bonus suit (♠) → no bonus
- **Total Score: 20**

**Player 2**
- Initial Hand: A♣, 4♦, 7♣, 8♥, 5♣
- Chosen Bonus Suit: ♣
- Action: Drops 4♦ and 8♥
- New Hand: A♣, 7♠, 7♣, 4♥, 5♣
- Scoring: ♣ totals 23 points (A+7+5 = 11+7+5)
- Matches bonus suit → +5 bonus
- **Total Score: 28**

### Final Results
- **Player 1:** 34 + 20 = 54 points
- **Player 2:** 19 + 28 = 47 points
- **Winner: Player 1!**