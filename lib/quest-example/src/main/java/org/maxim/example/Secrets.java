package org.maxim.example;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class Secrets {
    public static String getToken() {
        try {
            // Read token from a file
            return Files.readString(Paths.get("<token-file>")).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
