package org.maxim.core.models.quest.config.task;

import org.maxim.core.models.quest.config.Nullable;

public class QuestTask extends QuestTaskBase {
    public String eventName;

    @Nullable
    public String title;
    @Nullable
    public String description;
}
