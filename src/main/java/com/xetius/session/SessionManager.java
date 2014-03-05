package com.xetius.session;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static SessionManager instance;
    ConcurrentHashMap<Integer, SessionData> sessions;
    ConcurrentHashMap<String, Integer> userIds;

    private SessionManager(){
        sessions = new ConcurrentHashMap<>();
        userIds = new ConcurrentHashMap<>();
    }

    public static SessionManager getInstance() {
        if (null == instance) {
            synchronized (SessionManager.class) {
                if (null == instance) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    public String login (int userId) {
        if (existingValidSessionExists(userId)) {
            return currentSessionId(userId);
        }

        return createNewSessionId(userId);
    }

    boolean existingValidSessionExists(int userId) {
        if (sessions.containsKey(userId)) {
            SessionData sessionData = sessions.get(userId);
            if (!sessionData.hasExpired()) {
                return true;
            } else {
                sessions.remove(userId);
                userIds.remove(sessionData.getSessionId());
            }
        }
        return false;
    }

    private String currentSessionId(int userId) {
        SessionData sessionData = sessions.get(userId);
        return sessionData.getSessionId();
    }

    private String createNewSessionId(int userId) {
        String sessionId = SessionIdGenerator.getId();
        long now = System.currentTimeMillis();
        SessionData sessionData = new SessionData(sessionId, now);
        sessions.put(userId, sessionData);
        userIds.put(sessionId, userId);
        return sessionId;
    }

    public int getUserId(String sessionId) {
        if (userIds.containsKey(sessionId)) {
            return userIds.get(sessionId);
        } else {
            return 0;
        }
    }
}
