package org.maxim.core.models.completion;

import org.maxim.core.models.quest.types.QuestType;

public class QuestCompletionStatus {
    public volatile QuestType type = QuestType.Unknown;
    public volatile long done = 0;
    public volatile long total = 0;
    public volatile boolean completed = false;

    public double getPercentage() {
        return total > 0 ? ((double) done / total) * 100.0f : 0.0f;
    }
}
