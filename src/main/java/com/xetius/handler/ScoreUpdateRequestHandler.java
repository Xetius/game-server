package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.xetius.data.HashMapDataStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreUpdateRequestHandler extends AbstractHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);

        int level = extractLevel(httpExchange);
        String sessionId = extractSessionId(httpExchange);
        int score = extractScore(httpExchange);
        HashMapDataStorage storage = HashMapDataStorage.getInstance();
        storage.setLevelScoreForUser(level, sessionId, score);
    }

    private int extractLevel(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        Pattern pattern = Pattern.compile("/(?<level>\\d+)/score");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group("level"));
        }
        return 0;
    }

    private String extractSessionId(HttpExchange httpExchange) {
        Map<String, Object> params = (Map<String, Object>)httpExchange.getAttribute("parameters");
        return (String)params.get("sessionkey");
    }

    private int extractScore(HttpExchange httpExchange) {
        String scoreString = convertStreamToString(httpExchange.getRequestBody());
        return Integer.parseInt(scoreString);
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
