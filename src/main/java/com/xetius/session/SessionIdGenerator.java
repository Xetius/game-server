package com.xetius.session;

import java.util.Random;

public class SessionIdGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 10;
    private static final Random random = new Random();

    public static String getId() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            builder.append (CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }
}
