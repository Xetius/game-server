package com.xetius.data;

import com.xetius.session.SessionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class HashMapDataStorageTest {
    private HashMapDataStorage dataStorage;

    @Before
    public void setUp() {
        dataStorage = HashMapDataStorage.getInstance();
    }

    @Test
    public void testAddingNewUserScoreForNewLevelCreatesLevelAndUser() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.login(1000);
        dataStorage.setLevelScoreForUser(1, sessionId, 12345);
        Map<Integer, Map<Integer, Integer>> storage = dataStorage.getRawStorage();
        Assert.assertEquals(1, storage.size());
        Map<Integer, Integer> levelData = storage.get(1);
        Assert.assertNotNull(levelData);
        Assert.assertEquals(1, levelData.size());
        Assert.assertTrue(levelData.containsKey(1000));
        Integer score = levelData.get(1000);
        Assert.assertNotNull(score);
        Assert.assertEquals(12345, score.intValue());
    }

    @Test
    public void testUpdatingSingleUsersScoreWithHigherScoreChangesHighScore() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.login(1000);
        dataStorage.setLevelScoreForUser(1, sessionId, 12345);
        dataStorage.setLevelScoreForUser(1, sessionId, 12346);
        Map<Integer, Map<Integer, Integer>> storage = dataStorage.getRawStorage();
        Assert.assertEquals(1, storage.size());
        Map<Integer, Integer> levelData = storage.get(1);
        Assert.assertNotNull(levelData);
        Assert.assertEquals(1, levelData.size());
        Assert.assertTrue(levelData.containsKey(1000));
        Integer score = levelData.get(1000);
        Assert.assertNotNull(score);
        Assert.assertEquals(12346, score.intValue());
    }

    @Test
    public void testUpdatingSingleUsersScoreWithLowerScoreKeepsExistingHighScore() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.login(1000);
        dataStorage.setLevelScoreForUser(1, sessionId, 12345);
        dataStorage.setLevelScoreForUser(1, sessionId, 12344);
        Map<Integer, Map<Integer, Integer>> storage = dataStorage.getRawStorage();
        Assert.assertEquals(1, storage.size());
        Map<Integer, Integer> levelData = storage.get(1);
        Assert.assertNotNull(levelData);
        Assert.assertEquals(1, levelData.size());
        Assert.assertTrue(levelData.containsKey(1000));
        Integer score = levelData.get(1000);
        Assert.assertNotNull(score);
        Assert.assertEquals(12345, score.intValue());
    }

    @Test
    public void testAddingSecondUsersScoreForLevelAddsSecondScoreToLevelData() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId1 = sessionManager.login(1000);
        String sessionId2 = sessionManager.login(1001);
        dataStorage.setLevelScoreForUser(1, sessionId1, 12345);
        dataStorage.setLevelScoreForUser(1, sessionId2, 12344);
        Map<Integer, Map<Integer, Integer>> storage = dataStorage.getRawStorage();
        Assert.assertEquals(1, storage.size());
        Map<Integer, Integer> levelData = storage.get(1);
        Assert.assertNotNull(levelData);
        Assert.assertEquals(2, levelData.size());
        Assert.assertTrue(levelData.containsKey(1000));
        Integer score1 = levelData.get(1000);
        Assert.assertNotNull(score1);
        Assert.assertEquals(12345, score1.intValue());
        Assert.assertTrue(levelData.containsKey(1001));
        Integer score2 = levelData.get(1001);
        Assert.assertNotNull(score2);
        Assert.assertEquals(12344, score2.intValue());
    }

    @Test
    public void test15HighScores() {
        addUserScore(1, 1000, 94);
        addUserScore(1, 1001, 20);
        addUserScore(1, 1002, 54);
        addUserScore(1, 1003, 12);
        addUserScore(1, 1004, 65);
        addUserScore(1, 1005, 94);
        addUserScore(1, 1006, 83);
        addUserScore(1, 1007, 33);
        addUserScore(1, 1008, 74);
        addUserScore(1, 1009, 65);
        addUserScore(1, 1010, 23);
        addUserScore(1, 1011, 64);
        addUserScore(1, 1012, 23);
        addUserScore(1, 1013, 63);
        addUserScore(1, 1014, 16);
        addUserScore(1, 1015, 75);
        addUserScore(1, 1016, 12);
        addUserScore(1, 1017, 91);
        addUserScore(1, 1018, 47);
        addUserScore(1, 1019, 77);
        addUserScore(1, 1020, 74);
        String topScores = dataStorage.getTopScoresForLevel(1, 15);
        String expected = "1000=94,1005=94,1017=91,1006=83,1019=77,1015=75,1008=74,1020=74,1004=65,1009=65,1011=64,1013=63,1002=54,1018=47,1007=33";
        Assert.assertEquals(expected, topScores);
    }

    @Test
    public void test5HighScoresFromMoreData() {
        addUserScore(1, 1000, 1);
        addUserScore(1, 1001, 2);
        addUserScore(1, 1002, 3);
        addUserScore(1, 1003, 4);
        addUserScore(1, 1004, 5);
        addUserScore(1, 1005, 6);
        String topScores = dataStorage.getTopScoresForLevel(1, 5);
        String expected = "1005=6,1004=5,1003=4,1002=3,1001=2";
        Assert.assertEquals(expected, topScores);
    }

    @Test
    public void test5HighScoresFromLessData() {
        addUserScore(1, 1000, 1);
        addUserScore(1, 1001, 2);
        addUserScore(1, 1002, 3);
        String topScores = dataStorage.getTopScoresForLevel(1, 5);
        String expected = "1002=3,1001=2,1000=1";
        Assert.assertEquals(expected, topScores);
    }

    @Test
    public void testHighScoresWithNoDataReturnsEmptyString() {
        String topScores = dataStorage.getTopScoresForLevel(1, 5);
        String expected = "";
        Assert.assertEquals(expected, topScores);
    }

    @Test
    public void testHighScoresWithNonExistentLevelReturnsEmptyString() {
        addUserScore(1, 1000, 1);
        addUserScore(1, 1001, 2);
        addUserScore(1, 1002, 3);
        addUserScore(1, 1003, 4);
        addUserScore(1, 1004, 5);
        addUserScore(1, 1005, 6);
        String topScores = dataStorage.getTopScoresForLevel(2, 5);
        String expected = "";
        Assert.assertEquals(expected, topScores);
    }

    private void addUserScore(int level, int userId, int score) {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.login(userId);
        dataStorage.setLevelScoreForUser(level, sessionId, score);
    }
}
