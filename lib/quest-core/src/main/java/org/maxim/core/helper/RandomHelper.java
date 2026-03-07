package org.maxim.core.helper;

/* Python's random module minimal interface */
public interface RandomHelper {
    // Don't know
    public long uniform(int a, int b);

    // Generate a random value b/w [0, 1]
    public double random();
}
