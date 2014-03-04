package com.xetius;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.xetius.handler.HttpHandlerFactory;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    HttpHandlerFactory factory;
    public RequestHandler() {
        factory = new HttpHandlerFactory();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = factory.getHandler(httpExchange);
        handler.handle(httpExchange);
    }
}
