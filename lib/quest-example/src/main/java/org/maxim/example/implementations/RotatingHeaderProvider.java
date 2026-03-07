package org.maxim.example.implementations;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class RotatingHeaderProvider implements HeaderProvider {
    private final AtomicReference<Map<String, String>> cached = new AtomicReference<>();
    private final Supplier<Map<String, String>> supplier;
    private final Duration ttl;

    private Instant lastRefresh = Instant.MIN;

    public RotatingHeaderProvider(
            Duration ttl,
            Supplier<Map<String, String>> supplier) {
        this.ttl = ttl;
        this.supplier = supplier;
    }

    @Override
    public synchronized Map<String, String> headers() {
        Instant now = Instant.now();

        if (cached.get() == null || Duration.between(lastRefresh, now).compareTo(ttl) > 0) {
            cached.set(supplier.get());
            lastRefresh = now;
        }

        return cached.get();
    }
}
