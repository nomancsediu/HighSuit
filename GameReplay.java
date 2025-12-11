import java.util.*;

public class GameReplay {
    public static class RoundData {
        public int round;
        public List<PlayerRoundData> players;
        
        public RoundData(int round) {
            this.round = round;
            this.players = new ArrayList<>();
        }
    }
    
    public static class PlayerRoundData {
        public String name;
        public List<String> initialHand;
        public String bonusSuit;
        public List<String> finalHand;
        public String bestSuit;
        public int score;
        
        public PlayerRoundData(String name, List<String> initialHand, String bonusSuit, 
                              List<String> finalHand, String bestSuit, int score) {
            this.name = name;
            this.initialHand = new ArrayList<>(initialHand);
            this.bonusSuit = bonusSuit;
            this.finalHand = new ArrayList<>(finalHand);
            this.bestSuit = bestSuit;
            this.score = score;
        }
    }
}