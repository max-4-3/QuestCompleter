package org.maxim.core.models.quest.config.task;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.maxim.core.models.quest.config.application.QuestPartialApplication;

public class QuestTaskV2 extends QuestTaskBase {
    public String type;

    @Nullable
    public List<QuestPartialApplication> applications;
}

