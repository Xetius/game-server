package com.xetius.data;

import com.xetius.session.SessionManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapDataStorage implements DataStorage {
    private static HashMapDataStorage instance;
    private SessionManager sessionManager;
    private Map<Integer, Map<Integer, Integer>> scores;

    private HashMapDataStorage() {
        sessionManager = SessionManager.getInstance();
        reset();
    }

    public void reset() {
        scores = new ConcurrentHashMap<>();
    }

    public static HashMapDataStorage getInstance() {
        if (null == instance) {
            synchronized (HashMapDataStorage.class) {
                if (null == instance) {
                    instance = new HashMapDataStorage();
                }
            }
        }
        return instance;
    }

    public void setLevelScoreForUser(int level, String sessionId, int score) {
        Map<Integer, Integer> levelData = getLevelData(level);
        int userId = sessionManager.getUserId(sessionId);
        setOrUpdateUserScore(levelData, userId, score);
    }

    private Map<Integer, Integer> getLevelData(int level) {
        if (scores.containsKey(level)) {
            return scores.get(level);
        } else {
            Map<Integer, Integer> newLevel = new ConcurrentHashMap<>();
            scores.put(level, newLevel);
            return newLevel;
        }
    }

    private void setOrUpdateUserScore(Map<Integer,Integer> levelMap, int user, int score) {
        int newScore = score;
        if (levelMap.containsKey(user)) {
            int oldScore = levelMap.get(user);
            if (oldScore > newScore) {
                newScore = oldScore;
            }
        }
        levelMap.put(user, newScore);
    }

    public String getTopScoresForLevel(int level, int count) {
        if (scores.containsKey(level)) {
            Map<Integer, Integer> scoresForLevel = scores.get(level);
            TreeSet<ScoreData> scoreData = convertMapToTree(scoresForLevel);
            return createScoreCSV(scoreData, count);
        }
        return "";
    }

    private String createScoreCSV(TreeSet<ScoreData> scoreData, int count) {

        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (ScoreData item : scoreData) {
            if (index >= count) {
                return builder.toString();
            }

            if (index > 0) {
                builder.append(",");
            }

            builder.append(item.toString());
            index++;
        }
        return builder.toString();
    }

    private TreeSet<ScoreData> convertMapToTree(Map<Integer,Integer> scoresForLevel) {
        TreeSet<ScoreData> scoreDataSet = new TreeSet<>(new Comparator<ScoreData>() {
            @Override
            public int compare(ScoreData o1, ScoreData o2) {
                if (o2.getScore() != o1.getScore()) {
                    return o2.getScore() - o1.getScore();
                } else {
                    return o1.getUserId() - o2.getUserId();
                }
            }
        });

        for(Map.Entry<Integer, Integer> entry : scoresForLevel.entrySet()) {
            ScoreData scoreData = new ScoreData(entry.getKey(), entry.getValue());
            scoreDataSet.add(scoreData);
        }
        return scoreDataSet;
    }

    public Map<Integer, Map<Integer, Integer>> getRawStorage() {
        return scores;
    }
}
