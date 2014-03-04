package com.xetius;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.xetius.filter.ParameterFilter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class GameServer {
    HttpServer server;
    InetSocketAddress address;

    public GameServer() {
        this("/", 8080);
    }

    public GameServer(String rootContext, int port) {
        try {
            address = new InetSocketAddress(port);
            server = HttpServer.create(address, 0);
            HttpContext context = server.createContext(rootContext, new RequestHandler());
            context.getFilters().add(new ParameterFilter());
            server.setExecutor(Executors.newFixedThreadPool(100));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        server.start();
        System.out.println("GameServer running on port " + address.getPort());
    }
}
