package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.xetius.session.SessionManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRequestHandler extends AbstractHttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);
        String sessionId = doLogin(httpExchange);
        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(sessionId.getBytes());
        responseBody.close();
    }

    private String doLogin(HttpExchange httpExchange) {
        int userId = extractUserId(httpExchange);
        SessionManager sessionManager = SessionManager.getInstance();
        return sessionManager.login(userId);
    }

    @SuppressWarnings("MalformedRegex")
    private int extractUserId(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        Pattern pattern = Pattern.compile("/(?<id>\\d+)/login");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            String id = matcher.group("id");
            return Integer.parseInt(id);
        }
        return 0;
    }


}
