package com.xetius;


public class Main {

    private GameServer server;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        server = new GameServer();
        server.start();
    }
}
