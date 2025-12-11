import java.util.*;

public class Player {
    protected String name;
    protected List<Card> hand;
    protected int totalScore;
    protected List<Integer> roundScores;
    
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.totalScore = 0;
        this.roundScores = new ArrayList<>();
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public List<Card> removeCards(List<Integer> indices) {
        List<Card> removed = new ArrayList<>();
        Collections.sort(indices, Collections.reverseOrder());
        for (int i : indices) {
            removed.add(hand.remove(i));
        }
        return removed;
    }
    
    public Map<String, Integer> getSuitScores() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Hearts", 0);
        scores.put("Diamonds", 0);
        scores.put("Clubs", 0);
        scores.put("Spades", 0);
        
        for (Card card : hand) {
            scores.put(card.getSuit(), scores.get(card.getSuit()) + card.getValue());
        }
        return scores;
    }
    
    public int getMaxSuitScore() {
        return Collections.max(getSuitScores().values());
    }
    
    public String getBestSuit() {
        Map<String, Integer> scores = getSuitScores();
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    
    public String getName() { return name; }
    public List<Card> getHand() { return hand; }
    public int getTotalScore() { return totalScore; }
    public List<Integer> getRoundScores() { return roundScores; }
    
    public void addRoundScore(int score) {
        roundScores.add(score);
        totalScore += score;
    }
    
    public void clearHand() { hand.clear(); }
}