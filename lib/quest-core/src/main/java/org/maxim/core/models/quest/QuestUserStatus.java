package org.maxim.core.models.quest;

import org.jetbrains.annotations.Nullable;
import java.util.Map;
import org.maxim.core.models.quest.datatypes.Snowflake;

public class QuestUserStatus {
    public Snowflake userID;

    @Nullable
    public Snowflake questID;
    @Nullable
    public String enrolledAt;
    @Nullable
    public String completedAt;
    @Nullable
    public String claimedAt;
    @Nullable
    public Integer claimedTier;

    public String lastStreamHeartbeatAt;
    public String streamProgressSeconds;
    public Integer dismissedQuestContent;
    public Map<String, QuestTaskProgress> progress;

}
