// https://leetcode.com/problems/design-a-leaderboard/description/
class Leaderboard {
// use a Map to keep <id, score>
// use a TreeMap <score, dup> to keep top K scores
    private Map<Integer, Integer> scoreMap = new HashMap<>();
    // for topK player
    // map of <score, occurrence>
    private TreeMap<Integer, Integer> topMap = new TreeMap<>();

    public Leaderboard() {
        
    }
    
    public void addScore(int playerId, int score) {
        int oldScore = scoreMap.getOrDefault(playerId, 0);
        int newScore = oldScore + score;
        // remove oldScore entry if needed
        if (scoreMap.containsKey(playerId)) {
            // no need to update scoreMap because we put newScore later
            int occurrence = topMap.get(oldScore);
            if (occurrence > 1) {
                topMap.put(oldScore, occurrence - 1);
            } else {
                topMap.remove(oldScore);
            }
        }
        // add newScore entry
        scoreMap.put(playerId, newScore);        
        int newOcc = topMap.getOrDefault(newScore, 0);
        topMap.put(newScore, newOcc + 1);
    }
    
    public int top(int K) {
        int sum = 0;
        int cnt = 0;
        for (Map.Entry<Integer, Integer> entry : topMap.descendingMap().entrySet()) {
            int score = entry.getKey();
            int occ = entry.getValue();
            if (cnt + occ <= K) {
                sum += score * occ;
                cnt += occ;
            } else {
                sum += score * (K - cnt);
                break;
            }
        }
        return sum;
    }
    
    public void reset(int playerId) {
        int score = scoreMap.get(playerId);
        scoreMap.remove(playerId);
        int occ = topMap.get(score);
        if (occ > 1) {
            topMap.put(score, occ - 1);
        } else {
            topMap.remove(score);
        }
    }
}

/**
 * Your Leaderboard object will be instantiated and called as such:
 * Leaderboard obj = new Leaderboard();
 * obj.addScore(playerId,score);
 * int param_2 = obj.top(K);
 * obj.reset(playerId);
 */
