package org.maxim.core.models.quest.userstatus.task;

import org.maxim.core.models.quest.config.Nullable;

public class QuestUserTaskProgress {
    public String eventName;
    public Integer value;
    public String updatedAt;

    @Nullable
    public String completedAt;
    public QuestTaskHeartbeat heartbeat;
}
