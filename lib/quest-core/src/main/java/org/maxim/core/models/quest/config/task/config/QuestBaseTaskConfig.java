package org.maxim.core.models.quest.config.task.config;

import java.util.Map;

import org.maxim.core.models.quest.config.Nullable;
import org.maxim.core.models.quest.config.task.QuestTaskBase;

public class QuestBaseTaskConfig {
    public String joinOperator;
    public Map<String, QuestTaskBase> tasks;

    @Nullable
    public String enrollmentUrl;
    @Nullable
    public Long developerApplicationId;
}

