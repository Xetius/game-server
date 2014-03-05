package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.xetius.data.HashMapDataStorage;

import java.io.IOException;
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

    @SuppressWarnings("MalformedRegex")
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
        return extractParameter(httpExchange, "sessionkey");
    }

    private int extractScore(HttpExchange httpExchange) {
        String scoreString = extractParameter(httpExchange, "score");
        return Integer.parseInt(scoreString);
    }

    @SuppressWarnings("unchecked")
    private String extractParameter(HttpExchange httpExchange, String paramKey) {
        Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
        if (params.containsKey(paramKey)) {
            return (String)params.get(paramKey);
        }
        return "";
    }
}
