package org.example;

public class Logger {
    public static void log(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void warn(String message) {
        System.out.println("[WARN] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}