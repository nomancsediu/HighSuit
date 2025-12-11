import java.util.*;

public class HighSuitGame {
    private List<Player> players;
    private Deck deck;
    private int rounds;
    private int currentRound;
    private Scanner scanner;
    private HighScoreManager scoreManager;
    private List<GameReplay.RoundData> gameReplays;
    
    public HighSuitGame() {
        players = new ArrayList<>();
        scanner = new Scanner(System.in);
        scoreManager = new HighScoreManager();
        gameReplays = new ArrayList<>();
        currentRound = 0;
    }
    
    public void setupGame() {
        System.out.println("=== HighSuit Card Game ===");
        
        // Number of players
        int numPlayers;
        while (true) {
            try {
                System.out.print("Enter number of players (1 or 2): ");
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers == 1 || numPlayers == 2) break;
                System.out.println("Please enter 1 or 2");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        
        // Player names
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + " (or 'Computer' for AI): ");
            String name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("computer")) {
                players.add(new ComputerPlayer("Computer"));
            } else {
                players.add(new Player(name));
            }
        }
        
        // Number of rounds
        while (true) {
            try {
                System.out.print("Enter number of rounds (1-3): ");
                rounds = Integer.parseInt(scanner.nextLine());
                if (rounds >= 1 && rounds <= 3) break;
                System.out.println("Please enter 1, 2, or 3");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    private void dealInitialHands() {
        deck = new Deck();
        for (Player player : players) {
            player.clearHand();
            for (int i = 0; i < 5; i++) {
                player.addCard(deck.deal());
            }
        }
    }
    
    private void displayHand(Player player) {
        System.out.println("\n" + player.getName() + "'s hand: " + 
            String.join(" ", player.getHand().stream().map(Card::toString).toArray(String[]::new)));
        System.out.println("Maximum single suit score: " + player.getMaxSuitScore());
    }
    
    private String chooseBonusSuit(Player player) {
        if (player instanceof ComputerPlayer) {
            String bonusSuit = ((ComputerPlayer) player).chooseBonusSuit();
            System.out.println(player.getName() + " chooses " + bonusSuit + " as bonus suit");
            return bonusSuit;
        }
        
        Map<String, Integer> suitScores = player.getSuitScores();
        System.out.println("\nSuit scores: " + suitScores);
        
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        while (true) {
            System.out.print("Choose bonus suit (Hearts/Diamonds/Clubs/Spades): ");
            String choice = scanner.nextLine().trim();
            for (String suit : suits) {
                if (suit.equalsIgnoreCase(choice)) {
                    return suit;
                }
            }
            System.out.println("Please enter a valid suit name");
        }
    }
    
    private void replaceCards(Player player) {
        if (player instanceof ComputerPlayer) {
            List<Integer> indices = ((ComputerPlayer) player).chooseCardsToReplace();
            if (!indices.isEmpty()) {
                System.out.println(player.getName() + " replaces cards at positions: " + 
                    indices.stream().map(i -> i + 1).toArray());
                player.removeCards(indices);
                for (int i = 0; i < indices.size() && deck.size() > 0; i++) {
                    player.addCard(deck.deal());
                }
            } else {
                System.out.println(player.getName() + " keeps all cards");
            }
            return;
        }
        
        System.out.println("\n" + player.getName() + "'s current hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }
        
        while (true) {
            try {
                System.out.print("Enter positions to replace (1-5, max 4, comma-separated) or 'none': ");
                String choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("none")) break;
                
                String[] parts = choice.split(",");
                List<Integer> positions = new ArrayList<>();
                for (String part : parts) {
                    positions.add(Integer.parseInt(part.trim()) - 1);
                }
                
                if (positions.size() > 4) {
                    System.out.println("Maximum 4 cards can be replaced");
                    continue;
                }
                
                boolean valid = true;
                for (int pos : positions) {
                    if (pos < 0 || pos >= 5) {
                        valid = false;
                        break;
                    }
                }
                
                if (valid) {
                    player.removeCards(positions);
                    for (int i = 0; i < positions.size() && deck.size() > 0; i++) {
                        player.addCard(deck.deal());
                    }
                    break;
                } else {
                    System.out.println("Please enter valid positions (1-5)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers");
            }
        }
    }
    
    private int calculateRoundScore(Player player, String bonusSuit) {
        Map<String, Integer> suitScores = player.getSuitScores();
        int maxScore = Collections.max(suitScores.values());
        String bestSuit = Collections.max(suitScores.entrySet(), Map.Entry.comparingByValue()).getKey();
        
        int bonus = bestSuit.equals(bonusSuit) ? 5 : 0;
        int total = maxScore + bonus;
        
        System.out.println(player.getName() + " - Best suit: " + bestSuit + " (" + maxScore + " points)");
        if (bonus > 0) {
            System.out.println("Bonus suit match! +5 points");
        }
        System.out.println("Round score: " + total);
        
        return total;
    }
    
    public void playRound() {
        System.out.println("\n=== Round " + (currentRound + 1) + " ===");
        dealInitialHands();
        
        GameReplay.RoundData roundData = new GameReplay.RoundData(currentRound + 1);
        
        for (Player player : players) {
            displayHand(player);
            
            List<String> initialHand = player.getHand().stream()
                .map(Card::toString).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            
            String bonusSuit = chooseBonusSuit(player);
            replaceCards(player);
            
            System.out.println("\n" + player.getName() + "'s final hand: " + 
                String.join(" ", player.getHand().stream().map(Card::toString).toArray(String[]::new)));
            
            int score = calculateRoundScore(player, bonusSuit);
            player.addRoundScore(score);
            
            List<String> finalHand = player.getHand().stream()
                .map(Card::toString).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            String bestSuit = player.getBestSuit();
            
            roundData.players.add(new GameReplay.PlayerRoundData(
                player.getName(), initialHand, bonusSuit, finalHand, bestSuit, score));
        }
        
        gameReplays.add(roundData);
        currentRound++;
    }
    
    public List<Player> displayFinalScores() {
        System.out.println("\n=== Final Scores ===");
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((a, b) -> Integer.compare(b.getTotalScore(), a.getTotalScore()));
        
        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            System.out.println((i + 1) + ". " + player.getName() + ": " + player.getTotalScore() + " points");
            System.out.println("   Round scores: " + player.getRoundScores());
        }
        
        System.out.println("\nWinner: " + sortedPlayers.get(0).getName() + "!");
        return sortedPlayers;
    }
    
    private void updateHighScores(List<Player> sortedPlayers) {
        scoreManager.updateHighScores(sortedPlayers, rounds);
        
        System.out.print("\nView high score table? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            scoreManager.displayHighScores();
        }
    }
    
    private void showReplay() {
        System.out.print("\nView game replay? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (!choice.equals("y")) return;
        
        System.out.println("\n=== Game Replay ===");
        for (GameReplay.RoundData roundData : gameReplays) {
            System.out.println("\nRound " + roundData.round + ":");
            for (GameReplay.PlayerRoundData playerData : roundData.players) {
                System.out.println("\n" + playerData.name + ":");
                System.out.println("  Initial hand: " + String.join(" ", playerData.initialHand));
                System.out.println("  Bonus suit: " + playerData.bonusSuit);
                System.out.println("  Final hand: " + String.join(" ", playerData.finalHand));
                System.out.println("  Best suit: " + playerData.bestSuit);
                System.out.println("  Score: " + playerData.score);
            }
        }
    }
    
    public void play() {
        setupGame();
        
        for (int i = 0; i < rounds; i++) {
            playRound();
        }
        
        List<Player> sortedPlayers = displayFinalScores();
        updateHighScores(sortedPlayers);
        showReplay();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            HighSuitGame game = new HighSuitGame();
            game.play();
            
            System.out.print("\nPlay another game? (y/n): ");
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("y")) break;
        }
        
        System.out.println("Thanks for playing HighSuit!");
        scanner.close();
    }
}