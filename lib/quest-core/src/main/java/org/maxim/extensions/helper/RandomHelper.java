package org.maxim.extensions.helper;

/* Python's random module minimal interface */
public interface RandomHelper {
    // Don't know
    long uniform(int a, int b);

    // Generate a random value b/w [0, 1]
    double random();
}
