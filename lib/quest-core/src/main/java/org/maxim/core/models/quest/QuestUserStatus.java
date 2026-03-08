package org.maxim.core.models.quest;

import java.util.Map;

import org.jetbrains.annotations.Nullable;
import org.maxim.core.models.quest.userstatus.task.QuestUserTaskProgress;

public class QuestUserStatus {
    public Long userId;

    @Nullable
    public Long questId;
    @Nullable
    public String enrolledAt;
    @Nullable
    public String completedAt;
    @Nullable
    public String claimedAt;
    @Nullable
    public Integer claimedTier;

    @Nullable
    public String lastStreamHeartbeatAt;

    public Integer streamProgressSeconds;
    public Integer dismissedQuestContent;
    public Map<String, QuestUserTaskProgress> progress;

}
