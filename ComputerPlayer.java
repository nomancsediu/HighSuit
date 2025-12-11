import java.util.*;

public class ComputerPlayer extends Player {
    
    public ComputerPlayer(String name) {
        super(name);
    }
    
    public String chooseBonusSuit() {
        return getBestSuit();
    }
    
    public List<Integer> chooseCardsToReplace() {
        String bestSuit = getBestSuit();
        List<Integer> toReplace = new ArrayList<>();
        
        for (int i = 0; i < hand.size(); i++) {
            if (!hand.get(i).getSuit().equals(bestSuit)) {
                toReplace.add(i);
            }
        }
        
        // Limit to 4 replacements
        if (toReplace.size() > 4) {
            toReplace = toReplace.subList(0, 4);
        }
        
        return toReplace;
    }
}