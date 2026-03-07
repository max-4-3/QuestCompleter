package org.maxim.core.models.quest;
import org.jetbrains.annotations.Nullable;

class QuestTaskHeartbeat {
    public String lastBeatAt;

    @Nullable
    public String expiresAt;
}

public class QuestTaskProgress {
    public String eventName;
    public Integer value;
    public String updatedAt;

    @Nullable
    public String completedAt;
    public QuestTaskHeartbeat heartbeat;
}
