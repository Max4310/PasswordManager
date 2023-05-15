package org.example;

import java.util.ArrayList;

public class Main {
    public static ArrayList<String> activeUsers;

    public static void main(String[] args) {
        activeUsers = new ArrayList<>();
        new Server();
    }
}