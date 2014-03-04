package com.xetius.session;

public class SessionData {
    private String sessionId;
    private long startTime;

    public SessionData(String sessionId, long startTime) {
        this.sessionId = sessionId;
        this.startTime = startTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean hasExpired() {
        long now = System.currentTimeMillis();
        long TEN_MINUTES = 600000;
        return (startTime + TEN_MINUTES) < now;
    }
}
