package org.maxim.extensions.completer.status;

public class CompletionStatus {
    public volatile long done;
    public volatile long total;
    public volatile boolean completed;

    public synchronized double getPercentage() {
        return (double) done / total;
    }
}
