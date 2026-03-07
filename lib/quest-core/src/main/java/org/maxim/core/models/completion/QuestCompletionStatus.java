package org.maxim.core.models.completion;

import org.maxim.core.models.quest.types.QuestType;

public class QuestCompletionStatus {
    public QuestType type = QuestType.Unknown;
    public long done = 0;
    public long total = 0;

    public float getPercentage() {
        return total > 0 ? done / total : 0.0f;
    }
}
