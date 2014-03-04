package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.xetius.data.DataStorage;
import com.xetius.data.HashMapDataStorage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighScoreListRequestHandler extends AbstractHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);

        DataStorage storage = HashMapDataStorage.getInstance();
        int level = extractLevel(httpExchange);
        String highScores = storage.getTopScoresForLevel(level, 15);

        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(highScores.getBytes());
        responseBody.close();
    }

    @SuppressWarnings("MalformedRegex")
    private int extractLevel(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();

        Pattern pattern = Pattern.compile("/(?<level>\\d+)/highscorelist");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group("level"));
        }
        return 0;
    }
}
