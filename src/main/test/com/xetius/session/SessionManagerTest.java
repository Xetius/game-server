package com.xetius.session;

import org.junit.Assert;
import org.junit.Test;

public class SessionManagerTest {

    @Test
    public void testGetInstanceReturnsIdenticalInstances() {
        SessionManager sessionManager1 = SessionManager.getInstance();
        SessionManager sessionManager2 = SessionManager.getInstance();

        Assert.assertEquals(sessionManager1, sessionManager2);
    }

    @Test
    public void testLoginReturnsTenDigitSessionId() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.login(1000);
        Assert.assertNotNull(sessionId);
        Assert.assertEquals(10, sessionId.length());
    }

    @SuppressWarnings("StringEquality")
    @Test
    public void testSequentialLoginsReturnTheSameSessionIdForTheSameUser() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId1 = sessionManager.login(1000);
        String sessionId2 = sessionManager.login(1000);

        Assert.assertNotNull(sessionId1);
        Assert.assertNotNull(sessionId2);
        Assert.assertEquals(sessionId1, sessionId2);
        Assert.assertTrue(sessionId1 == sessionId2);
    }

    @Test
    public void testSequentialLoginsReturnDifferentSessionIdsForDifferentUsers() {
        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId1 = sessionManager.login(1000);
        String sessionId2 = sessionManager.login(1001);
        Assert.assertNotNull(sessionId1);
        Assert.assertNotNull(sessionId2);
        Assert.assertNotEquals(sessionId1, sessionId2);
    }
}
