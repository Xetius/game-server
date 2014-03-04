package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHandlerFactory {
    @SuppressWarnings("MalformedRegex")
    public HttpHandler getHandler(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        String path = uri.getPath();

        Pattern pattern = Pattern.compile("/(\\d+)/(?<verb>.*)");
        Matcher matcher = pattern.matcher(path);

        String verb = "";

        if (matcher.find()) {
            verb = matcher.group("verb");
        }

        if (verb.equalsIgnoreCase("login") && method.equalsIgnoreCase("GET")) {
            return new LoginRequestHandler();
        } else if (verb.equalsIgnoreCase("score") && method.equalsIgnoreCase("POST")) {
            return new ScoreUpdateRequestHandler();
        } else if (verb.equalsIgnoreCase("highscorelist") && method.equalsIgnoreCase("GET")) {
            return new HighScoreListRequestHandler();
        }

        return new NullHttpHandler();
    }
}
