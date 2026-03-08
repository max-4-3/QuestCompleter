package org.maxim.core.models.quest.config.task.config;

import java.util.Map;

import org.maxim.core.models.quest.config.task.QuestTask;

public class QuestTaskConfig extends QuestBaseTaskConfig {
    public Integer type;
    public Map<String, QuestTask> tasks;
}

