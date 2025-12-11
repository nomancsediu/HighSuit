public class Card {
    private String suit;
    private String rank;
    
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public int getValue() {
        switch (rank) {
            case "J": case "Q": case "K": return 10;
            case "A": return 11;
            default: return Integer.parseInt(rank);
        }
    }
    
    public String getSuit() { return suit; }
    public String getRank() { return rank; }
    
    @Override
    public String toString() {
        String symbol = suit.equals("Hearts") ? "♥" : 
                       suit.equals("Diamonds") ? "♦" : 
                       suit.equals("Clubs") ? "♣" : "♠";
        return rank + symbol;
    }
}