package org.maxim.core.completer;

import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.completion.QuestCompletionResult;
import org.maxim.core.models.completion.QuestCompletionStatus;

public interface Completer {

    // This method should be long running task to complete the Quest
    public QuestCompletionResult completeQuest(Quest quest);

    // This should get the current status of the progress
    public QuestCompletionStatus currentStatus();
}
