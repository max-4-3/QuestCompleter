package org.maxim.core.completer;

import org.maxim.core.session.Session;
import org.maxim.core.models.quest.datatypes.QuestProgress;

import java.util.Map;
import java.util.function.Consumer;

import org.maxim.core.models.quest.Quest;

public class QuestCompleter {

    public static class PrefixedProgress {
        public long done;
        public long total;
        public String prefix;

        public static class Builder {
            private final PrefixedProgress p = new PrefixedProgress();

            public Builder prefix(String prefix) {
                this.p.prefix = prefix;
                return this;
            }

            public Builder done(long done) {
                this.p.done = done;
                return this;
            }

            public Builder total(long total) {
                this.p.total = total;
                return this;
            }

            public PrefixedProgress build() {
                return this.p;
            }
        }
    }

    private Map<Integer, Consumer<PrefixedProgress>> procCallback;
    private Map<Integer, Consumer<String>> logCallback;
    private Session session;

    public boolean CompleteQuest(Quest quest) {
        return true;
    }

    public static PrefixedProgress getPrefixedProgress(QuestProgress progress) {
        return new PrefixedProgress.Builder()
                .prefix(progress.taskName)
                .done((long) progress.done)
                .total((long) progress.total)
                .build();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    public void sendLogMessage(String message) {
        for (Consumer<String> c : this.logCallback.values()) {
            c.accept(message);
        }
    }

    public void sendProgressMessage(PrefixedProgress message) {
        for (Consumer<PrefixedProgress> c : this.procCallback.values()) {
            c.accept(message);
        }
    }

    public void removeLogListener(int id) {
        if (this.logCallback.containsKey(id)) {
            this.logCallback.remove(id);
        }
    }

    public void removeProgressListener(int id) {
        if (this.procCallback.containsKey(id)) {
            this.procCallback.remove(id);
        }
    }

    public int addLogListener(Consumer<String> consumer) {
        int id = this.logCallback.size();
        this.logCallback.put(id, consumer);
        return id;
    }

    public int addProgressListener(Consumer<PrefixedProgress> consumer) {
        int id = this.procCallback.size();
        this.procCallback.put(id, consumer);
        return id;
    }
}
