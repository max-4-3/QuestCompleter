package org.maxim.core.models.quest.config.reward;

import java.util.Map;

import org.maxim.core.models.quest.config.Nullable;

public class QuestRewardMessages {
    public String name;
    public String nameWithArticle;
    @Nullable
    public Map<Integer, String> rewardRedemptionInstructionsByPlatform;
}

