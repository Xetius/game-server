package com.xetius.data;

public class ScoreData {
    private int userId;
    private int score;

    public ScoreData(int userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return userId + "=" + score;
    }

    public int getUserId() {
        return userId;
    }
}
