package org.maxim.core.models.quest.config.task.config;

import java.util.Map;

import org.maxim.core.models.quest.config.Nullable;
import org.maxim.core.models.quest.config.task.QuestTaskBase;

public class QuestBaseTaskConfig<T extends QuestTaskBase> {
    public String joinOperator;
    public Map<String, T> tasks;

    @Nullable
    public String enrollmentUrl;
    @Nullable
    public Long developerApplicationId;
}

