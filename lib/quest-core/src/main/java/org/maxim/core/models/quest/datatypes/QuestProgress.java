package org.maxim.core.models.quest.datatypes;

public final class QuestProgress {
    public String taskName;
    public long done;
    public long total;

    public QuestProgress() {
    }

    public QuestProgress(String taskName, int done, int total) {
        this.taskName = taskName;
        this.done = done;
        this.total = total;
    }

    public float getPercentage() {
        return this.total > 0 ? (((float) this.done) / ((float) this.total)) : 0.0f;
    }
}
