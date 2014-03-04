package com.xetius.data;

import java.util.Map;

public interface DataStorage {
    void setLevelScoreForUser(int level, String sessionId, int score);
    String getTopScoresForLevel(int level, int count);
    public Map<Integer, Map<Integer, Integer>> getRawStorage();
}
