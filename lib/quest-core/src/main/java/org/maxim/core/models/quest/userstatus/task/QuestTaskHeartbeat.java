package org.maxim.core.models.quest.userstatus.task;

import org.maxim.core.models.quest.config.Nullable;

public class QuestTaskHeartbeat {
    public String lastBeatAt;

    @Nullable
    public String expiresAt;
}

