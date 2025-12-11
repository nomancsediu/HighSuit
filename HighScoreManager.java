import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGH_SCORES_FILE = "high_scores.txt";
    
    public static class HighScore {
        public String name;
        public double score;
        public int totalScore;
        public int rounds;
        
        public HighScore(String name, double score, int totalScore, int rounds) {
            this.name = name;
            this.score = score;
            this.totalScore = totalScore;
            this.rounds = rounds;
        }
    }
    
    public List<HighScore> loadHighScores() {
        List<HighScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    scores.add(new HighScore(parts[0], Double.parseDouble(parts[1]), 
                        Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, return empty list
        }
        return scores;
    }
    
    public void saveHighScores(List<HighScore> scores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORES_FILE))) {
            for (HighScore score : scores) {
                writer.println(score.name + "," + score.score + "," + score.totalScore + "," + score.rounds);
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }
    
    public void updateHighScores(List<Player> players, int rounds) {
        List<HighScore> highScores = loadHighScores();
        
        for (Player player : players) {
            double avgScore = (double) player.getTotalScore() / rounds;
            highScores.add(new HighScore(player.getName(), avgScore, player.getTotalScore(), rounds));
        }
        
        highScores.sort((a, b) -> Double.compare(b.score, a.score));
        if (highScores.size() > 5) {
            highScores = highScores.subList(0, 5);
        }
        
        saveHighScores(highScores);
    }
    
    public void displayHighScores() {
        List<HighScore> scores = loadHighScores();
        System.out.println("\n=== High Score Table ===");
        if (scores.isEmpty()) {
            System.out.println("No scores recorded yet");
            return;
        }
        
        for (int i = 0; i < scores.size(); i++) {
            HighScore score = scores.get(i);
            System.out.printf("%d. %s: %.1f (Total: %d over %d rounds)%n", 
                i + 1, score.name, score.score, score.totalScore, score.rounds);
        }
    }
}