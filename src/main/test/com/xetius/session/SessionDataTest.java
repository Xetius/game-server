package com.xetius.session;

import org.junit.Assert;
import org.junit.Test;

public class SessionDataTest {
    @Test
    public void testHasExpiredReturnsTrueForSessionDataOlderThanTenMinutes() {
        String id = SessionIdGenerator.getId();
        long tenMinutesAgo = System.currentTimeMillis() - 600001;
        SessionData sessionData = new SessionData(id, tenMinutesAgo);
        Assert.assertTrue(sessionData.hasExpired());
    }

    @Test
    public void testHasExpiredReturnsFalseForRecentGeneratedSessionIds() {
        String id = SessionIdGenerator.getId();
        long fiveMinutesAgo = System.currentTimeMillis() - 300000;
        SessionData sessionData = new SessionData(id, fiveMinutesAgo);
        Assert.assertFalse(sessionData.hasExpired());
    }
}
