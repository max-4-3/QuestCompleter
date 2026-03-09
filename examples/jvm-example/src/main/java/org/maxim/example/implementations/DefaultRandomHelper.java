package org.maxim.example.implementations;

import org.maxim.core.helper.RandomHelper;
import java.util.random.RandomGenerator;

public class DefaultRandomHelper implements RandomHelper {
    private final RandomGenerator rng = RandomGenerator.getDefault();

    public long uniform(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be <= b");
        }
        return rng.nextLong(a, (long) b + 1);
    }

    public double random() {
        return rng.nextDouble();
    }
}
