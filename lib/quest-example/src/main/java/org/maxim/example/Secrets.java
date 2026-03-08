package org.maxim.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public final class Secrets {
    public static String getToken() {
        try {
            // Read token from a file
            return Files.readString(Paths.get("<token-file>")).trim();
        } catch (NoSuchFileException e) {
            throw new RuntimeException("ERROR: wrong token file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
